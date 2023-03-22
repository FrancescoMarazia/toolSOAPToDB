package jdbc.model.entities;

import java.util.Map;

public class OrdineDiLavoro implements Entita {
	
	String DATA_CREAZIONE_ODL, DESCRIZIONE_ESTESA, DES_CONT_ODL, DIVISA_ORDINE_ACQ, FORNITORE_ENI, ID_SIS_SORGENTE, INTESTAZIONE_ODL, NUM_DOC, NUM_DOC_ODL, POS_DOC_ODL, UNITA_GESTORE, VALORE_DESTCNT, VALORE_DESTCNT_EUR ;

	public OrdineDiLavoro(Map<String,String> odl) {
		DATA_CREAZIONE_ODL = odl.get("d4p1:DATA_CREAZIONE_ODL");
		DESCRIZIONE_ESTESA = odl.get("d4p1:DESCRIZIONE_ESTESA");
		DES_CONT_ODL = odl.get("d4p1:DES_CONT_ODL");
		DIVISA_ORDINE_ACQ = odl.get("d4p1:DIVISA_ORDINE_ACQ");
		FORNITORE_ENI = odl.get("d4p1:FORNITORE_ENI");
		ID_SIS_SORGENTE = odl.get("d4p1:ID_SIS_SORGENTE");
		INTESTAZIONE_ODL = odl.get("d4p1:INTESTAZIONE_ODL");
		NUM_DOC = odl.get("d4p1:NUM_DOC");
		NUM_DOC_ODL = odl.get("d4p1:NUM_DOC_ODL");
		POS_DOC_ODL = odl.get("d4p1:POS_DOC_ODL");
		UNITA_GESTORE = odl.get("d4p1:UNITA_GESTORE");
		VALORE_DESTCNT = odl.get("d4p1:VALORE_DESTCNT") != "" ? odl.get("d4p1:VALORE_DESTCNT") : "null";
		VALORE_DESTCNT_EUR = odl.get("d4p1:VALORE_DESTCNT_EUR") != "" ? odl.get("d4p1:VALORE_DESTCNT_EUR") : "null";
	}

	@Override
	public String generaQuery() {
		return "SELECT '"+DATA_CREAZIONE_ODL+"','"+DESCRIZIONE_ESTESA.replace("'", "''").replace("&", "\\&")+"','"+DES_CONT_ODL.replace("'", "''").replace("&", "\\&")+"','"+DIVISA_ORDINE_ACQ+"','"+FORNITORE_ENI+"','"+ID_SIS_SORGENTE+"','"+INTESTAZIONE_ODL+"','"+NUM_DOC+"','"+NUM_DOC_ODL+"','"+POS_DOC_ODL+"','"+UNITA_GESTORE.replace("'", "''").replace("&", "\\&")+"',"+VALORE_DESTCNT+","+VALORE_DESTCNT_EUR+" FROM DUAL ";
	}
	
}
