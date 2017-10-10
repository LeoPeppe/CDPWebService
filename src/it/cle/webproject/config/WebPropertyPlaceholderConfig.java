package it.cle.webproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

// TODO: Auto-generated Javadoc
/**
 * The Class WebPropertyPlaceholderConfig.
 *
 * @author leo.convertini
 */
@Configuration
@PropertySource({"classpath:hibernate.properties"})
public class WebPropertyPlaceholderConfig {

   
    
	/**
	 * Property sources placeholder configurer.
	 *
	 * @return the property sources placeholder configurer
	 */
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}
