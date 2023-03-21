package soap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jdbc.model.entities.Attachment;
import jdbc.model.entities.Entita;

public class AttachmentCall {
	
	private String[] url = new String[] {};
	private String[][] credentials;
	
	AttachmentCall(String id_sis_sorgente){
		switch(id_sis_sorgente) {
		case "03": 
			// IESS
			break;

		case "R1":
			// SITAM 3
			break;

		case "22":
			// SITAM 3 (Raffineria di Gela)
			break;
		case "56":
			// SSM
			break;

		case "R0":
			this.url = new String[]{"http://s02srv000qo.ad02.eni.intranet:8052/sap/bc/srt/wsdl/bndg_6C5CED63C5D8B74DE10000000A6726AB/wsdl11/allinone/ws_policy/document?sap-client=201","http://s02log00bdp.hosts.eni.intranet:8051/sap/bc/srt/wsdl/flv_10002A111AD1/bndg_url/sap/bc/srt/rfc/sap/zcont_slt/201/zcont_slt/zcont_slt_binding?sap-client=201"};
			this.credentials = new String[][] {{"TECHSLT","4898er3j"},{"TECHSLT","TECHSLT"}};
			break;

		case "18":
			// Polimeri Europa (Versalis)
			break;
		}
	}
	
	public List<Entita> getAttachments(String ebeln) {
		
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\"><soapenv:Header/><soapenv:Body><urn:ZATTA_CONT_SLT><IT_EBELN><item><EBELN>"+ebeln+"</EBELN></item></IT_EBELN></urn:ZATTA_CONT_SLT></soapenv:Body></soapenv:Envelope>";
		
		String parentNodeName = "item";

		List<Entita> items = new ArrayList<Entita>();
		
		for(int u=0; u < this.url.length; u++)
		{
			try {

			URL obj = new URL(this.url[u]);

			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");
			String credentials = this.credentials[u][0]+":"+this.credentials[u][1];
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(credentials.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);

			conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

			conn.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();

			String responseStatus = conn.getResponseMessage();
			System.out.println(responseStatus);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(conn.getInputStream());
			doc.getDocumentElement().normalize();

			
			NodeList nodeList = doc.getElementsByTagName(parentNodeName);

			for (int itr = 0; itr < nodeList.getLength(); itr++) {

				Node node = nodeList.item(itr);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					
					NodeList childNodes = eElement.getChildNodes();
					
					Map<String, String> callObj = new HashMap<String, String>();
					
					for(int cn = 0;cn < childNodes.getLength();cn++) {
						
						Node childNode = childNodes.item(cn);
						
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							
							String key = childNode.getNodeName().trim();
							String value = childNode.getTextContent().trim();
							
							callObj.put(key, value);
							
						}
					}
					
					Attachment atta = new Attachment(callObj);
					items.add(atta);
				}
			}

			in.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

		return items;
	}
}
