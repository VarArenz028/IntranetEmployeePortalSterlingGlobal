package org.sterling.intranet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.sterling")
public class WebConfiguration extends WebMvcConfigurerAdapter
{
    
    @Bean(name="multipartResolver")
    public StandardServletMultipartResolver resolver()
    {
        return new StandardServletMultipartResolver();
    }
    @Bean
    public DispatcherServletBeanPostProcessor dispatcherServletBeanPostProcessor() 
    {
        return new DispatcherServletBeanPostProcessor();
    }
    @Bean
    public VelocityConfigurer velocityConfig() 
    {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath("/WEB-INF/views/");
        return velocityConfigurer;
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) 
    {
        VelocityViewResolver viewResolver = new VelocityViewResolver();

        viewResolver.setViewClass(VelocityView.class);
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".html");
        viewResolver.setExposeSpringMacroHelpers(true);
        registry.viewResolver(viewResolver);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        registry.addResourceHandler("/app-resources/**").addResourceLocations("/app-resources/");
        registry.addResourceHandler("/app-resources/css/**").addResourceLocations("/app-resources/css/");
        registry.addResourceHandler("/views/").addResourceLocations("/views/");
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }
}
