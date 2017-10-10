package it.cle.webproject.ws;



import it.cle.project.config.AppConfig;
import it.cle.project.dto.PrestazioniRequestDTO;
import it.cle.project.dto.ReportPrestazioniDTO;
import it.cle.project.dto.ReportPrestazioniRequestDTO;
import it.cle.project.dto.ReportPrestazioniResponseDTO;
import it.cle.project.dto.fattura.DatiInputFatturaDTO;
import it.cle.project.dto.fattura.FatturaElettronicaDTO;
import it.cle.project.dto.fattura.FatturaResponseDTO;
import it.cle.project.model.DatiAssistito;
import it.cle.project.model.IndiceADL;
import it.cle.project.model.IndiceBarthelMobilita;
import it.cle.project.model.IndiceExtonSmith;
import it.cle.project.model.IndiceNPI;
import it.cle.project.model.IndiceSPMSQ;
import it.cle.project.model.IndiceSupportoReteSociale;
import it.cle.project.model.Pai;
import it.cle.project.model.Pai_Prestazioni;
import it.cle.project.model.Prestazioni;
import it.cle.project.model.RisultatiIndici;
import it.cle.project.model.composti.npi.AgitazioneAggressivita;
import it.cle.project.model.composti.npi.Allucinazioni;
import it.cle.project.model.composti.npi.Ansia;
import it.cle.project.model.composti.npi.ApatiaIndifferenza;
import it.cle.project.model.composti.npi.AttivitaMotoriaAberrante;
import it.cle.project.model.composti.npi.Deliri;
import it.cle.project.model.composti.npi.DepressioneDisforia;
import it.cle.project.model.composti.npi.Disinibizione;
import it.cle.project.model.composti.npi.DisturbiAlimentazione;
import it.cle.project.model.composti.npi.DisturbiSonno;
import it.cle.project.model.composti.npi.EuforiaEsaltazione;
import it.cle.project.model.composti.npi.IrritabilitaLabilita;
import it.cle.project.service.ComponiFatturaService;
import it.cle.project.service.DatiAssistitoService;
import it.cle.project.service.IndiceADLService;
import it.cle.project.service.IndiceBarthelMobilitaService;
import it.cle.project.service.IndiceExtonSmithService;
import it.cle.project.service.IndiceNPIService;
import it.cle.project.service.IndiceSPMSQService;
import it.cle.project.service.IndiceSupportoReteSocialeService;
import it.cle.project.service.InterventoService;
import it.cle.project.service.PaiService;
import it.cle.project.service.Pai_PrestazioniService;
import it.cle.project.service.PrestazioniService;
import it.cle.project.service.RisultatiIndiciService;
import it.cle.webproject.dto.FatturaXMLDTO;
import it.cle.webproject.dto.ResultDTO;
import it.cle.webproject.dto.ResultDTOXML;
import it.cle.webproject.utils.GeneraFatturaUtils;
import it.cle.webproject.utils.Utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;



// TODO: Auto-generated Javadoc
/**
 * The Class WebServicesRepository.
 *
 * @author ggermano
 * Classe contenente i servizi esposti per Indice ADL, Indice Barthel Mobilita
 * Indice Exton Smith, Indice SPMSQ
 */
@Controller
public class WebServicesRepository {
	
//	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	final static String pathSviluppo="/home/leo/Scrivania/Lavoro/Fertilità terreno/png/savedFile.png";
	final static String  pathProduzione="/home/apache-tomcat-7.0.42/webapps/ROOT/immaginiScaricateServizio/savedFile.png";

	
	
	final static int responsecodeOk=200;
	/** The request. */
	@Autowired
	private HttpServletRequest request;
 
	/** The logger. */
	protected static Logger logger = Logger.getLogger(WebServicesRepository.class);
	
	/** The ctx. */
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	
	/** The indice supporto rete sociale service. */
	private IndiceSupportoReteSocialeService indiceSupportoReteSocialeService = (IndiceSupportoReteSocialeService) ctx.getBean("indiceSupportoReteSocialeService");
	
	/** The indice adl service. */
	private IndiceADLService indiceADLService = (IndiceADLService) ctx.getBean("indiceADLService");
	
	/** The indice barthel mobilita service. */
	private IndiceBarthelMobilitaService indiceBarthelMobilitaService = (IndiceBarthelMobilitaService) ctx.getBean("indiceBarthelMobilitaService");
	
	/** The indice exton smith service. */
	private IndiceExtonSmithService indiceExtonSmithService = (IndiceExtonSmithService) ctx.getBean("indiceExtonSmithService");
	
	/** The indice spmsq service. */
	private IndiceSPMSQService indiceSPMSQService = (IndiceSPMSQService) ctx.getBean("indiceSPMSQService");
	
	/** The indice npi service. */
	private IndiceNPIService indiceNPIService = (IndiceNPIService) ctx.getBean("indiceNPIService");
	
	/** The dati assistito service. */
	private DatiAssistitoService datiAssistitoService= (DatiAssistitoService) ctx.getBean("datiAssistitoService");
	
	/** The prestazioni service. */
	private PrestazioniService prestazioniService= (PrestazioniService) ctx.getBean("prestazioniService");
	
	/** The pai service. */
	private PaiService paiService= (PaiService) ctx.getBean("paiService");
	
	/** The pai_prestazioni service. */
	private Pai_PrestazioniService pai_prestazioniService = (Pai_PrestazioniService) ctx.getBean("pai_prestazioniService");
	
	/** The risultati_indici service. */
	private RisultatiIndiciService risultati_indiciService = (RisultatiIndiciService) ctx.getBean("risultati_indiciService");
	
	/** The intervento_service. */
	private InterventoService intervento_service = (InterventoService) ctx.getBean("interventoService");
	
	/** The componi fattura service. */
	private ComponiFatturaService componiFatturaService = (ComponiFatturaService) ctx.getBean("componiFatturaService");
	
	/**
	 * Gets the indice adljson schema.
	 *
	 * @return the indice adljson schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceADLJSONSchema", method = RequestMethod.GET, headers="Accept=application/json" )//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO getIndiceADLJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceADL.class));
		result.setMap(map);
		return result;
		
	}
	
	
	/**
	 * Gets the indice adl.
	 *
	 * @param indiceADL the indice adl
	 * @return the indice adl
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceADL", method = RequestMethod.POST, headers="Accept=application/json")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO getIndiceADL(@Valid @RequestBody IndiceADL indiceADL) throws Exception {
		
			ResultDTO result = new ResultDTO();
			Map<String, Object> map = Maps.newHashMap();
			
			
			Integer adl =indiceADLService.calcolaIndiceADL(indiceADL);
			String codificaAdl= indiceADLService.codificaADL(adl);
			map.put("OK","Calcolo Effettuato");
			map.put("indiceADL", adl + " - " +codificaAdl);
			result.setMap(map); 
			
			return result;
			
		
	}

	 
	
	/**
	 * Gets the indice adlxml.
	 *
	 * @param indiceADL the indice adl
	 * @return the indice adlxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceADLXML", method = RequestMethod.POST,headers="Accept=application/xml")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTOXML getIndiceADLXML(@Valid @RequestBody   IndiceADL indiceADL) throws Exception {
		
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object>map = Maps.newHashMap();
		
		Integer adl =indiceADLService.calcolaIndiceADL(indiceADL);
		String codificaAdl= indiceADLService.codificaADL(adl);
		map.put("OK","Calcolo Effettuato");
		map.put("indiceADL", adl + " - " +codificaAdl);
		result.setMap(map); 
		
		return result;
	}
	
	
	/**
	 * Gets the indice barthel mobilita json schema.
	 *
	 * @return the indice barthel mobilita json schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceBarthelMobilitaJSONSchema", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceBarthelMobilitaJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceBarthelMobilita.class));
		result.setMap(map);
		return result;
	}
	
	
	
	/**
	 * Gets the indice barthel mobilita.
	 *
	 * @param indiceBarthelMobilita the indice barthel mobilita
	 * @param errors the errors
	 * @return the indice barthel mobilita
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceBarthelMobilita", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceBarthelMobilita(@Valid @RequestBody IndiceBarthelMobilita indiceBarthelMobilita,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTO reserr = new ResultDTO();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		
		Integer vmob = indiceBarthelMobilitaService.calcolaMobilita(indiceBarthelMobilita);
		String codificaIndiceMobilita = indiceBarthelMobilitaService.codificaVMOB(vmob);
		map.put("OK", "Calcolo effettuato");
		map.put("indiceBarthelMobilita", vmob +" - "+codificaIndiceMobilita);
		result.setMap(map);
		
		
		return result;
	}
	
	/**
	 * Gets the indice barthel mobilita xml.
	 *
	 * @param indiceBarthelMobilita the indice barthel mobilita
	 * @param errors the errors
	 * @return the indice barthel mobilita xml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceBarthelMobilitaXML", method = RequestMethod.POST,headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML getIndiceBarthelMobilitaXML(@Valid @RequestBody IndiceBarthelMobilita indiceBarthelMobilita,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTOXML reserr = new ResultDTOXML();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
				
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object> map = Maps.newHashMap();
		Integer vmob = indiceBarthelMobilitaService.calcolaMobilita(indiceBarthelMobilita);
		String codificaIndiceMobilita = indiceBarthelMobilitaService.codificaVMOB(vmob);
		map.put("OK", "Calcolo effettuato");
		map.put("indiceBarthelMobilita", vmob +" - "+codificaIndiceMobilita);
		result.setMap(map);

		return result;
	}
	

	
	/**
	 * Gets the indice exton smith json schema.
	 *
	 * @return the indice exton smith json schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceExtonSmithJSONSchema", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceExtonSmithJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceExtonSmith.class));
		result.setMap(map);
		return result;
	}
	
    
	
	/**
	 * Gets the indice exton smith.
	 *
	 * @param indiceExtonSmith the indice exton smith
	 * @param errors the errors
	 * @return the indice exton smith
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceExtonSmith", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceExtonSmith(@Valid @RequestBody IndiceExtonSmith indiceExtonSmith,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTO reserr = new ResultDTO();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
				
		
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		Integer valoreIndiceExtonSmith = indiceExtonSmithService.calcolaPrevDecub(indiceExtonSmith);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica Exton Smith", valoreIndiceExtonSmith);
		result.setMap(map);
				
				
		return result;
	}
	
	/**
	 * Gets the indice exton smith xml.
	 *
	 * @param indiceExtonSmith the indice exton smith
	 * @param errors the errors
	 * @return the indice exton smith xml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceExtonSmithXML", method = RequestMethod.POST,headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML getIndiceExtonSmithXML(@Valid @RequestBody IndiceExtonSmith indiceExtonSmith,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTOXML reserr = new ResultDTOXML();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
						
				
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object> map = Maps.newHashMap();
		Integer valoreIndiceExtonSmith = indiceExtonSmithService.calcolaPrevDecub(indiceExtonSmith);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica Exton Smith", valoreIndiceExtonSmith);
		result.setMap(map);
		
		return result;
	}
	
	
	
	/**
	 * Gets the indice spmsqjson schema.
	 *
	 * @return the indice spmsqjson schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSPMSQJSONSchema", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceSPMSQJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceSPMSQ.class));
		result.setMap(map);
		return result;
	}
	
    
	
	/**
	 * Gets the indice spmsq.
	 *
	 * @param indiceSPMSQ the indice spmsq
	 * @param errors the errors
	 * @return the indice spmsq
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSPMSQ", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceSPMSQ(@Valid @RequestBody IndiceSPMSQ indiceSPMSQ,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTO reserr = new ResultDTO();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		
		
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		Integer valoreIndiceSPMSQ = indiceSPMSQService.calcolaSPMSQ(indiceSPMSQ);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica SPMSQ", valoreIndiceSPMSQ);
		
		
		result.setMap(map);
		return result;
	}
	
	
	/**
	 * Gets the indice spmsqxml.
	 *
	 * @param indiceSPMSQ the indice spmsq
	 * @param errors the errors
	 * @return the indice spmsqxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSPMSQXML", method = RequestMethod.POST,headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML getIndiceSPMSQXML(@Valid @RequestBody IndiceSPMSQ indiceSPMSQ,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTOXML reserr = new ResultDTOXML();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object> map = Maps.newHashMap();
		Integer valoreIndiceSPMSQ = indiceSPMSQService.calcolaSPMSQ(indiceSPMSQ);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica SPMSQ", valoreIndiceSPMSQ);
		result.setMap(map);
		
				
		return result;
	}
	
	
	/**
	 * Gets the indice npijson schema.
	 *
	 * @return the indice npijson schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceNPIJSONSchema", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceNPIJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceNPI.class));
		result.setMap(map);
		return result;
	}
	
    
	
	/**
	 * Gets the indice npi.
	 *
	 * @param indiceNPI the indice npi
	 * @param errors the errors
	 * @return the indice npi
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceNPI", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public ResultDTO getIndiceNPI(@Valid @RequestBody IndiceNPI indiceNPI,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTO reserr = new ResultDTO();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		
		
		Integer valoreIndiceNPI = indiceNPIService.calcolaIndiceNPI(indiceNPI);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica NPI", valoreIndiceNPI);
		result.setMap(map);
		
				
		return result;
	}
	
	
	/**
	 * Gets the indice npixml.
	 *
	 * @param indiceNPI the indice npi
	 * @param errors the errors
	 * @return the indice npixml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceNPIXML", method = RequestMethod.POST,headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML getIndiceNPIXML(@Valid @RequestBody IndiceNPI indiceNPI,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTOXML reserr = new ResultDTOXML();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object> map = Maps.newHashMap();
		Integer valoreIndiceNPI = indiceNPIService.calcolaIndiceNPI(indiceNPI);
		map.put("OK", "Calcolo effettuato");
		map.put("Codifica NPI", valoreIndiceNPI);
		result.setMap(map);
		
		
		return result;
	}
	
	
	/**
	 * Gets the indice supporto rete sociale json schema.
	 *
	 * @return the indice supporto rete sociale json schema
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSupportoReteSocialeJSONSchema", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO getIndiceSupportoReteSocialeJSONSchema() throws Exception {
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		map.put("JSONSchema", Utils.JSONSchemaMapper(IndiceSupportoReteSociale.class));
		result.setMap(map);
		return result;
	}
	
    
	
	/**
	 * Gets the indice supporto rete sociale.
	 *
	 * @param indiceSupportoReteSociale the indice supporto rete sociale
	 * @param errors the errors
	 * @return the indice supporto rete sociale
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSupportoReteSociale", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO getIndiceSupportoReteSociale(@Valid @RequestBody IndiceSupportoReteSociale indiceSupportoReteSociale,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTO reserr = new ResultDTO();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		ResultDTO result = new ResultDTO();
		Map<String, Object> map = Maps.newHashMap();
		
		
		Integer vsoc = indiceSupportoReteSocialeService.calcolaIndiceSupportoReteSociale(indiceSupportoReteSociale);
		String codificaVsoc = indiceSupportoReteSocialeService.codificaPsoc(vsoc);
		map.put("indiceSupportoReteSociale", vsoc+ " - "+codificaVsoc);
		map.put("OK", "Calcolo effettuato");
		
		result.setMap(map);
		
				
				
		return result;
	}
	
	
	/**
	 * Gets the indice supporto rete sociale xml.
	 *
	 * @param indiceSupportoReteSociale the indice supporto rete sociale
	 * @param errors the errors
	 * @return the indice supporto rete sociale xml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/indiceSupportoReteSocialeXML", method = RequestMethod.POST,headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML getIndiceSupportoReteSocialeXML(@Valid @RequestBody IndiceSupportoReteSociale indiceSupportoReteSociale,Errors errors) throws Exception {
		if (errors.hasErrors()) {
			ResultDTOXML reserr = new ResultDTOXML();
			Map<String, Object> maperr = Maps.newHashMap();
			maperr.put("OK", "Errore nella richiesta");
			reserr.setMap(maperr);
			return reserr;
		}
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object> map = Maps.newHashMap();
				
		Integer vsoc = indiceSupportoReteSocialeService.calcolaIndiceSupportoReteSociale(indiceSupportoReteSociale);
		String codificaVsoc = indiceSupportoReteSocialeService.codificaPsoc(vsoc);
		map.put("indiceSupportoReteSociale", vsoc+ " - "+codificaVsoc);
		map.put("OK", "Calcolo effettuato");
		
		result.setMap(map);
		
		return result;
	}
	
	/**
	 * Gets the monitoring.
	 *
	 * @return the monitoring
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoring", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoring() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			
		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
		return resultOK;
	}
	
	/**
	 * Gets the monitoring adl.
	 *
	 * @return the monitoring adl
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringADL", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoringADL() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			IndiceADL indiceADL = new IndiceADL();
			indiceADL.setfAlimentazione(2);
			indiceADL.setfBagnoDoccia(2);
			indiceADL.setfIgienePersonale(1);
			indiceADL.setfAbbigliamento(2);
			indiceADL.setfContinenzaIntestinale(5);
			indiceADL.setfContinenzaUrinaria(5);
			indiceADL.setfUsoGabinetto(10);
			
			
			int result = indiceADLService.calcolaIndiceADL(indiceADL);
			if(result== 27){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	
	/**
	 * Gets the monitoring barthel.
	 *
	 * @return the monitoring barthel
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringBarthel", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoringBarthel() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			IndiceBarthelMobilita indiceBarthelMobilita = new IndiceBarthelMobilita();
			indiceBarthelMobilita.setmTrasferimentoLS(7);
			indiceBarthelMobilita.setmDeambulazione(10);
			indiceBarthelMobilita.setmScale(10);
			
			

			
			
			int result = indiceBarthelMobilitaService.calcolaMobilita(indiceBarthelMobilita);
			if(result== 27){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	/**
	 * Gets the supporto rete sociale.
	 *
	 * @return the supporto rete sociale
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringSupportoReteSociale", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getSupportoReteSociale() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			IndiceSupportoReteSociale indiceSupportoReteSociale = new IndiceSupportoReteSociale();
			indiceSupportoReteSociale.setPreparazionePasti(5);
			indiceSupportoReteSociale.setPuliziaCasa(10);
			indiceSupportoReteSociale.setLavanderia(5);
			indiceSupportoReteSociale.setEffettuazioneAcquisti(0);
			indiceSupportoReteSociale.setAlimentazione(0);
			indiceSupportoReteSociale.setBagno(0);
			indiceSupportoReteSociale.setToelettaPersonale(5);
			indiceSupportoReteSociale.setAbbigliamento(0);
			indiceSupportoReteSociale.setUsoWC(5);
			indiceSupportoReteSociale.setAssunzioneMedicinali(0);
			indiceSupportoReteSociale.setTrasferimenti(15);
			indiceSupportoReteSociale.setDeambulazione(5);
			indiceSupportoReteSociale.setSostegnoPsicoAffettivo(0);
			indiceSupportoReteSociale.setGestioneDenaro(10);
			indiceSupportoReteSociale.setSupervisioneDiurna(5);
			indiceSupportoReteSociale.setSupervisioneNotturna(0);
			
			

			
			
			int result = indiceSupportoReteSocialeService.calcolaIndiceSupportoReteSociale(indiceSupportoReteSociale);
			if(result== 65){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	
	/**
	 * Gets the monitoring exton smith.
	 *
	 * @return the monitoring exton smith
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringExtonSmith", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoringExtonSmith() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			IndiceExtonSmith exthonSmith = new IndiceExtonSmith();
			exthonSmith.setpPresenzaPiaghe(15);
			exthonSmith.setpDecubitiCondGen(3);
			exthonSmith.setpDecubitiStatoMen(2);
			exthonSmith.setpDecubitiAttivita(3);
			exthonSmith.setpDecubitiMobilita(1);
			exthonSmith.setpDecubitiIncontinenza(1);
	
			
			int result = indiceExtonSmithService.calcolaPrevDecub(exthonSmith);
			if(result== 25){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	
	/**
	 * Gets the monitoring npi.
	 *
	 * @return the monitoring npi
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringNPI", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoringNPI() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
		IndiceNPI indiceNpi =new IndiceNPI();
			
		AgitazioneAggressivita agitazioneAggressivita= new AgitazioneAggressivita();
		agitazioneAggressivita.setNonApplicabile(false);
		agitazioneAggressivita.setMotivazione("");
		agitazioneAggressivita.setDomandaPreliminare(false);
		agitazioneAggressivita.setfDomandaIrritabile(1);
		agitazioneAggressivita.setgDomandaIrritabile(2);
		agitazioneAggressivita.setfDomandaOstinato(2);
		agitazioneAggressivita.setgDomandaOstinato(1);
		agitazioneAggressivita.setfDomandaNonCollabora(3);
		agitazioneAggressivita.setgDomandaNonCollabora(1);
		agitazioneAggressivita.setfDomandaAltriComportamenti(2);
		agitazioneAggressivita.setgDomandaAltriComportamenti(1);
		agitazioneAggressivita.setfDomandaBestemmie(2);
		agitazioneAggressivita.setgDomandaBestemmie(2);
		agitazioneAggressivita.setfDomandaLanciaOggetti(1);
		agitazioneAggressivita.setgDomandaLanciaOggetti(3);
		agitazioneAggressivita.setfDomandaColpireAltri(1);
		agitazioneAggressivita.setgDomandaColpireAltri(2);
		agitazioneAggressivita.setfDomandaAltriComportamentiAggressivi(1);
		agitazioneAggressivita.setgDomandaAltriComportamentiAggressivi(1);
		indiceNpi.setAgitazioneAggressivita(agitazioneAggressivita);
			
			
		Allucinazioni allucinazioni = new Allucinazioni();

		allucinazioni.setNonApplicabile(false);
		allucinazioni.setMotivazione("");
		allucinazioni.setDomandaPreliminare(true);
		allucinazioni.setfDomandaVoci(1);
		allucinazioni.setgDomandaVoci(2);
		allucinazioni.setfDomandaPersoneNonPresenti(2);
		allucinazioni.setgDomandaPersoneNonPresenti(1);
		allucinazioni.setfDomandaVisioneCose(3);
		allucinazioni.setgDomandaVisioneCose(1);
		allucinazioni.setfDomandaOdori(2);
		allucinazioni.setgDomandaOdori(1);
		allucinazioni.setfDomandaToccoPelle(2);
		allucinazioni.setgDomandaToccoPelle(2);
		allucinazioni.setfDomandaSapori(1);
		allucinazioni.setgDomandaSapori(3);
		allucinazioni.setfDomandaEsperienzeSensoriali(1);
		allucinazioni.setgDomandaEsperienzeSensoriali(2);
		indiceNpi.setAllucinazioni(allucinazioni);
		  
		Ansia ansia = new Ansia();

		ansia.setNonApplicabile(false);
		ansia.setMotivazione("");
		ansia.setDomandaPreliminare(true);
		ansia.setfDomandaFuturo(1);
		ansia.setgDomandaFuturo(2);
		ansia.setfDomandaEccessivamenteTeso(2);
		ansia.setgDomandaEccessivamenteTeso(1);
		ansia.setfDomandaRespiroCorto(3);
		ansia.setgDomandaRespiroCorto(1);
		ansia.setfDomandaRane(2);
		ansia.setgDomandaRane(1);
		ansia.setfDomandaEvitaLuoghi(2);
		ansia.setgDomandaEvitaLuoghi(2);
		ansia.setfDomandaDiventaNervoso(1);
		ansia.setgDomandaDiventaNervoso(3);
		ansia.setfDomandaManifestaAnsia(1);
		ansia.setgDomandaManifestaAnsia(2);
		indiceNpi.setAnsia(ansia);
		  
		ApatiaIndifferenza apatiaIndifferenza = new ApatiaIndifferenza();
		  
		apatiaIndifferenza.setNonApplicabile(false);
		apatiaIndifferenza.setMotivazione("");
		apatiaIndifferenza.setDomandaPreliminare(true);
		apatiaIndifferenza.setfDomandaMenoSpontaneo(1);
		apatiaIndifferenza.setgDomandaMenoSpontaneo(2);
		apatiaIndifferenza.setfDomandaMenoDisponibile(2);
		apatiaIndifferenza.setgDomandaMenoDisponibile(1);
		apatiaIndifferenza.setfDomandaMenoAffettuoso(3);
		apatiaIndifferenza.setgDomandaMenoAffettuoso(1);
		apatiaIndifferenza.setfDomandaContribuisceMeno(2);
		apatiaIndifferenza.setgDomandaContribuisceMeno(1);
		apatiaIndifferenza.setfDomandaMenoInteressato(2);
		apatiaIndifferenza.setgDomandaMenoInteressato(2);
		apatiaIndifferenza.setfDomandaMenoInteresseAmici(1);
		apatiaIndifferenza.setgDomandaMenoInteresseAmici(3);
		apatiaIndifferenza.setfDomandaMenoEntusiasta(1);
		apatiaIndifferenza.setgDomandaMenoEntusiasta(2);
		apatiaIndifferenza.setfDomandaAltriSegni(1);
		apatiaIndifferenza.setgDomandaAltriSegni(1);
		indiceNpi.setApatiaIndifferenza(apatiaIndifferenza);
		  
		AttivitaMotoriaAberrante attivitaAberrante = new AttivitaMotoriaAberrante();
		  
		attivitaAberrante.setNonApplicabile(false);
		attivitaAberrante.setMotivazione("");
		attivitaAberrante.setDomandaPreliminare(true);
		attivitaAberrante.setfDomandaCammina(1);
		attivitaAberrante.setgDomandaCammina(2);
		attivitaAberrante.setfDomandaRovistaCassetti(2);
		attivitaAberrante.setgDomandaRovistaCassetti(1);
		attivitaAberrante.setfDomandaVestiti(3);
		attivitaAberrante.setgDomandaVestiti(1);
		attivitaAberrante.setfDomandaAzioniRipetute(2);
		attivitaAberrante.setgDomandaAzioniRipetute(1);
		attivitaAberrante.setfDomandaAzioniRipetuteContinuamente(2);
		attivitaAberrante.setgDomandaAzioniRipetuteContinuamente(2);
		indiceNpi.setAttivitaMotoriaAberrante(attivitaAberrante);
		  
		  
		Deliri deliri = new Deliri();

		deliri.setNonApplicabile(false );
		deliri.setMotivazione("");
		deliri.setDomandaPreliminare(true);
		deliri.setfDomandaPericolo(1);
		deliri.setgDomandaPericolo(2);
		deliri.setfDomandaDerubato(2);
		deliri.setgDomandaDerubato(1);
		deliri.setfDomandaTradimento(3);
		deliri.setgDomandaTradimento(1);
		deliri.setfDomandaOspitiIndesiderati(2);
		deliri.setgDomandaOspitiIndesiderati(1);
		deliri.setfDomandaPersoneSconosciute(2);
		deliri.setgDomandaPersoneSconosciute(2);
		deliri.setfDomandaAbitazione(1);
		deliri.setgDomandaAbitazione(3);
		deliri.setfDomandaAbbandono(1);
		deliri.setgDomandaAbbandono(2);
		deliri.setfDomandaPresenzeInCasa(1);
		deliri.setgDomandaPresenzeInCasa(1);
		deliri.setfDomandaAltreCose(1);
		deliri.setgDomandaAltreCose(1);
		indiceNpi.setDeliri(deliri);
		    
	    DepressioneDisforia depressioneDisforia = new DepressioneDisforia();
		    
		depressioneDisforia.setNonApplicabile(false);
		depressioneDisforia.setMotivazione("");
		depressioneDisforia.setDomandaPreliminare(true);
		depressioneDisforia.setfDomandaMalinconia(1);
		depressioneDisforia.setgDomandaMalinconia(2);
	    depressioneDisforia.setfDomandaTristezza(2);
	    depressioneDisforia.setgDomandaTristezza(1);
	    depressioneDisforia.setfDomandaFallito(3);
	    depressioneDisforia.setgDomandaFallito(1);
	    depressioneDisforia.setfDomandaPersonaCattiva(2);
	    depressioneDisforia.setgDomandaPersonaCattiva(1);
	    depressioneDisforia.setfDomandaScoraggiato(2);
	    depressioneDisforia.setgDomandaScoraggiato(2);
	    depressioneDisforia.setfDomandaPeso(1);
	    depressioneDisforia.setgDomandaPeso(3);
	    depressioneDisforia.setfDomandaSuicidio(1);
	    depressioneDisforia.setgDomandaSuicidio(2);
	    depressioneDisforia.setfDomandaAltriSegni(1);
	    depressioneDisforia.setgDomandaAltriSegni(1);
	    indiceNpi.setDepressioneDisforia(depressioneDisforia);

		    
		Disinibizione disinibizione= new Disinibizione();   
		disinibizione.setNonApplicabile(false); 
		disinibizione.setMotivazione(""); 
		disinibizione.setDomandaPreliminare(true); 
		disinibizione.setfDomandaImpulsivo(1); 
	    disinibizione.setgDomandaImpulsivo(2); 
	    disinibizione.setfDomandaEstranei(2); 
	    disinibizione.setgDomandaEstranei(1); 
	    disinibizione.setfDomandaOffensive(3); 
	    disinibizione.setgDomandaOffensive(1); 
	    disinibizione.setfDomandaCoseVolgari(2); 
	    disinibizione.setgDomandaCoseVolgari(1); 
	    disinibizione.setfDomandaCosePrivate(2); 
	    disinibizione.setgDomandaCosePrivate(2); 
	    disinibizione.setfDomandaLiberta(1); 
	    disinibizione.setgDomandaLiberta(3); 
	    disinibizione.setfDomandaAltriSegni(1); 
	    disinibizione.setgDomandaAltriSegni(2); 
	    indiceNpi.setDisinibizione(disinibizione);
	     
	    DisturbiAlimentazione disturbiAlimentazione = new DisturbiAlimentazione();
	     
	    disturbiAlimentazione.setNonApplicabile(false);
	    disturbiAlimentazione.setMotivazione("");
	    disturbiAlimentazione.setDomandaPreliminare(true);
	    disturbiAlimentazione.setfDomandaPerditaAppetito(1);
	    disturbiAlimentazione.setgDomandaPerditaAppetito(2);
	    disturbiAlimentazione.setfDomandaAumentoAppetito(2);
	    disturbiAlimentazione.setgDomandaAumentoAppetito(1);
	    disturbiAlimentazione.setfDomandaPerditaPeso(3);
	    disturbiAlimentazione.setgDomandaPerditaPeso(1);
	    disturbiAlimentazione.setfDomandaAumentoPeso(2);
	    disturbiAlimentazione.setgDomandaAumentoPeso(1);
	    disturbiAlimentazione.setfDomandaCambioAbitudiniAlimentari(2);
	    disturbiAlimentazione.setgDomandaCambioAbitudiniAlimentari(2);
	    disturbiAlimentazione.setfDomandaCambioGustiAlimentari(1);
	    disturbiAlimentazione.setgDomandaCambioGustiAlimentari(3);
	    disturbiAlimentazione.setfDomandaSpecificheAbitudini(1);
	    disturbiAlimentazione.setgDomandaSpecificheAbitudini(2);
	    disturbiAlimentazione.setfDomandaAltriDisturbi(1);
	    disturbiAlimentazione.setgDomandaAltriDisturbi(1);
	    indiceNpi.setDisturbiAlimentazione(disturbiAlimentazione);
		    
		DisturbiSonno disturbiSonno= new DisturbiSonno();
		  
		disturbiSonno.setNonApplicabile(false);
	    disturbiSonno.setMotivazione("");
	    disturbiSonno.setDomandaPreliminare(true);
	    disturbiSonno.setfDomandaAddormentarsi(1);
	    disturbiSonno.setgDomandaAddormentarsi(2);
	    disturbiSonno.setfDomandaAlzaDuranteNotte(2);
	    disturbiSonno.setgDomandaAlzaDuranteNotte(1);
	    disturbiSonno.setfDomandaVaga(3);
	    disturbiSonno.setgDomandaVaga(1);
	    disturbiSonno.setfDomandaDisturba(2);
	    disturbiSonno.setgDomandaDisturba(1);
	    disturbiSonno.setfDomandaEsceDiCasa(2);
	    disturbiSonno.setgDomandaEsceDiCasa(2);
	    disturbiSonno.setfDomandaSiSvegliaPresto(1);
	    disturbiSonno.setgDomandaSiSvegliaPresto(3);
	    disturbiSonno.setfDomandaDormeEccessivamente(1);
	    disturbiSonno.setgDomandaDormeEccessivamente(2);
	    disturbiSonno.setfDomandaAltriDisturbi(1);
	    disturbiSonno.setgDomandaAltriDisturbi(1);
	    indiceNpi.setDisturbiSonno(disturbiSonno);
	    
	    EuforiaEsaltazione euforiaEsaltazione= new EuforiaEsaltazione();

	    euforiaEsaltazione.setNonApplicabile(false);
	    euforiaEsaltazione.setMotivazione("");
	    euforiaEsaltazione.setDomandaPreliminare(true);
	    euforiaEsaltazione.setfDomandaDifferenteDalSolito(1);
	    euforiaEsaltazione.setgDomandaDifferenteDalSolito(2);
	    euforiaEsaltazione.setfDomandaCoseRidicole(2);
	    euforiaEsaltazione.setgDomandaCoseRidicole(1);
	    euforiaEsaltazione.setfDomandaSensoUmorismo(3);
	    euforiaEsaltazione.setgDomandaSensoUmorismo(1);
	    euforiaEsaltazione.setfDomandaOsservazioni(2);
	    euforiaEsaltazione.setgDomandaOsservazioni(1);
	    euforiaEsaltazione.setfDomandaScherzi(2);
	    euforiaEsaltazione.setgDomandaScherzi(2);
	    euforiaEsaltazione.setfDomandaBravoRicco(1);
	    euforiaEsaltazione.setgDomandaBravoRicco(3);
	    euforiaEsaltazione.setfDomandaAltriSegni(1);
	    euforiaEsaltazione.setgDomandaAltriSegni(2);
	    indiceNpi.setEuforiaEsaltazione(euforiaEsaltazione);

		IrritabilitaLabilita  irritabilitaLabilita= new IrritabilitaLabilita();
		
	    irritabilitaLabilita.setNonApplicabile(false);
	    irritabilitaLabilita.setMotivazione("");
	    irritabilitaLabilita.setDomandaPreliminare(true);
	    irritabilitaLabilita.setfDomandaBruttoCarattere(1);
        irritabilitaLabilita.setgDomandaBruttoCarattere(2);
	    irritabilitaLabilita.setfDomandaCambiamentiUmore(2);
	    irritabilitaLabilita.setgDomandaCambiamentiUmore(1);
	    irritabilitaLabilita.setfDomandaRabbia(3);
	    irritabilitaLabilita.setgDomandaRabbia(1);
	    irritabilitaLabilita.setfDomandaInsofferente(2);
	    irritabilitaLabilita.setgDomandaInsofferente(1);
	    irritabilitaLabilita.setfDomandaNervoso(2);
	    irritabilitaLabilita.setgDomandaNervoso(2);
	    irritabilitaLabilita.setfDomandaBorbotta(1);
	    irritabilitaLabilita.setgDomandaBorbotta(3);
	    irritabilitaLabilita.setfDomandaAltriSegni(1);
	    irritabilitaLabilita.setgDomandaAltriSegni(3);
	    indiceNpi.setIrritabilitaLabilita(irritabilitaLabilita);

			
			int result = indiceNPIService.calcolaIndiceNPI(indiceNpi);
			if(result== 44){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	
	/**
	 * Gets the monitoring spmsq.
	 *
	 * @return the monitoring spmsq
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/monitoring/monitoringSPMSQ", method = RequestMethod.GET,headers="Accept=application/xml")
	@ResponseBody
	public String getMonitoringSPMSQ() throws Exception {
		
		String resultOK ="<html> "+
						"<head>"+
						"<style type=\"text/css\"></style>"+
						"</head>"+
						"<body>"+
						"OK"+
						"</body>"+
						"</html>";
		String resultError ="<html> "+
				"<head>"+
				"<style type=\"text/css\"></style>"+
				"</head>"+
				"<body>"+
				"ERROR"+
				"</body>"+
				"</html>";
		
		try {
			IndiceSPMSQ indiceSPMSQ = new IndiceSPMSQ();
			
			indiceSPMSQ.setDomandaGiornoOdierno(true);
			indiceSPMSQ.setDomandaGiornoSettimanale(true);
			indiceSPMSQ.setDomandaNomePosto(true);
			indiceSPMSQ.setDomandaIndirizzo(true);
			indiceSPMSQ.setDomandaAnni(true);
			indiceSPMSQ.setDomandaDataNascita(true);
			indiceSPMSQ.setDomandaPresRepPapa(true);
			indiceSPMSQ.setDomandaPrecedentePresRepPapa(true);
			indiceSPMSQ.setDomandaNomeMadre(true);
			indiceSPMSQ.setDomandaSottrazione(false);
			indiceSPMSQ.setNonSomministrabile(false);
			
			int result = indiceSPMSQService.calcolaSPMSQ(indiceSPMSQ);
			if(result== 9){
				return resultOK;
			}
			return resultError;

		} catch (Exception e) {
			logger.error("Eccezione : "+ e.getMessage());
			return resultError;
		}
	}
	
	/**
	 * Insert dati assistito.
	 *
	 * @param datiAssistito the dati assistito
	 * @return the result dto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaAssistito", method = RequestMethod.POST, headers="Accept=application/json")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO insertDatiAssistito(@RequestBody DatiAssistito datiAssistito) throws Exception {
		
		
		ResultDTO result = new ResultDTO();
		datiAssistitoService.insert(datiAssistito);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		map.put("idAssistito",  datiAssistito.getIdDatiAssistito());
		result.setMap(map);
		return result;
			
		
	}
	
	/**
	 * Insert dati assistito xml.
	 *
	 * @param datiAssistito the dati assistito
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaAssistitoXML", method = RequestMethod.POST, headers="Accept=application/xml")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTOXML insertDatiAssistitoXML(@RequestBody DatiAssistito datiAssistito) throws Exception {
		
		ResultDTOXML result = new ResultDTOXML();
		 datiAssistitoService.insert(datiAssistito);
		
		Map<String, Object> map = Maps.newHashMap();
		
		map.put("OK", "inserimento effettuato");
		map.put("idAssistito",  datiAssistito.getIdDatiAssistito());
	
		result.setMap(map);
		return result;
		
	}
	
	/**
	 * Select prestazioni consigliate.
	 *
	 * @param prestazioniRequestDTO the prestazioni request dto
	 * @return the list
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/selectPrestazioniConsigliate", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public List<Prestazioni> selectPrestazioniConsigliate(@RequestBody PrestazioniRequestDTO prestazioniRequestDTO) throws Exception {
		
		List<Prestazioni> elencoPrestazioni = null ;
		
		String adl_ = prestazioniRequestDTO.getRangeADL();
		String barthel_ = prestazioniRequestDTO.getRangeBarthel();
		String rete_ = prestazioniRequestDTO.getRangeRete();
		
		String resultAdl = adl_.replace(" ", "");
		String resultBarthel = barthel_.replace(" ", "");
		String resultRete = rete_.replace(" ", "");
		
		String rangeADLResult = prestazioniService.calcolaRangeADL(resultAdl);
		String rangeBarthelResult = prestazioniService.calcolaRangeBarthel(resultBarthel);
		String rangeReteResult = prestazioniService.calcolaRangeRete(resultRete);

		elencoPrestazioni = prestazioniService.select(rangeADLResult,rangeBarthelResult,rangeReteResult);
		
		return elencoPrestazioni;
		
	}
	
	/**
	 * Select prestazioni consigliate xml.
	 *
	 * @param prestazioniRequestDTO the prestazioni request dto
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/selectPrestazioniConsigliateXML", method = RequestMethod.POST, headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML selectPrestazioniConsigliateXML(@RequestBody PrestazioniRequestDTO prestazioniRequestDTO) throws Exception {
		
		List<Prestazioni> elencoPrestazioni = null ;
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object>map = Maps.newHashMap();
		
		String adl_ = prestazioniRequestDTO.getRangeADL();
		String barthel_ = prestazioniRequestDTO.getRangeBarthel();
		String rete_ = prestazioniRequestDTO.getRangeRete();
		
		String resultAdl = adl_.replace(" ", "");
		String resultBarthel = barthel_.replace(" ", "");
		String resultRete = rete_.replace(" ", "");
		
		String rangeADLResult = prestazioniService.calcolaRangeADL(resultAdl);
		String rangeBarthelResult = prestazioniService.calcolaRangeBarthel(resultBarthel);
		String rangeReteResult = prestazioniService.calcolaRangeRete(resultRete);

		elencoPrestazioni = prestazioniService.select(rangeADLResult,rangeBarthelResult,rangeReteResult);
		
		for (int i = 0; i < elencoPrestazioni.size(); i++) {
			String elPrest=elencoPrestazioni.get(i)+"";
			
			String elPrestazioni= elPrest.substring(elPrest.indexOf("=")+1,elPrest.indexOf("}"));

			map.put("descrizione"+i,elPrestazioni);
			
			 
		}
		result.setMap(map);
		
		return result;
		
		
		
	}
	
	
	
	/**
	 * Servizio per prendere tutte le prestazioni.
	 *
	 * @param prestazioniRequestDTO the prestazioni request dto
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/selectPrestazioniXML", method = RequestMethod.POST, headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML selectPrestazioniXML(@RequestBody PrestazioniRequestDTO prestazioniRequestDTO) throws Exception {
		
		List<Prestazioni> elencoPrestazioni = null ;
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object>map = Maps.newHashMap();

		elencoPrestazioni = prestazioniService.selectTutte();
		
		
		for (int i = 0; i < elencoPrestazioni.size(); i++) {
			String elPrest=elencoPrestazioni.get(i)+"";
			
			String elPrestazioni= elPrest.substring(elPrest.indexOf("=")+1,elPrest.indexOf("}"));
			
			map.put("descrizione"+i,elPrestazioni);
			
		}
		result.setMap(map);
		
		return result;
		
		
	}
	
	
	/**
	 * Insert risultati indici.
	 *
	 * @param risultatiIndici the risultati indici
	 * @return the result dto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaRisultatiIndici", method = RequestMethod.POST, headers="Accept=application/json")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO insertRisultatiIndici(@RequestBody RisultatiIndici risultatiIndici) throws Exception {
		
		
		ResultDTO result = new ResultDTO();
		risultati_indiciService.insert(risultatiIndici);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		result.setMap(map);
		return result;
		
	}
	
	/**
	 * Insert risultati indici xml.
	 *
	 * @param risultatiIndici the risultati indici
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaRisultatiIndiciXML", method = RequestMethod.POST, headers="Accept=application/xml")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTOXML insertRisultatiIndiciXML(@RequestBody RisultatiIndici risultatiIndici) throws Exception {
		
		ResultDTOXML result = new ResultDTOXML();
		risultati_indiciService.insert(risultatiIndici);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		result.setMap(map);
		return result;
		
	}
	
	
	/**
	 * Insert pai prestazioni.
	 *
	 * @param pai_Prestazioni the pai_ prestazioni
	 * @return the result dto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaPaiPrestazioni", method = RequestMethod.POST, headers="Accept=application/json")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO insertPaiPrestazioni(@RequestBody Pai_Prestazioni pai_Prestazioni) throws Exception {
		
		
		ResultDTO result = new ResultDTO();
		pai_prestazioniService.insert(pai_Prestazioni);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		result.setMap(map);
		return result;
		
	}
	
	/**
	 * Insert pai prestazioni xml.
	 *
	 * @param pai_Prestazioni the pai_ prestazioni
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaPaiPrestazioniXML", method = RequestMethod.POST, headers="Accept=application/xml")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTOXML insertPaiPrestazioniXML(@RequestBody Pai_Prestazioni pai_Prestazioni) throws Exception {
		ResultDTOXML result = new ResultDTOXML();
		pai_prestazioniService.insert(pai_Prestazioni);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		result.setMap(map);
		return result;
		
	}
	
	
	/**
	 * Insert pai.
	 *
	 * @param pai the pai
	 * @return the result dto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaPAI", method = RequestMethod.POST, headers="Accept=application/json")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTO insertPai(@RequestBody Pai pai) throws Exception {
		
		ResultDTO result = new ResultDTO();
		paiService.insert(pai);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		map.put("idPai",  pai.getIdPai());
		result.setMap(map);
		return result;
		
	}
	
	/**
	 * Insert paixml.
	 *
	 * @param pai the pai
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/salvaPAIXML", method = RequestMethod.POST, headers="Accept=application/xml")//aggiunto , headers="Accept=application/xml"
	@ResponseBody
	public ResultDTOXML insertPAIXML(@RequestBody Pai pai) throws Exception {
		
		ResultDTOXML result = new ResultDTOXML();
		paiService.insert(pai);
		Map<String, Object> map = Maps.newHashMap();
		map.put("OK", "inserimento effettuato");
		map.put("idPai",  pai.getIdPai());
		result.setMap(map);
		return result;
		
	}
	
	
	
	/**
	 * Select report prestazioni.
	 *
	 * @param reportPrestazioniRequestDTO the report prestazioni request dto
	 * @return the report prestazioni response dto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/selectReportPrestazioni", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public ReportPrestazioniResponseDTO selectReportPrestazioni(@RequestBody ReportPrestazioniRequestDTO reportPrestazioniRequestDTO) throws Exception {
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		ReportPrestazioniResponseDTO reportPrestazioni = new ReportPrestazioniResponseDTO() ;
		String comune = reportPrestazioniRequestDTO.getComune();
		Date data_da = format.parse(reportPrestazioniRequestDTO.getData_da());
		Date data_a = format.parse(reportPrestazioniRequestDTO.getData_a());
		
		reportPrestazioni=intervento_service.getReportPrestazioni(comune, data_da, data_a);
				
		return reportPrestazioni;
		
	}
	
	/**
	 * Select report prestazioni xml.
	 *
	 * @param reportPrestazioniRequestDTO the report prestazioni request dto
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/selectReportPrestazioniXML", method = RequestMethod.POST, headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML selectReportPrestazioniXML(@RequestBody ReportPrestazioniRequestDTO reportPrestazioniRequestDTO) throws Exception {
		
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object>map = Maps.newHashMap();
		
		ReportPrestazioniResponseDTO reportPrestazioni = new ReportPrestazioniResponseDTO() ;
		String comune = reportPrestazioniRequestDTO.getComune();
		String data_das = reportPrestazioniRequestDTO.getData_da();
		String data_as = reportPrestazioniRequestDTO.getData_a();
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data_da = format.parse(data_das);
		Date data_a = format.parse(data_as);
		try {
		reportPrestazioni=intervento_service.getReportPrestazioni(comune, data_da, data_a);
		
		map.put("esito", "OK");
		map.put("totale", reportPrestazioni.getTotale()+"");
		
		ArrayList<ReportPrestazioniDTO> elenco = (ArrayList<ReportPrestazioniDTO>) reportPrestazioni.getElencoPrestazioni();
		map.put("elementi", elenco.size());

		for(int i=0; i<elenco.size(); i++){
			ReportPrestazioniDTO dto = new ReportPrestazioniDTO();
			dto = elenco.get(i);
			map.put("riga"+i, dto.toStringXML());

			
		}
		} catch(Exception e){
			map.put("esito", e.getMessage());
			map.put("totale", "");
			map.put("riga0", "");
			map.put("elementi", "0");
			logger.error(it.cle.project.utils.Utils.getStackTrace(e));
		}
		
		result.setMap(map);
		return result;
		
	}
	
	

	/**
	 * Componi fattura xml.
	 *
	 * @param datiInputFatturaDTO the dati input fattura dto
	 * @return the result dtoxml
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/componiFatturaXML", method = RequestMethod.POST, headers="Accept=application/xml")
	@ResponseBody
	public ResultDTOXML componiFatturaXML(@RequestBody DatiInputFatturaDTO datiInputFatturaDTO) throws Exception {
		
		ResultDTOXML result = new ResultDTOXML();
		Map<String, Object>map = Maps.newHashMap();
		


		GeneraFatturaUtils gfUtils = new GeneraFatturaUtils();
		FatturaResponseDTO fatturaRespDTO = gfUtils.getFatturaXML(datiInputFatturaDTO, componiFatturaService);

		try {
			if(fatturaRespDTO != null) {
			String fileNameErrori= fatturaRespDTO.getFileNameErrori();
		   	 String fileNameFattura=fatturaRespDTO.getFileNameFattura();
		   	 String flussoFattura=fatturaRespDTO.getFlussoFattura();
		   	 String fileNameFatturaHtml=fatturaRespDTO.getFileNameFatturaHtml();
		   	 String flussoFatturaHtml=fatturaRespDTO.getFlussoFatturaHtml();
		        String statoFattura= fatturaRespDTO.getStato();
		        String messaggioErrore=fatturaRespDTO.getMessaggioErrore();
		        
		        logger.info("fileNameErrori "+fileNameErrori);
		        logger.info("fileNameFattura "+fileNameFattura);
		        logger.info("flussoFattura "+flussoFattura);
		        logger.info("fileNameFatturaHtml "+fileNameFatturaHtml);
		        logger.info("flussoFatturaHtml "+flussoFatturaHtml);
		        logger.info("statoFattura "+statoFattura);
		        logger.info("messaggioErrore "+messaggioErrore);		
		      
		        if(statoFattura.equals("OK")){
		        	map.put("esito", "OK");
		    		
		        	map.put("flussoFattura",fatturaRespDTO.getFlussoFattura());
		    		logger.info("Fattura Generata dal Servizio");
		    		logger.info(fatturaRespDTO.getFlussoFatturaHtml());
		    		logger.info(fatturaRespDTO.getFlussoFattura());
		        }
		        else{
		        	map.put("esito", fatturaRespDTO.getMessaggioErrore());
		        	map.put("fileNameErrori", fatturaRespDTO.getFileNameErrori());
		        }
		
			} else {
				throw new Exception("Si è verificato un errore nella chiamata al servizio di generazione della fattura.");
			}
		
		} catch(Exception e){
			map.put("esito", e.getMessage());
			map.put("totale", "");
			map.put("riga0", "");
			map.put("elementi", "0");
			logger.error(it.cle.project.utils.Utils.getStackTrace(e));
		}
		
		result.setMap(map);
		return result;
		
	}

	
	
	/**
	 * Genera fattura xml.
	 *
	 * @param datiInputFatturaDTO the dati input fattura dto
	 * @return the fattura xmldto
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/generaFatturaXML", method = RequestMethod.POST, headers="Accept=application/xml")
	@ResponseBody
	public FatturaXMLDTO generaFatturaXML(@RequestBody DatiInputFatturaDTO datiInputFatturaDTO) throws Exception {
		
		FatturaXMLDTO result = new FatturaXMLDTO();
		

		GeneraFatturaUtils gfUtils = new GeneraFatturaUtils();
		FatturaResponseDTO fatturaRespDTO = gfUtils.getFatturaXML(datiInputFatturaDTO, componiFatturaService);

		try {
			if(fatturaRespDTO != null) {
		        
				String statoFattura= fatturaRespDTO.getStato();
		        String messaggioErrore=fatturaRespDTO.getMessaggioErrore();
		        
		        logger.info("statoFattura "+statoFattura);
		        logger.info("messaggioErrore "+messaggioErrore);		
			      
		        result.setEsito(fatturaRespDTO.getStato());
		        if(statoFattura.equals("OK")){


					List<FatturaElettronicaDTO> listFattura = new ArrayList<>();
					listFattura.add(componiFatturaService.getFatturaElettronica());
		    		result.setFattura(listFattura);
		    		
		        }
		        else{
		        	result.setErrore(fatturaRespDTO.getMessaggioErrore());
		        	logger.error("Errore nella generazione della fattura: " );
		        	logger.error(fatturaRespDTO.getMessaggioErrore());		        }
		       
		
			} else {
				throw new Exception("Si è verificato un errore nella chiamata al servizio di generazione della fattura.");
			}
		
		} catch(Exception e){

			logger.error(it.cle.project.utils.Utils.getStackTrace(e));
		}
		
		return result;
		
	}	
	
	
	
	
	
	
//	@RequestMapping(value = "/selectReportPrestazioniXML", method = RequestMethod.POST, headers="Accept=application/xml")
//	@ResponseBody
//	public ResultDTOXML selectReportPrestazioniXML(@RequestBody ReportPrestazioniRequestDTO reportPrestazioniRequestDTO) throws Exception {
		@RequestMapping(value = "/rest/grass/status/{starTime}/{endTime}/{lon}/{lat}", method = RequestMethod.GET,headers="Accept=application/json")
		@ResponseBody
		public  String getConCordinate(@PathVariable("starTime") String starTime,@PathVariable("endTime") String endTime,@PathVariable("lon") String lon, @PathVariable("lat") String lat) {
			logger.info("Start getStatus getConCordinate starTime ="+starTime);
		
			String result = "";
			 
//			 JsonArray jsonArray = new JsonArray();
			 try {
	            
				 URL url = new URL("https://actinia.mundialis.de/sentinel2_query?start_time="+starTime+"&end_time="+endTime+"&lon="+lon+"&lat="+lat+"&cloud_covert=10");
//				 URL url = new URL("https://actinia.mundialis.de/sentinel2_query?start_time=2017-01-01T12%3A00%3A00&end_time=2017-01-01T12%3A40%3A00&lon=-40.5&lat=-53&cloud_covert=10");
	            System.out.println("URL CHIAMATA getConCordinate :" +url.toString());
	            
	            Base64 b = new Base64();
	            String encoding = b.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

	            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setDoOutput(true);
	            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
//	            connection.setRequestProperty("Access-Control-Allow-Origin", "*");
	            
	            
	            
	            int responseCode = connection.getResponseCode();
	            logger.info("response code getConCordinate  "+connection.getResponseCode());
	            if (responseCode == responsecodeOk ) {
	            
	            InputStream content = (InputStream)connection.getInputStream();

	            StringBuilder responseStrBuilder = new StringBuilder();
	            BufferedReader in   = 
	                new BufferedReader (new InputStreamReader (content));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	 responseStrBuilder.append(line);
	            }
	        
	            
	            result = responseStrBuilder.toString();
	           System.out.println("RESULT prima chiamata con cordinate e tempo "+result); 

	        
//	           Gson gson = new Gson();
//	           JsonParser jsonParser = new JsonParser();
//	            JsonArray jsonArray = (JsonArray) jsonParser.parse(result);
	           
	       
	           
	           
	        content.close();
				in.close();
			
			 } 
			 }
		        catch(Exception e) {
		            e.printStackTrace();
		        }
			 return result;
		}
		
		
		
		
			@RequestMapping(value = "/rest/grass/status/{ambiente}/{sceneId}/{south_lat}/{north_lat}/{east_lon}/{west_lon}/{south_lat_small}/{north_lat_small}/{east_lon_small}/{west_lon_small}", method = RequestMethod.GET,headers="Accept=application/json")
			@ResponseBody
			public  String getImage(@PathVariable("ambiente") String ambiente,@PathVariable("sceneId") String sceneId,
					@PathVariable("south_lat") String south_lat,@PathVariable("north_lat") String north_lat,
					@PathVariable("east_lon") String east_lon,@PathVariable("west_lon") String west_lon,
					@PathVariable("south_lat_small") String south_lat_small,
					@PathVariable("north_lat_small") String north_lat_small,
					@PathVariable("east_lon_small") String east_lon_small,@PathVariable("west_lon_small") String west_lon_small) {
				
				
				
				logger.info("Start getSaveAndCutImg   con ambiente="+ambiente+" sceneId="+sceneId+ " south_lat "+south_lat+" north_lat "+north_lat+ " east_lon "+east_lon+ " west_lon "+west_lon);

				String result_call="";
				String resource_id="";
				String resultSalvataggioImmagine="KO - Salvataggio non avvenuto";
				 try {
					 //passo sceneId

		           URL url = new URL ("https://actinia.mundialis.de/sentinel2_process/ndvi/"+sceneId);

		           logger.info("URL CHIAMATA getStatus con url  :" +url.toString());

		           Base64 b = new Base64();
		           String encoding = b.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

		           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		           connection.setRequestMethod("POST");
		           connection.setDoOutput(true);
		           connection.setRequestProperty  ("Authorization", "Basic " + encoding);
		           
		           int responseCode = connection.getResponseCode();
		          logger.info("response code "+connection.getResponseCode());
		          if (responseCode == responsecodeOk ) {
					
			
		           InputStream content = (InputStream)connection.getInputStream();
		          
		           
		           StringBuilder responseStrBuilder = new StringBuilder();
		           BufferedReader in   =
		               new BufferedReader (new InputStreamReader (content));
		           String line;
		           while ((line = in.readLine()) != null) {
		           	 responseStrBuilder.append(line);
		           }

		          JSONObject result = new JSONObject(responseStrBuilder.toString());
		         logger.info("Response : \n "+result);


		         resource_id=result.getJSONObject("urls").getString("status");
		       //ottengo lo stato
		        logger.info("Campo STATUS : \n"+resource_id);

		       content.close();
				in.close();



				//chiamo per ottenere l'immagine

				 resultSalvataggioImmagine = getUrlPngStatic(resource_id,ambiente);
				 	if(!resultSalvataggioImmagine.equals("")){

//					 south_lat="53.2560298512";
//					 north_lat="52.8703819712";
//					 east_lon="52.8703819712";
//					 west_lon="40.6113931789";
//					 south_lat_small="52.8703819712";
//					 north_lat_small="53.05";
//					 east_lon_small="40.4";
//					 west_lon_small="40.5";

				 	// taglio immagine e la risalvo aggiornata
				 		BufferedImage bimg = null;
						if(ambiente.equals("sviluppo")){
							bimg = ImageIO.read(new File(pathSviluppo));
							logger.info("path sviluppo "+pathSviluppo);
						}
						if(ambiente.equals("produzione")){
							bimg = ImageIO.read(new File(pathProduzione));
							logger.info("path Produzione "+pathProduzione);
						}

						Float west_lon_small_float= Float.parseFloat(west_lon_small);
						Float east_lon_small_float= Float.parseFloat(east_lon_small);
						Float west_lon_float= Float.parseFloat(west_lon);
						Float east_lon_float= Float.parseFloat(east_lon);
						Float north_lat_small_float= Float.parseFloat(north_lat_small);
						Float  south_lat_small_float= Float.parseFloat(south_lat_small);
						Float south_lat_float= Float.parseFloat(south_lat);
						Float north_lat_float= Float.parseFloat(north_lat);
						
						Float w_ratio_num = Math.abs(Math.abs(west_lon_small_float) - Math.abs(east_lon_small_float));
						Float w_ratio_den = Math.abs(Math.abs(west_lon_float) - Math.abs(east_lon_float));

						Float h_ratio_num = Math.abs(Math.abs(north_lat_small_float) - Math.abs(south_lat_small_float));
						Float h_ratio_den = Math.abs(Math.abs(north_lat_float) - Math.abs(south_lat_float));

						Float x_ratio_num = Math.abs(Math.abs(west_lon_small_float) - Math.abs(west_lon_float));
						Float x_ratio_den = Math.abs(Math.abs(east_lon_float) - Math.abs(west_lon_float));

						Float y_ratio_num = Math.abs(Math.abs(north_lat_small_float) - Math.abs(north_lat_float));
						Float y_ratio_den = Math.abs(Math.abs(north_lat_float) - Math.abs(south_lat_float));


						int width_img          = bimg.getWidth();
						int height_img         = bimg.getHeight();

						 int width = Math.round((w_ratio_num / w_ratio_den) * width_img);
						 int height = Math.round((h_ratio_num / h_ratio_den) * height_img);
				
						 int x =Math.round((x_ratio_num / x_ratio_den) * width_img);
						 int y = Math.round((y_ratio_num / y_ratio_den) * height_img);


						 		logger.info("width_img "+width_img);
						 		logger.info("height_img "+height_img);

								logger.info("My width "+width);
								logger.info("My height "+height);

								logger.info("X "+x);
								logger.info("Y "+y);



								if(x+width <=width_img && y+height<=height_img){

								BufferedImage dst = bimg.getSubimage(x, y, width, height);
								
										if(ambiente.equals("sviluppo")){
											ImageIO.write(dst, "png", new File(pathSviluppo));
										}
										if(ambiente.equals("produzione")){
											ImageIO.write(dst, "png", new File(pathProduzione));
										}
										
										result_call="L'immagine è stata tagliata e salvata correttamente";
								}
								
								else {
									result_call="Si è verificato un errore nel taglio dell'immagine in quanto i valori inseriti superano la grandezza dell'immagine sorgente!";
								
								logger.error("Si è verificato un errore nel taglio dell'immagine in quanto i valori inseriti superano la grandezza dell'immagine sorgente!");
								}
								 


						 }
				 	else{
				 		logger.error("Si è verificato un errore nella chiamara verso le API del serve esterno ");
			        	  result_call="KO-Si e verificato un errore nella chiamata verso le API del serve esterno";
				 	}
				 
			          }else {
			        	  logger.error("Si è verificato un errore nella chiamara verso le API del serve esterno ");
			        	  result_call="Si è verificato un errore nella chiamata verso le API del serve esterno";
			          }
				 }
			        catch(Exception e) {
			        	
			        	logger.error("Si è verificato un errore: "+e.getMessage());
			            e.printStackTrace();
			            result_call="Si  verificato un errore nella chiamata verso le API del serve esterno";
			        }
				 
				 logger.info(result_call);
				 return result_call;
			}
			
			
			public static String getUrlPngStatic(String resource_id, String ambiente) {
				
				logger.info("Start getUrlPngStatic con \n resource_id ="+resource_id +" ambiente "+ambiente);

			String statoSalvataggio ="";
			String urlPng="";
			String destinationFile ="";
			 try {
				 
		        	JSONArray resources = getUrlPngStatic(resource_id);
		        	
		        	
		        	
		        	while (resources.length() ==0) {
		        		 resources = getUrlPngStatic(resource_id);
		        		 if(resources.length()>0){
				        	   	urlPng=(String) resources.get(0);
				        
				        	   	logger.info("urlPng dentro: "+urlPng);
				           }
					}
		        	 if(resources.length()>0){
			        	   	urlPng=(String) resources.get(0);
			        
			        	   	logger.info("urlPng  fuori: "+urlPng);
			           }
		        	 
		        	//salvo immagino sul disco
	     		

		        	 if(ambiente.equals("sviluppo")){
		        		 destinationFile =pathSviluppo;
		        	 }
		        	 if(ambiente.equals("produzione")){
		        	 destinationFile=pathProduzione;
		        	 }
	     		
		        	 if(!urlPng.equals("")){
	     			
		        		 statoSalvataggio = saveImage(urlPng, destinationFile);
	     		}
			 } 
		        catch(Exception e) {
		            e.printStackTrace();
		            logger.error(e.getMessage());
		        }
			
			return statoSalvataggio;
		}
	
			 public static JSONArray getUrlPngStatic(String resource_id) {
					
					JSONArray resources = null;
					 try {
//				commentato xke nn fatto come servizio piu il nostro flusso  URL url2 = new URL ("https://actinia.mundialis.de/status/harvestplus/"+resource_id);
						 URL url2 = new URL (resource_id);
				            
						 logger.info("URL CHIAMATA in getUrlPngStatic :" +url2.toString());
				            
				            Base64 b2 = new Base64();
				            String encoding2= b2.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

				            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
				            connection2.setRequestMethod("GET");
				            connection2.setDoOutput(true);
				            connection2.setRequestProperty  ("Authorization", "Basic " + encoding2);
				            InputStream content2 = (InputStream)connection2.getInputStream();

				            StringBuilder responseStrBuilder2 = new StringBuilder();
				            BufferedReader in2   = 
				                new BufferedReader (new InputStreamReader (content2));
				            String line2;
				            while ((line2 = in2.readLine()) != null) {
				            	 responseStrBuilder2.append(line2);
				            }
				        
				            
				            
				           JSONObject result2 = new JSONObject(responseStrBuilder2.toString());
				           JSONObject urls=result2.getJSONObject("urls");
				           
				            resources= urls.getJSONArray("resources");
				            logger.info("resources "+resources);
				        content2.close();
			 			in2.close();
			 			
					 }
				        catch(Exception e) {
				        	logger.error(e.getMessage());
				            e.printStackTrace();
				            
				            JSONObject jInnerObject = new JSONObject();
				            jInnerObject.put("error", e.getMessage());
				            resources.put(jInnerObject);
				        }
					
					
					return resources;
					
				}
			 
			 public static String saveImage(String imageUrl, String destinationFile) throws IOException {
					String statoSalvataggio="KO -Immagine non salvata";
					 try {
					
					 URL url2 = new URL (imageUrl);
			            
			            logger.info("URL CHIAMATA  salva immagine con URL : \n " +url2.toString());
			            
			            Base64 b2 = new Base64();
			            String encoding2= b2.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

			            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
			            connection2.setRequestMethod("GET");
			            connection2.setDoOutput(true);
			            connection2.setRequestProperty  ("Authorization", "Basic " + encoding2);
			            InputStream content2 = (InputStream)connection2.getInputStream();
					
						OutputStream os = new FileOutputStream(destinationFile);

						byte[] b = new byte[2048];
						int length;

						while ((length = content2.read(b)) != -1) {
							os.write(b, 0, length);
						}

						content2.close();
						os.close();
						statoSalvataggio="OK- Savataggio avvenuto con successo";
						} catch (Exception e) {
							 e.printStackTrace();
						}
					 
					 logger.info("Stato Salvataggio immagine sul disco : " +statoSalvataggio);
					return statoSalvataggio;
					}
}


