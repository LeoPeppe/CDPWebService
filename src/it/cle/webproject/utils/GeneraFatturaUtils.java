package it.cle.webproject.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import it.cle.project.dto.fattura.DatiInputFatturaDTO;
import it.cle.project.dto.fattura.FatturaResponseDTO;
import it.cle.project.service.ComponiFatturaService;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneraFatturaUtils.
 */
public class GeneraFatturaUtils {

	/** The logger. */
	protected Logger logger = Logger.getLogger(GeneraFatturaUtils.class);
	
/**
 * Metodo che compone l'xml della fattura elettronica a partire dai dati in input	.
 *
 * @param datiInputFatturaDTO the dati input fattura dto
 * @param componiFatturaService the componi fattura service
 * @return Restituisce un DTO contentente nel campo "stato" l'esito della composizione dell'xml, pu&ograve; assumere valori<br/>
 * "OK" o "KO":
 * 
 * <li>se "OK" nel campo flussoFattura è presente l'xml prodotto;</li>
 * <li>se "KO" nel campo messaggioErrore è presente l'errore avvenuto.</li>
 */
	public FatturaResponseDTO getFatturaXML(DatiInputFatturaDTO datiInputFatturaDTO, ComponiFatturaService componiFatturaService) {
		 
		
		FatturaResponseDTO fatturaRespDTO = new FatturaResponseDTO();		
		String xmlInput = componiFatturaService.componiFatturaXML(datiInputFatturaDTO);
		try { 
		 if(xmlInput.startsWith("<esito>ERRORE</esito>")) {
			 String exMessage = xmlInput.substring(xmlInput.indexOf("<dettagli>")+10, xmlInput.indexOf("</dettagli>"));
			 fatturaRespDTO.setStato("KO");
			 fatturaRespDTO.setMessaggioErrore(exMessage);
			 throw new Exception(exMessage);

		 } else {
			 fatturaRespDTO.setStato("OK");
			 fatturaRespDTO.setFlussoFattura(xmlInput);
		 }
		} catch(Exception e) {
			 fatturaRespDTO.setStato("KO");
			 fatturaRespDTO.setMessaggioErrore(e.getMessage());
			 logger.error("Errore nella chiamata al servizio di generazione della fattura: ");
			 logger.error(it.cle.project.utils.Utils.getStackTrace(e));
		}
		 return fatturaRespDTO;
	}
	
	/**
	 * Genera fattura.
	 *
	 * @param datiInputFatturaDTO the dati input fattura dto
	 * @param componiFatturaService the componi fattura service
	 * @return the fattura response dto
	 */
	public FatturaResponseDTO generaFattura(DatiInputFatturaDTO datiInputFatturaDTO, ComponiFatturaService componiFatturaService){
		
	    
		//Code to make a webservice HTTP request
		final int statusOK=200;
		
		 FatturaResponseDTO fatturaRespDTO = new FatturaResponseDTO();
		try 
	    {
		
/* **** Composizione XML input per Fattura ***** */
		 String xmlPre ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:flus=\"http://pd20.parsec326.it/FlussoGeneraFatturaXML/\">\n"+
					"<soapenv:Header/>"+
						"<soapenv:Body>"+
						"<flus:GeneraFattura>" +
						"<flus:fattura>";
		 String xmlPost = "</flus:fattura>"+
						"</flus:GeneraFattura>"+
				   "</soapenv:Body>"+
					"</soapenv:Envelope>";
		 String xmlInput = componiFatturaService.componiFatturaXML(datiInputFatturaDTO);
		 
		 if(xmlInput.startsWith("<esito>ERRORE</esito>")) {
			 String exMessage = xmlInput.substring(xmlInput.indexOf("<esito>ERRORE</esito>"), xmlInput.indexOf("</dettagli>"));
			 fatturaRespDTO.setStato("KO");
			 fatturaRespDTO.setMessaggioErrore(exMessage);
			 throw new Exception(exMessage);

		 } 
		 	String endPointFattura = new String(it.cle.project.utils.Utils.getPropertyFromConfig("urlFattura", ""));
			
			URL urlFattura = new URL(endPointFattura);
			logger.info("endpoint metodo Genera Fattura: " + endPointFattura);
			HttpURLConnection connection = (HttpURLConnection) urlFattura.openConnection();
		 
		 
		 	connection.setDoOutput(true); 
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("charset", "UTF-8");
			
			connection.setUseCaches(false);  
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(xmlInput);
			wr.flush(); 
			wr.close(); 
		
			int responseCodeParsec = connection.getResponseCode();
			logger.info("responseCode servizio Genera Fattura "+responseCodeParsec);
			
			if(responseCodeParsec ==statusOK){
				BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer responseXML = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                	responseXML.append(inputLine);
                }
                
                logger.info("ResponseXML  "+responseXML.toString());
                
                String responseString= responseXML.toString();
                String xmlResponse=responseString.substring(responseString.indexOf("<GeneraFatturaResult>"), responseString.indexOf("</GeneraFatturaResponse>"));

                
                logger.info("xmlResponse  "+xmlResponse);
                
                 fatturaRespDTO = FatturaResponseDTO.unmarshallReportPrestDTO(xmlResponse);
                
                in.close();
                
                if(fatturaRespDTO.getStato().equals("OK")) {
                	
           			 /* *** Salvataggio Fattura Inviata ***/
           			 String resultSalvataggioFattura = componiFatturaService.salvaFatturaGenerata();
           			 if(resultSalvataggioFattura.equals("OK")) {
           				 logger.info("Fattura salvata correttamente");
           			 } else {
           				 logger.error(resultSalvataggioFattura);
           			 }
           			 xmlInput = xmlPre.concat(xmlInput).concat(xmlPost);
           			 logger.info("xml fattura prodotto: ");
           			 logger.info(xmlInput);
           		 
                }
             

		}
	    }catch (Exception e) {
			 fatturaRespDTO.setStato("KO");
			 fatturaRespDTO.setMessaggioErrore(e.getMessage());
			 logger.error("Errore nella chiamata al servizio di generazione della fattura: ");
			 logger.error(it.cle.project.utils.Utils.getStackTrace(e));
			 
		}
		return fatturaRespDTO;
	}
}
