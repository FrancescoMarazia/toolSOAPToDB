package jdbc.model.entities;

import java.util.Map;

public class Attachment implements Entita{
	
	public String EBELN, FILENAME, EXTENSION, DATE, AUTHOR, FIELDKEY, ID , SERVIZIO;

	public Attachment(Map<String,String> contratto) {
		
		EBELN = contratto.get("EBELN");
		FILENAME = contratto.get("FILENAME");
		EXTENSION = contratto.get("EXTENSION");
		DATE = contratto.get("DATE");
		AUTHOR = contratto.get("AUTHOR");
		FIELDKEY = contratto.get("FIELDKEY");
		ID = contratto.get("EBELN") +"-"+contratto.get("FIELDKEY");
		SERVIZIO = contratto.get("SERVIZIO");
		
	}
	

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT "+EBELN+",'"+FILENAME+"','"+EXTENSION+"',TO_DATE('"+DATE+"','YYYY-MM-DD'),'"+AUTHOR+"','"+FIELDKEY+"','"+EBELN+"-"+FIELDKEY+"','"+SERVIZIO+"' FROM DUAL ";

	}
}
