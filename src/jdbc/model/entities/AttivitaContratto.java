package jdbc.model.entities;

import java.util.Map;

public class AttivitaContratto implements Entita{
	
public String ID_SIS_SORGENTE,NUM_DOC,POS_DOC,NUM_PACCHETTO,NUM_RIGA,NOME_CATALOGO_PRESTAZIONI,DESCRIZIONE_MEDIA,QUANTITA,SOCICHIAVE_DIVISA,IMPORTO_TOTALE
;
	

	public AttivitaContratto(Map<String,String> anag) {
		ID_SIS_SORGENTE = anag.get("d4p1:ID_SIS_SORGENTE");
		NUM_DOC = anag.get("d4p1:NUM_DOC");
		POS_DOC = anag.get("d4p1:POS_DOC");
		NUM_PACCHETTO = anag.get("d4p1:NUM_PACCHETTO");
		NUM_RIGA = anag.get("d4p1:NUM_RIGA");
		NOME_CATALOGO_PRESTAZIONI = anag.get("d4p1:NOME_CATALOGO_PRESTAZIONI");
		DESCRIZIONE_MEDIA = anag.get("d4p1:DESCRIZIONE_MEDIA");
		QUANTITA = anag.get("d4p1:QUANTITA");
		SOCICHIAVE_DIVISA = anag.get("d4p1:SOCICHIAVE_DIVISA");
		IMPORTO_TOTALE = anag.get("d4p1:IMPORTO_TOTALE");
	}

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT '"+ID_SIS_SORGENTE+"','"+NUM_DOC+"','"+POS_DOC+"','"+NUM_PACCHETTO+"','"+NUM_RIGA+"','"+NOME_CATALOGO_PRESTAZIONI+"','"+DESCRIZIONE_MEDIA+"','"+QUANTITA+"','"+SOCICHIAVE_DIVISA+"','"+IMPORTO_TOTALE+"' FROM DUAL ";
	}
}