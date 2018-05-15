package org.sterling.intranet.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Var Arenz Villarino
 */
public class DispatcherServletBeanPostProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        if (bean instanceof DispatcherServlet)
        {
            ((DispatcherServlet) bean).setDispatchOptionsRequest(true);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException 
    {
        return bean;
    }
}
