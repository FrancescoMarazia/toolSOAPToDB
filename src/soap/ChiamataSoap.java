package soap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
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

import constants.TypeChiamata;
import jdbc.model.entities.AnagraficaPlant;
import jdbc.model.entities.Contratto;
import jdbc.model.entities.Fattura;
import jdbc.model.entities.Entita;
import jdbc.model.entities.Mda;
import jdbc.model.entities.OrdineDiLavoro;
import jdbc.model.entities.PartnerContratto;

public class ChiamataSoap{
	
	public TypeChiamata typeChiamata;
	private LocalDate startDate;
	private LocalDate currentDate;
	
	public ChiamataSoap(TypeChiamata typeChiamata,LocalDate startDate, LocalDate currentDate){
		
		this.typeChiamata = typeChiamata;
		
		if(this.typeChiamata == TypeChiamata.SALUTE_CONTRATTI_T601 || this.typeChiamata == TypeChiamata.SALUTE_FATTURE_T603 || this.typeChiamata == TypeChiamata.SALUTE_ORDINE_DI_LAVORO_T600 || this.typeChiamata == TypeChiamata.SALUTE_PARTNER_CONTRATTO_T605 )
		
		{
			this.startDate = startDate;
			this.currentDate = currentDate;
		}
	}
	
	public List<Entita> getData() {
		
		System.out.println( "Dates range: "+this.startDate +" "+ this.currentDate);
		
		String xml = "";
		
		List<Entita> objectsList = new ArrayList<Entita>();
		String parentNodeName = "";
		
		switch(this.typeChiamata) {
		case SALUTE_ANAGRAFICA_PLANT_T606: 
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> <soapenv:Header/> <soapenv:Body> <tem:GetAnagraficaPlant/> </soapenv:Body> </soapenv:Envelope>";
			parentNodeName = "d4p1:RecordAnagraficaPlant";
			break;
		case SALUTE_ORDINE_DI_LAVORO_T600: 
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:pdh=\"http://schemas.datacontract.org/2004/07/Pdh.Models\"> <soapenv:Header/> <soapenv:Body> <tem:GetOdl> <tem:req> <pdh:EndDate>"+this.currentDate+"</pdh:EndDate> <pdh:StartDate>"+this.startDate+"</pdh:StartDate> </tem:req> </tem:GetOdl> </soapenv:Body> </soapenv:Envelope>";
			parentNodeName = "d4p1:RecordOdl";
			break;
		case SALUTE_MDA_T602: 
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> <soapenv:Header/> <soapenv:Body> <tem:GetMda/> </soapenv:Body> </soapenv:Envelope>";
			parentNodeName = "d4p1:RecordMda";
			break;
		case SALUTE_CONTRATTI_T601: 
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:pdh=\"http://schemas.datacontract.org/2004/07/Pdh.Models\"> <soapenv:Header/> <soapenv:Body> <tem:GetContratti> <tem:req>  <pdh:EndDate>"+this.currentDate+"</pdh:EndDate>  <pdh:StartDate>"+this.startDate+"</pdh:StartDate> </tem:req> </tem:GetContratti> </soapenv:Body> </soapenv:Envelope>";
			parentNodeName = "d4p1:RecordContratti";
			break;
		  case SALUTE_FATTURE_T603:
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:pdh=\"http://schemas.datacontract.org/2004/07/Pdh.Models\"><soapenv:Header/><soapenv:Body><tem:GetFatture><tem:req><pdh:EndDate>"+this.currentDate+"</pdh:EndDate><pdh:StartDate>"+this.startDate+"</pdh:StartDate></tem:req></tem:GetFatture></soapenv:Body></soapenv:Envelope>";
			parentNodeName = "d4p1:RecordFatture";
		  	break;
		 
		case SALUTE_PARTNER_CONTRATTO_T605: 
			xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:pdh=\"http://schemas.datacontract.org/2004/07/Pdh.Models\"> <soapenv:Header/> <soapenv:Body> <tem:GetPartnerContratto> <tem:req> <pdh:EndDate>"+this.currentDate+"</pdh:EndDate> <pdh:StartDate>"+this.startDate+"</pdh:StartDate> </tem:req> </tem:GetPartnerContratto> </soapenv:Body> </soapenv:Envelope>";
			parentNodeName = "d4p1:RecordPartnerContratto";
			break;
		}



		try {

			String url = "https://wap-wwe-ictd-001-pdh.azurewebsites.net/PdhSoapService.asmx";
			URL obj = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");
			String credentials = "af9d82ef-d23f-49d0-9d82-176ab73aa2cc:DYO8Q~mhGxR6qOmVoZ8~tSvpSR8Nks7JjMlgkdk2";
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
					
					switch(this.typeChiamata) {
					case SALUTE_ANAGRAFICA_PLANT_T606: 
						AnagraficaPlant anag = new AnagraficaPlant(callObj);
						objectsList.add(anag);
						break;
					case SALUTE_CONTRATTI_T601: 
						Contratto contratto = new Contratto(callObj);
						objectsList.add(contratto);
						break;
					case SALUTE_ORDINE_DI_LAVORO_T600: 
						OrdineDiLavoro odl = new OrdineDiLavoro(callObj);
						objectsList.add(odl);
						break;
					case SALUTE_MDA_T602: 
						Mda mda = new Mda(callObj);
						objectsList.add(mda);
						break;
					case SALUTE_FATTURE_T603:
						Fattura fattura = new Fattura(callObj);
					    objectsList.add(fattura);
					    break;	 
					case SALUTE_PARTNER_CONTRATTO_T605: 
						PartnerContratto partnerC = new PartnerContratto(callObj);
						objectsList.add(partnerC);
						break;
					}
				}
			}

			in.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return objectsList;
	}
	
	public boolean hasDates() {
		return (this.startDate != null && this.currentDate != null);
		
	}

	
	
}
