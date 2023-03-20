package jdbc.model.entities;

import java.util.Map;

public class PartnerContratto implements Entita{

	String COUNTER, DATA_INSERIMENTO, DESCRIZIONE_ESTESA, DES_RUOLO_PARTNER, FORNITORE_ENI, FORN_SOCIETARIO, FORN_SOCIETARIO_DESCR, ID_SIS_SORGENTE, NUM_DOC_ODL, RUOLO_PARTNER;

	public PartnerContratto(Map<String,String> partnerContratto) {
		
		COUNTER = partnerContratto.get("d4p1:COUNTER");
		DATA_INSERIMENTO = partnerContratto.get("d4p1:DATA_INSERIMENTO");
		DESCRIZIONE_ESTESA = partnerContratto.get("d4p1:DESCRIZIONE_ESTESA");
		DES_RUOLO_PARTNER = partnerContratto.get("d4p1:DES_RUOLO_PARTNER");
		FORNITORE_ENI = partnerContratto.get("d4p1:FORNITORE_ENI");
		FORN_SOCIETARIO = partnerContratto.get("d4p1:FORN_SOCIETARIO");
		FORN_SOCIETARIO_DESCR = partnerContratto.get("d4p1:FORN_SOCIETARIO_DESCR");
		ID_SIS_SORGENTE = partnerContratto.get("d4p1:ID_SIS_SORGENTE");
		NUM_DOC_ODL = partnerContratto.get("d4p1:NUM_DOC_ODL");
		RUOLO_PARTNER = partnerContratto.get("d4p1:RUOLO_PARTNER");
	}
	

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT "+COUNTER+",'"+DATA_INSERIMENTO+"','"+DESCRIZIONE_ESTESA+"','"+DES_RUOLO_PARTNER+"','"+FORNITORE_ENI+"','"+FORN_SOCIETARIO+"','"+FORN_SOCIETARIO_DESCR.replace("'", "''")+"','"+ID_SIS_SORGENTE+"','"+NUM_DOC_ODL+"','"+RUOLO_PARTNER+"' FROM DUAL ";

	}	
}