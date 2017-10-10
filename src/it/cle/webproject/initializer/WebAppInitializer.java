package it.cle.webproject.initializer;

import it.cle.webproject.config.SimpleCORSFilter;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.DispatcherServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class WebAppInitializer.
 *
 * @author ggermano Inizializza l' applicazione prendendo il contesto tramite il
 *         metodo getContext()
 */
public class WebAppInitializer implements WebApplicationInitializer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    /* (non-Javadoc)
     * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
     */
    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        // Register application config
        rootContext.setConfigLocation("it.cle.webproject.config");
       //System.out.println("prima di add listener");
        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));
        //System.out.println("dopo di addlistener");
        // Register Encoding Filter
        addEncodingFilter(container);

        // Register Logging Filter
        addLoggingFilter(container);
        addCorsFilter(container);
        
        
        rootContext.getEnvironment().setActiveProfiles("dev"); //era commentato con activeProfile come argomnto
        //System.out.println("legge profilo");
        
        // Register and map the dispatcher servlet
        addServiceDispatcherServlet(container, rootContext);
    }

   

	/**
	 * Adds the cors filter.
	 *
	 * @param container the container
	 */
	private void addCorsFilter(ServletContext container) {
		FilterRegistration.Dynamic corsFilter = container.addFilter("corsFilter", SimpleCORSFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
		
	}



	/**
	 * Adds the service dispatcher servlet.
	 *
	 * @param container the container
	 * @param rootContext the root context
	 */
	private void addServiceDispatcherServlet(ServletContext container, AnnotationConfigWebApplicationContext rootContext) {
        final String SERVICES_MAPPING = "/";

        ServletRegistration.Dynamic dispatcher = container.addServlet("DispatcherServlet", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        Set<String> mappingConflicts = dispatcher.addMapping(SERVICES_MAPPING);

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException("'ServicesDispatcher' could not be mapped to '" + SERVICES_MAPPING + "'");
        }
    }
    
    /**
     * Adds the encoding filter.
     *
     * @param container the container
     */
    private void addEncodingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("encodingFilter", new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
        //System.out.println("fine addencoding");
    }
    
    /**
     * Adds the logging filter.
     *
     * @param container the container
     */
    private void addLoggingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("loggingFilter", new CommonsRequestLoggingFilter());
        fr.addMappingForUrlPatterns(null, true, "/*");
        //System.out.println("fine addlogging");
    }

}