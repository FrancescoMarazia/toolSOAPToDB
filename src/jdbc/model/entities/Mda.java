package jdbc.model.entities;

import java.util.Map;

public class Mda implements Entita{

	public String DATA_REGCONT_MDA, DES_CONT_MDA, DIVISA_ORDINE_ACQ, ESERCIZIO_EM, ID_SIS_SORGENTE, MDA_STIMA, NUM_DOC_ODL,
			NUM_MDA, NUM_POS_DESCONT, POS_DOC_ODL, VAL_MDA_DIVDOC, VAL_MDA_EUR;

	public Mda(Map<String, String> mda) {
		DATA_REGCONT_MDA = mda.get("d4p1:DATA_REGCONT_MDA");
		DES_CONT_MDA = mda.get("d4p1:DES_CONT_MDA");
		DIVISA_ORDINE_ACQ = mda.get("d4p1:DIVISA_ORDINE_ACQ");
		ESERCIZIO_EM = mda.get("d4p1:ESERCIZIO_EM")!= "" ? mda.get("d4p1:ESERCIZIO_EM") : "null";
		ID_SIS_SORGENTE = mda.get("d4p1:ID_SIS_SORGENTE");
		MDA_STIMA = mda.get("d4p1:MDA_STIMA");
		NUM_DOC_ODL = mda.get("d4p1:NUM_DOC_ODL");
		NUM_MDA = mda.get("d4p1:NUM_MDA");
		NUM_POS_DESCONT = mda.get("d4p1:NUM_POS_DESCONT") != "" ? mda.get("d4p1:NUM_POS_DESCONT") : "null";
		POS_DOC_ODL = mda.get("d4p1:POS_DOC_ODL");
		VAL_MDA_DIVDOC = mda.get("d4p1:VAL_MDA_DIVDOC") != "" ? mda.get("d4p1:NUM_POS_DESCONT") : "null";
		VAL_MDA_EUR = mda.get("d4p1:VAL_MDA_EUR") != "" ? mda.get("d4p1:VAL_MDA_EUR") : "null";
	}

	@Override
	public String generaQuery() {
		
		return "SELECT '"+ DATA_REGCONT_MDA + "','" + DES_CONT_MDA + "','" + DIVISA_ORDINE_ACQ + "',"+0+",'"+ ID_SIS_SORGENTE + "','" + MDA_STIMA + "','" + NUM_DOC_ODL + "','" + NUM_MDA + "'," + NUM_POS_DESCONT + ",'" + POS_DOC_ODL + "'," + VAL_MDA_DIVDOC + "," + VAL_MDA_EUR +" FROM DUAL ";
	}
}