package it.cle.webproject.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class IndiceADLXMLDTO.
 *
 * @author leo.convertini
 */
@XmlRootElement(name="indice_adl_result")
public class IndiceADLXMLDTO implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The indice adl value. */
	private Integer indiceADLValue;

	/**
	 * Gets the indice adl value.
	 *
	 * @return the indice adl value
	 */
	public Integer getIndiceADLValue() {
		return indiceADLValue;
	}
	
	/**
	 * Sets the indice adl value.
	 *
	 * @param indiceADLValue the new indice adl value
	 */
	@XmlElement(name="value")
	public void setIndiceADLValue(Integer indiceADLValue) {
		this.indiceADLValue = indiceADLValue;
	}
	
	
	

}
