package jdbc.model.entities;

import java.util.Map;

public class Fattura implements Entita{
	
	String ID_SIS_SORGENTE;
	String NUM_FATTURA;
	String ESERC_FATTURA;
	String POS_FATTURA;
	String NUM_MDA;
	String DES_CONT_FATT;
	String NUM_FATTURA_EST;
	String FLAG_ADD_SUCC;
	String DATA_REG_FATTURA;
	String IMPORTO_POS_FATTURA;
	String DIVISA_FATTURA;
	String VAL_POS_FATT_EUR;
	String NUM_DOC_ODL;
	String POS_DOC_ODL;
	String CAMBI_FATTURA;
	String CAT_POS_DOC_ACQUISTI;
	String CENTRO_DI_COSTO;
	String CENTRO_DI_RESPONSABILITA;
	String CONTO_CO_GE;
	String CHIAVE_INTERNA_OGG_IMM;
	String CONTROLLING_AREA;
	String CREDITORE_DIFFERENTE;
	String DATA_INSERIMENTO;
	String ELEMENTO_WBS;
	String FLAG_DARE_AVERE;
	String IMPORTO_DIVISA_DOC;
	String NUM_BOLLA;
	String NUM_CICLO;
	String NUM_PRINCPL_CESPITE;
	String NUM_PROGRESSIVO;
	String NUM_SECONDARIO_CESPITE;
	String NUM_SEGM_PROFIT;
	String OGGETTO_DI_COSTO;
	String PARTNAME;
	String ORA_CREAZIONE;
	String ORDINE_CO;
	String PROFIT_CENTER;
	String QUANTITA;
	String SOCIETA;
	String TESTO_POS_FATTURA;
	String TIPO_DES_CONT;
	String NUM_ORDINE_ACQ;
	String USER_FATTURA;
	String TIPO_DOC_FATTURA;
	String DATA_FATTURA;

	
	
	
	public Fattura(Map<String,String> fattura) {

		ID_SIS_SORGENTE = fattura.get("d4p1:ID_SIS_SORGENTE");
		NUM_FATTURA = fattura.get("d4p1:NUM_FATTURA");
		ESERC_FATTURA = fattura.get("d4p1:ESERC_FATTURA") != null ? fattura.get("d4p1:ESERC_FATTURA") : "null";
		POS_FATTURA = fattura.get("d4p1:POS_FATTURA");
		NUM_MDA = fattura.get("d4p1:NUM_MDA");
		DES_CONT_FATT = fattura.get("d4p1:DES_CONT_FATT");
		NUM_FATTURA_EST = fattura.get("d4p1:NUM_FATTURA_EST");
		FLAG_ADD_SUCC = fattura.get("d4p1:FLAG_ADD_SUCC");
		DATA_REG_FATTURA = fattura.get("d4p1:DATA_REG_FATTURA");
		IMPORTO_POS_FATTURA = fattura.get("d4p1:IMPORTO_POS_FATTURA") != null ? fattura.get("d4p1:IMPORTO_POS_FATTURA") : "null";
		DIVISA_FATTURA = fattura.get("d4p1:DIVISA_FATTURA");
		VAL_POS_FATT_EUR = fattura.get("d4p1:VAL_POS_FATT_EUR") != null ? fattura.get("d4p1:VAL_POS_FATT_EUR") : "null";
		NUM_DOC_ODL = fattura.get("d4p1:NUM_DOC_ODL");
		POS_DOC_ODL = fattura.get("d4p1:POS_DOC_ODL");
		CAMBI_FATTURA = fattura.get("d4p1:CAMBI_FATTURA");
		CAT_POS_DOC_ACQUISTI = fattura.get("d4p1:CAT_POS_DOC_ACQUISTI");
		CENTRO_DI_COSTO = fattura.get("d4p1:CENTRO_DI_COSTO");
		CENTRO_DI_RESPONSABILITA = fattura.get("d4p1:CENTRO_DI_RESPONSABILITA");
		CONTO_CO_GE = fattura.get("d4p1:CONTO_CO_GE");
		CHIAVE_INTERNA_OGG_IMM = fattura.get("d4p1:CHIAVE_INTERNA_OGG_IMM");
		CONTROLLING_AREA = fattura.get("d4p1:CONTROLLING_AREA");
		CREDITORE_DIFFERENTE = fattura.get("d4p1:CREDITORE_DIFFERENTE");
		DATA_INSERIMENTO = fattura.get("d4p1:DATA_INSERIMENTO");
		ELEMENTO_WBS = fattura.get("d4p1:ELEMENTO_WBS");
		FLAG_DARE_AVERE = fattura.get("d4p1:FLAG_DARE_AVERE");
		IMPORTO_DIVISA_DOC = fattura.get("d4p1:IMPORTO_DIVISA_DOC") != null ? fattura.get("d4p1:IMPORTO_DIVISA_DOC") : "null";
		NUM_BOLLA = fattura.get("d4p1:NUM_BOLLA");
		NUM_CICLO = fattura.get("d4p1:NUM_CICLO");
		NUM_PRINCPL_CESPITE = fattura.get("d4p1:NUM_PRINCPL_CESPITE");
		NUM_PROGRESSIVO = fattura.get("d4p1:NUM_PROGRESSIVO");
		NUM_SECONDARIO_CESPITE = fattura.get("d4p1:NUM_SECONDARIO_CESPITE");
		NUM_SEGM_PROFIT = fattura.get("d4p1:NUM_SEGM_PROFIT");
		OGGETTO_DI_COSTO = fattura.get("d4p1:OGGETTO_DI_COSTO");
		PARTNAME = fattura.get("d4p1:PARTNAME");
		ORA_CREAZIONE = fattura.get("d4p1:ORA_CREAZIONE");
		ORDINE_CO = fattura.get("d4p1:ORDINE_CO");
		PROFIT_CENTER = fattura.get("d4p1:PROFIT_CENTER");
		QUANTITA = fattura.get("d4p1:QUANTITA") != null ? fattura.get("d4p1:QUANTITA") : "null";
		SOCIETA = fattura.get("d4p1:SOCIETA");
		TESTO_POS_FATTURA = fattura.get("d4p1:TESTO_POS_FATTURA");
		TIPO_DES_CONT = fattura.get("d4p1:TIPO_DES_CONT");
		NUM_ORDINE_ACQ = fattura.get("d4p1:NUM_ORDINE_ACQ");
		USER_FATTURA = fattura.get("d4p1:USER_FATTURA");
		TIPO_DOC_FATTURA = fattura.get("d4p1:TIPO_DOC_FATTURA");
		DATA_FATTURA = fattura.get("d4p1:DATA_FATTURA");
	}

	



	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT '"+ID_SIS_SORGENTE+"','"+NUM_FATTURA+"',"+ESERC_FATTURA+","+POS_FATTURA+",'"+NUM_MDA+"','"+DES_CONT_FATT+"','"+NUM_FATTURA_EST+"','"+FLAG_ADD_SUCC+"','"+DATA_REG_FATTURA+"',"+IMPORTO_POS_FATTURA+",'"+DIVISA_FATTURA+"',"+VAL_POS_FATT_EUR+",'"+NUM_DOC_ODL+"','"+POS_DOC_ODL+"','"+CAMBI_FATTURA+"','"+CAT_POS_DOC_ACQUISTI+"','"+CENTRO_DI_COSTO+"','"+CENTRO_DI_RESPONSABILITA+"','"+CONTO_CO_GE+"','"+CHIAVE_INTERNA_OGG_IMM+"','"+CONTROLLING_AREA+"','"+CREDITORE_DIFFERENTE+"','"+DATA_INSERIMENTO+"','"+ELEMENTO_WBS+"','"+FLAG_DARE_AVERE+"',"+IMPORTO_DIVISA_DOC+",'"+NUM_BOLLA+"','"+NUM_CICLO+"','"+NUM_PRINCPL_CESPITE+"','"+NUM_PROGRESSIVO+"','"+NUM_SECONDARIO_CESPITE+"','"+NUM_SEGM_PROFIT+"','"+OGGETTO_DI_COSTO+"','"+PARTNAME+"','"+ORA_CREAZIONE+"','"+ORDINE_CO+"','"+PROFIT_CENTER+"',"+QUANTITA+",'"+SOCIETA+",'"+TESTO_POS_FATTURA+"','"+TIPO_DES_CONT+"','"+NUM_ORDINE_ACQ+"','"+USER_FATTURA+"','"+TIPO_DOC_FATTURA+"','"+DATA_FATTURA+"' FROM DUAL ";
	}

}
