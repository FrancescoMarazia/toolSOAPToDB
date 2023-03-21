package jdbc.model.entities;

import java.util.Map;

public class Attachment implements Entita{
	
	String EBELN, FILENAME, EXTENSION, DATE, AUTHOR, FIELDKEY;

	public Attachment(Map<String,String> contratto) {
		
		EBELN = contratto.get("EBELN");
		FILENAME = contratto.get("FILENAME");
		EXTENSION = contratto.get("EXTENSION");
		DATE = contratto.get("DATE");
		AUTHOR = contratto.get("AUTHOR");
		FIELDKEY = contratto.get("FIELDKEY");
		
	}
	

	@Override
	public String generaQuery() {
		// TODO Auto-generated method stub
		return "SELECT "+EBELN+",'"+FILENAME+"','"+EXTENSION+"','"+DATE+"','"+AUTHOR+"','"+FIELDKEY+"','"+EBELN+"-"+FIELDKEY+"' FROM DUAL ";

	}
}
