package org.sterling.intranet.config;

import org.sterling.intranet.security.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.sterling.intranet.security.RestUnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    
    @Autowired
    @Qualifier(value = "customUserDetailsService")
    UserDetailsService customUserDetailsService;
    
    @Autowired
    private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
    
    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;
    
    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception 
    {
        auth.userDetailsService(customUserDetailsService);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
       // authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/system-admin/**").access("hasRole('System Admin')")
            .antMatchers("/campaign-admin/**").access("hasRole('Campaign Admin')")
            .antMatchers("/agent/**").access("hasRole('Agent')")
            .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
            .accessDeniedHandler(restAccessDeniedHandler)
            .and().formLogin().loginProcessingUrl("/authenticate").usernameParameter("username").passwordParameter("password")
            .successHandler(customSuccessHandler).and().logout().logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).deleteCookies("JSESSIONID")
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

            http.cors().and().authorizeRequests().antMatchers("/app-resources/**", "/WEB-INF/views/loginPageTemplates/**").permitAll().anyRequest().permitAll();
        
    }
//    @Configuration
//    @Order(1)
//    public static class SystemAdminSecurityConfig extends WebSecurityConfigurerAdapter
//    {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception 
//        {
//
//            http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/system-admin/**").hasAuthority("ROLE_System Admin").anyRequest().authenticated()
//                .and().formLogin().loginProcessingUrl("/authenticate").usernameParameter("username").passwordParameter("password")
//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//
//                http.cors().and().authorizeRequests().antMatchers("/app-resources/**", "/WEB-INF/views/loginPageTemplates/**").permitAll().anyRequest().permitAll();
//
//        }
//    }
//    @Configuration
//    @Order(2)
//    public static class CampaignAdminSecurityConfig extends WebSecurityConfigurerAdapter
//    {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception 
//        {
//
//            http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/campaign-admin/**").hasAuthority("ROLE_Campaign Admin").anyRequest().authenticated()
//                .and().formLogin().loginProcessingUrl("/authenticate").usernameParameter("username").passwordParameter("password")
//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//
//                http.cors().and().authorizeRequests().antMatchers("/app-resources/**", "/WEB-INF/views/loginPageTemplates/**").permitAll().anyRequest().permitAll();
//
//        }
//    }
    /* To allow Pre-flight [OPTIONS] request from browser */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//      web.ignoring().antMatchers("/the_js_path/**");
//    }
}
