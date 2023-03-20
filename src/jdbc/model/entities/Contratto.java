package jdbc.model.entities;

import java.util.Map;

public class Contratto implements Entita{
	
	
	String ID_SIS_SORGENTE,
	NUM_DOC,
	OGGETTO_CONTR,
	ID_CONTRATTO_APCH,
	NUM_REVISIONE,
	INIZIO_VALIDITA,
	FINE_VALIDITA,
	DIVISA_ORDINE_ACQ,
	POS_DOC,
	TIPO_POSIZIONE,
	TESTO_BREVE,
	DATA_CONSEGNA,
	VAL_POS_DOC_EURO,
	IMPORTO_EM_DIVISA_INTERNA,
	VAL_POSIZIONE_DOCUMENTO,
	IMPORTO_EM_DIVISA_DOC,
	DIVISIONE,
	OGG_MERCEOLOGICO_CHIAVE_EST,
	UNITA_GESTORE,
	FORNITORE_ENI,
	DESCRIZIONE_ESTESA,
	TIPO_DOC_ACQUISTI,
	NUM_ACCORDO_QUADRO,
	SOCIETA,
	VAL_TESTATA_EURO,
	VAL_AMM_RESIDUA_EUR,
	VALORE_TESTATA,
	VAL_AMM_RESIDUA;
	
	public Contratto(Map<String,String> contratto) {
		
		ID_SIS_SORGENTE = contratto.get("d4p1:ID_SIS_SORGENTE");
		NUM_DOC = contratto.get("d4p1:NUM_DOC");
		OGGETTO_CONTR = contratto.get("d4p1:OGGETTO_CONTR");
		ID_CONTRATTO_APCH = contratto.get("d4p1:ID_CONTRATTO_APCH");
		NUM_REVISIONE = contratto.get("d4p1:NUM_REVISIONE") != null ? contratto.get("d4p1:NUM_REVISIONE") : "null";
		INIZIO_VALIDITA = contratto.get("d4p1:INIZIO_VALIDITA");
		FINE_VALIDITA = contratto.get("d4p1:FINE_VALIDITA");
		DIVISA_ORDINE_ACQ = contratto.get("d4p1:DIVISA_ORDINE_ACQ");
		POS_DOC = contratto.get("d4p1:POS_DOC");
		TIPO_POSIZIONE = contratto.get("d4p1:TIPO_POSIZIONE");
		TESTO_BREVE = contratto.get("d4p1:TESTO_BREVE");
		DATA_CONSEGNA = contratto.get("d4p1:DATA_CONSEGNA");
		VAL_POS_DOC_EURO = contratto.get("d4p1:VAL_POS_DOC_EURO") != null ? contratto.get("d4p1:VAL_POS_DOC_EURO") : "null";
		IMPORTO_EM_DIVISA_INTERNA = contratto.get("d4p1:IMPORTO_EM_DIVISA_INTERNA") != null ? contratto.get("d4p1:IMPORTO_EM_DIVISA_INTERNA") : "null";
		VAL_POSIZIONE_DOCUMENTO = contratto.get("d4p1:VAL_POSIZIONE_DOCUMENTO") != null ? contratto.get("d4p1:VAL_POSIZIONE_DOCUMENTO") : "null";
		IMPORTO_EM_DIVISA_DOC = contratto.get("d4p1:IMPORTO_EM_DIVISA_DOC") != null ? contratto.get("d4p1:IMPORTO_EM_DIVISA_DOC") : "null";
		DIVISIONE = contratto.get("d4p1:DIVISIONE");
		OGG_MERCEOLOGICO_CHIAVE_EST = contratto.get("d4p1:OGG_MERCEOLOGICO_CHIAVE_EST");
		UNITA_GESTORE = contratto.get("d4p1:UNITA_GESTORE");
		FORNITORE_ENI = contratto.get("d4p1:FORNITORE_ENI");
		DESCRIZIONE_ESTESA = contratto.get("d4p1:DESCRIZIONE_ESTESA");
		TIPO_DOC_ACQUISTI = contratto.get("d4p1:TIPO_DOC_ACQUISTI");
		NUM_ACCORDO_QUADRO = contratto.get("d4p1:NUM_ACCORDO_QUADRO");
		SOCIETA = contratto.get("d4p1:SOCIETA");
		VAL_TESTATA_EURO = contratto.get("d4p1:VAL_TESTATA_EURO") != null ? contratto.get("d4p1:VAL_TESTATA_EURO") : "null";
		VAL_AMM_RESIDUA_EUR = contratto.get("d4p1:VAL_AMM_RESIDUA_EUR") != null ? contratto.get("d4p1:VAL_AMM_RESIDUA_EUR") : "null";
		VALORE_TESTATA = contratto.get("d4p1:VALORE_TESTATA") != null ? contratto.get("d4p1:VALORE_TESTATA") : "null";
		VAL_AMM_RESIDUA = contratto.get("d4p1:VAL_AMM_RESIDUA") != null ? contratto.get("d4p1:VAL_AMM_RESIDUA") : "null";
	}
	

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT '"+ID_SIS_SORGENTE+"','"+NUM_DOC+"','"+OGGETTO_CONTR+"','"+ID_CONTRATTO_APCH+"',"+NUM_REVISIONE+",'"+INIZIO_VALIDITA+"','"+FINE_VALIDITA+"','"+DIVISA_ORDINE_ACQ+"','"+POS_DOC+"','"+TIPO_POSIZIONE+"','"+TESTO_BREVE+"','"+DATA_CONSEGNA+"',"+VAL_POS_DOC_EURO+","+IMPORTO_EM_DIVISA_INTERNA+","+VAL_POSIZIONE_DOCUMENTO+","+IMPORTO_EM_DIVISA_DOC+",'"+DIVISIONE+"','"+OGG_MERCEOLOGICO_CHIAVE_EST+"','"+UNITA_GESTORE+"','"+FORNITORE_ENI+"','"+DESCRIZIONE_ESTESA+"','"+TIPO_DOC_ACQUISTI+"','"+NUM_ACCORDO_QUADRO+"','"+SOCIETA+"',"+VAL_TESTATA_EURO+","+VAL_AMM_RESIDUA_EUR+","+VALORE_TESTATA+","+VAL_AMM_RESIDUA+" FROM DUAL ";
	}

	

}
