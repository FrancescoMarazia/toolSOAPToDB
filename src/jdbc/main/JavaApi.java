package jdbc.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import constants.Queries;
import constants.TypeChiamata;

import jdbc.model.entities.Contratto;
import jdbc.model.entities.Attachment;
import jdbc.model.entities.Entita;

import soap.ChiamataSoap;
import soap.AttachmentCall;

public class JavaApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LocalDate currentDate = java.time.LocalDate.now();
			
//		LocalDate startDate = currentDate.minusYears(5).withMonth(1).withDayOfMonth(1);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = "2000-01-01";
		LocalDate startDate = LocalDate.parse(formattedDate,formatter);

		try {

			String url = "jdbc:oracle:thin:HMS/pwdHMSdev01@hemas-rdb1-sd.services.eni.intranet:1531/ENHMSS";

			Connection conn = DriverManager.getConnection(url);
			
			for(TypeChiamata tipoChiamata : TypeChiamata.values()) {
				Integer chunksInterval = null;
				switch(tipoChiamata) {
				case SALUTE_FATTURE_T603:
					chunksInterval = 2;
					break;
				case SALUTE_CONTRATTI_T601:
					chunksInterval = 12;
					break;	
				}
				if(tipoChiamata == TypeChiamata.SALUTE_ORDINE_DI_LAVORO_T600) // CONTROLLO SELETTIVAMENTE QUALE TABELLA POPOLARE
				{
			
				List<LocalDate[]> chunks = splitDateRange(startDate,currentDate,chunksInterval);
				List<Entita> result = new ArrayList<Entita>();
				
				for(int c = 0; c < chunks.size();c++) {
					
					System.out.println("Sto eseguendo la chiamata " + tipoChiamata.name());	
					
					ChiamataSoap soapCall = new ChiamataSoap(tipoChiamata, chunks.get(c)[0], chunks.get(c)[1]);
					
					List<Entita> chunkResult = new ArrayList<Entita>();
					
					chunkResult = soapCall.getData();
					
					result.addAll(chunkResult);
					
					if(c == chunks.size()-1) {
						System.out.println(result.size() + "elementi ritornati");
					updateTable(conn, result, soapCall.typeChiamata.name(),soapCall.hasDates(),startDate);
					}
				}
					
					List<Entita> attachments = new ArrayList<Entita>();
					
					for(int i = 0; i < result.size();i++) {
						
						Contratto contratto = (Contratto) result.get(i);				
						AttachmentCall allegati = new AttachmentCall(contratto.ID_SIS_SORGENTE);
						
						List<Entita> contratto_attas = new ArrayList<Entita>();
						
						contratto_attas = allegati.getAttachments(contratto.NUM_DOC);
						attachments.addAll(contratto_attas);
						
					}
					updateTable(conn,attachments,"SALUTE_ATTACHMENT_T607",false,startDate);
				}
			}
			
			

			conn.close();
		} catch (SQLException exc) {
			System.out.println(exc);
		}

	}

	static void updateTable(Connection conn, List<Entita> lista, String soapCallName, boolean hasDates, LocalDate startDate) {
		
		int counter = 0;
		int insertSize = 200;
		
		
		// CREAZIONE TABELLA TEMPORANEA CON I DATI PRECEDENTI A START DATE
		
		String createTableTempQuery="";
		
		if(hasDates) {
			
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String startDateString = startDate.format(formatter);

		createTableTempQuery = "CREATE TABLE TEMP_"+soapCallName+" AS (SELECT * FROM "+soapCallName+" WHERE TO_DATE("+Queries.getSortingField(soapCallName)+",'YYYYMMDD') < TO_DATE('"+startDateString+"', 'YYYYMMDD'))";
		}else {
			createTableTempQuery = "CREATE TABLE TEMP_"+soapCallName+" AS (SELECT * FROM "+soapCallName+" WHERE(1=0))";
		}
		
		try {
			Statement createTempTableStmt;
			createTempTableStmt = conn.createStatement();
			createTempTableStmt.executeUpdate(createTableTempQuery);
			createTempTableStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Tabella temp creata");
		
		
		// CREAZIONE TRIGGER AUTOINCREMENT
		String[] triggerAndSeq = Queries.getTrigger(soapCallName);
		if(triggerAndSeq[0] != null) {
		String createTriggerQuery = "create TRIGGER "+triggerAndSeq[0]+" BEFORE INSERT ON TEMP_"+soapCallName+" FOR EACH ROW BEGIN <<COLUMN_SEQUENCES>> BEGIN IF INSERTING AND :NEW.ID IS NULL THEN SELECT "+triggerAndSeq[1]+".NEXTVAL INTO :NEW.ID FROM SYS.DUAL; END IF; END COLUMN_SEQUENCES; END;";
		
		try {
			Statement createTempTableStmt;
			createTempTableStmt = conn.createStatement();
			createTempTableStmt.executeUpdate(createTriggerQuery);
			createTempTableStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		}
			// INSERIMENTO DATI NELLA TABELLA TEMP
			
			System.out.println("Inserimento dati nella tabella. Attendere...");
			
			String query = Queries.getQueryFirstPart(soapCallName, "TEMP_"+soapCallName);
				
			for (int i = 0; i < lista.size(); i++) {

				String insertQuery = lista.get(i).generaQuery();
				
				if(counter < insertSize && i != lista.size()-1) {
					query += insertQuery + "UNION ALL ";
					counter ++;
				}else {
					query += insertQuery + ") SELECT * FROM p";
					try {
						Statement insertQueryStmt;
						insertQueryStmt = conn.createStatement();
						insertQueryStmt.executeUpdate(query);
						insertQueryStmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					//System.out.println(query);
					
					query = Queries.getQueryFirstPart(soapCallName, "TEMP_"+soapCallName);
					counter = 0;
				}
			}
			
			System.out.println("Dati inseriti nella tabella temp");

			// DELETE DELLA VECCHIA TABELLA

			String deleteTableQuery = "DROP TABLE " + soapCallName;
			try {
				Statement deleteOldTableStmt;
				deleteOldTableStmt = conn.createStatement();
				deleteOldTableStmt.executeUpdate(deleteTableQuery);
				deleteOldTableStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Tabella " + soapCallName + " cancellata...");

			// RINOMINA LA TABELLA TEMPORANEA

			String renameQuery = "RENAME TEMP_" + soapCallName + " TO "
					+ soapCallName;
			try {
				Statement renameTempTableStmt;
				renameTempTableStmt = conn.createStatement();
				renameTempTableStmt.executeUpdate(renameQuery);
				renameTempTableStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Tabella TEMP_" + soapCallName + " rinominata in "+ soapCallName);

		

		// System.out.println("Dati per la chiamata "+soapCall.typeChiamata.name()+"
		// caricati!");
		

		
		
	}
	
	static List<LocalDate[]> splitDateRange(LocalDate startDate, LocalDate endDate, Integer month) {
        List<LocalDate[]> ranges = new ArrayList<>();
        if(month != null) {
        	LocalDate rangeStart = startDate;
        LocalDate rangeEnd = rangeStart.plusMonths(month).minusDays(1);
        while (rangeEnd.isBefore(endDate)) {
        	ranges.add(new LocalDate[]{rangeStart, rangeEnd});
        	rangeStart = rangeEnd.plusDays(1);
        	rangeEnd = rangeStart.plusMonths(month).minusDays(1);
        }
        ranges.add(new LocalDate[]{rangeStart, endDate});
        }else {
        ranges.add(new LocalDate[]{startDate, endDate});
        }
        return ranges;
    }
	
//	static List<LocalDate[]> splitDateRange(LocalDate startDate, LocalDate endDate) {
//        List<LocalDate[]> ranges = new ArrayList<>();    
//        ranges.add(new LocalDate[]{startDate, endDate});
//        return ranges;
//    }
}