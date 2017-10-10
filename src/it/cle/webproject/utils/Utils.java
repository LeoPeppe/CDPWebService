package it.cle.webproject.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * JSON schema mapper.
	 *
	 * @param <T> the generic type
	 * @param o the o
	 * @return the json schema
	 * @throws JsonProcessingException the json processing exception
	 */
	public static <T> JsonSchema JSONSchemaMapper(Class<T> o) throws JsonProcessingException{
	    
	    ObjectMapper mapper = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(o, visitor);
        JsonSchema schema = visitor.finalSchema();
		return schema;
	}
	
}
