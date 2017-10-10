package it.cle.webproject.dto;

import it.cle.project.dto.fattura.FatturaElettronicaDTO;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * The Class FatturaXMLDTO.
 *
 * @author leo.convertini
 */
@XmlRootElement(name="fattura_xml")
@XmlType(propOrder={"esito", "fattura", "errore"})
public class FatturaXMLDTO implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The esito. */
	private String esito;
	
	/** The errore. */
	private String errore;
	
	/** The fattura. */
	private List<FatturaElettronicaDTO> fattura ;
	
	
	
	/**
	 * Gets the esito.
	 *
	 * @return the esito
	 */
	public String getEsito() {
		return esito;
	}
	
	/**
	 * Sets the esito.
	 *
	 * @param esito the new esito
	 */
	@XmlElement
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	/**
	 * Gets the errore.
	 *
	 * @return the errore
	 */
	public String getErrore() {
		return errore;
	}
	
	/**
	 * Sets the errore.
	 *
	 * @param errore the new errore
	 */
	@XmlElement
	public void setErrore(String errore) {
		this.errore = errore;
	}
	
	/**
	 * Gets the fattura.
	 *
	 * @return the fattura
	 */
	public List<FatturaElettronicaDTO> getFattura() {
		return fattura;
	}
	
	/**
	 * Sets the fattura.
	 *
	 * @param fattura the new fattura
	 */
	@XmlElementWrapper(name="fattura")
	@XmlElement(name="FatturaElettronica")
	public void setFattura(List<FatturaElettronicaDTO> fattura) {
		this.fattura = fattura;
	}
	
	


	
	

}
