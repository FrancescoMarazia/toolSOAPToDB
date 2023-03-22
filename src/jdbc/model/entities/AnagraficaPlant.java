package jdbc.model.entities;

import java.util.Map;

public class AnagraficaPlant implements Entita {
	

	
	public String AREA_VALORIZZAZIONE,DIVISIONE,DIVISIONE_LOCALITA,DIVISIONE_NAME,DIVISIONE_NAZIONE,ID_SIS_SORGENTE,INDIRIZZO,REGIONE,SOCIETA;
	

	public AnagraficaPlant(Map<String,String> anag) {
		AREA_VALORIZZAZIONE = anag.get("d4p1:AREA_VALORIZZAZIONE");
		DIVISIONE = anag.get("d4p1:DIVISIONE");
		DIVISIONE_LOCALITA = anag.get("d4p1:DIVISIONE_LOCALITA");
		DIVISIONE_NAME = anag.get("d4p1:DIVISIONE_NAME");
		DIVISIONE_NAZIONE = anag.get("d4p1:DIVISIONE_NAZIONE");
		ID_SIS_SORGENTE = anag.get("d4p1:ID_SIS_SORGENTE");
		INDIRIZZO = anag.get("d4p1:INDIRIZZO");
		REGIONE = anag.get("d4p1:REGIONE");
		SOCIETA = anag.get("d4p1:SOCIETA");
	}

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT '"+AREA_VALORIZZAZIONE+"','"+DIVISIONE+"','"+DIVISIONE_LOCALITA.replace("'", "''")+"','"+DIVISIONE_NAME.replace("'", "''")+"','"+DIVISIONE_NAZIONE+"','"+ID_SIS_SORGENTE+"','"+INDIRIZZO.replace("'", "''")+"','"+REGIONE+"','"+SOCIETA+"' FROM DUAL ";
	}
}