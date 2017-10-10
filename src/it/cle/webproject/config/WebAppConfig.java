package it.cle.webproject.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

// TODO: Auto-generated Javadoc
/**
 * The Class WebAppConfig.
 *
 * @author leo.convertini
 */

/**
 * The Class AppConfig.
 * Definisce i package su cui viene effettuato lo scanning per la lettura delle 
 * annotation 
 */
@Configuration
@ComponentScan(basePackages = {"it.cle.webproject.ws", "it.cle.webproject.controller", "it.cle.webproject.validation","it.cle.webproject.config"})
@Import( {WebMvcConfig.class } )
public class WebAppConfig {

	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

}