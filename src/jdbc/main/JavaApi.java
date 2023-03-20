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
import jdbc.model.entities.AnagraficaPlant;
import jdbc.model.entities.Entita;
import jdbc.model.entities.Mda;
import jdbc.model.entities.PartnerContratto;
import soap.ChiamataSoap;

public class JavaApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LocalDate currentDate = java.time.LocalDate.now();
			
		//LocalDate startDate = currentDate.minusYears(5).withMonth(1).withDayOfMonth(1);
		
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
					chunksInterval = 1;
					break;
				case SALUTE_PARTNER_CONTRATTO_T605:
					chunksInterval = 12;
					break;	
				}
				if(tipoChiamata == TypeChiamata.SALUTE_PARTNER_CONTRATTO_T605) {
			
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
					updateTable(conn, result, soapCall,startDate);
					}
				}
				
				}
			}

			conn.close();
		} catch (SQLException exc) {
			System.out.println(exc);
		}

	}

	static void updateTable(Connection conn, List<Entita> lista, ChiamataSoap soapCall, LocalDate startDate) {
		
		int counter = 0;
		int insertSize = 200;
			
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String startDateString = startDate.format(formatter);

		// CREAZIONE TABELLA TEMPORANEA CON I DATI PRECEDENTI A START DATE
			
		String createTableTempQuery = "CREATE TABLE TEMP_"+soapCall.typeChiamata.name()+" AS (SELECT * FROM "+soapCall.typeChiamata.name()+" WHERE TO_DATE('"+Queries.getSortingField(soapCall.typeChiamata)+"','YYYYMMDD') < TO_DATE('"+startDateString+"', 'YYYYMMDD')";

		try {
			Statement createTempTableStmt;
			createTempTableStmt = conn.createStatement();
			createTempTableStmt.executeUpdate(createTableTempQuery);
			createTempTableStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


			System.out.println("Tabella temp creata");
		
			
			// INSERIMENTO DATI NELLA TABELLA TEMP
			
			System.out.println("Inserimento dati nella tabella. Attendere...");
			
			String query = Queries.getQueryFirstPart(soapCall.typeChiamata, "TEMP_"+soapCall.typeChiamata.name());
				
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
					} catch (SQLException ignored) {
					}
					System.out.println(query);
					
					query = Queries.getQueryFirstPart(soapCall.typeChiamata, "TEMP_"+soapCall.typeChiamata.name());
					counter = 0;
				}
			}
			
			System.out.println("Dati inseriti nella tabella temp");

			// DELETE DELLA VECCHIA TABELLA

			String deleteTableQuery = "DROP TABLE " + soapCall.typeChiamata.name();
//			try {
//				Statement deleteOldTableStmt;
//				deleteOldTableStmt = conn.createStatement();
//				deleteOldTableStmt.executeUpdate(deleteTableQuery);
//				deleteOldTableStmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			System.out.println("Tabella " + soapCall.typeChiamata.name() + " cancellata...");

			// RINOMINA LA TABELLA TEMPORANEA

			String renameQuery = "RENAME TEMP_" + soapCall.typeChiamata.name() + " TO "
					+ soapCall.typeChiamata.name();
//			try {
//				Statement renameTempTableStmt;
//				renameTempTableStmt = conn.createStatement();
//				renameTempTableStmt.executeUpdate(renameQuery);
//				renameTempTableStmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			System.out.println("Tabella TEMP_" + soapCall.typeChiamata.name() + " rinominata in "+ soapCall.typeChiamata.name());

		

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