package org.sterling.intranet.config;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer implements WebApplicationInitializer
{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException 
    {
        File file = null;
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfiguration.class);
        context.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(context));
        System.gc();
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setInitParameter("dispatchOptionsRequest", "true");
        servlet.setMultipartConfig(new MultipartConfigElement("C:\\Users\\KAMBO OTIS\\Documents\\uploadedFiles", 25 * 1024 * 1024, 125 * 1024 * 1024, 1 * 1024 * 1024));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }

//    @Override
//    protected Class<?>[] getRootConfigClasses() 
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses()
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected String[] getServletMappings() 
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) 
//    {
//      registration.setMultipartConfig(getMultipartConfigElement());
//    }
//
//    private MultipartConfigElement getMultipartConfigElement()
//    {
//        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
//        return multipartConfigElement;
//    }
//    
//    /*Set these variables for your project needs*/ 
//    
//	private static final String LOCATION = "C:\\Users\\KAMBO OTIS\\Documents\\Capstone";
//
//	private static final long MAX_FILE_SIZE = 1024 * 1024 * 25;//25MB
//	
//	private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 30;//30MB
//
//	private static final int FILE_SIZE_THRESHOLD = 0;
//    @Override
//    protected Filter[] getServletFilters() 
//    {
//        Filter [] singleton = { new CORSFilter() };
//        return singleton;
//    }
    
}
