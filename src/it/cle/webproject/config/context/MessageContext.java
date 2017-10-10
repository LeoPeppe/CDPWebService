package it.cle.webproject.config.context;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageContext.
 *
 * @author Gianni Germano
 */
@Configuration
@Profile("production")
public class MessageContext {

    /** The Constant MESSAGE_SOURCE_BASE_NAME. */
    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
}
