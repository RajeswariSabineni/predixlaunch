package com.ge.predixlaunch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//temperorily commented by rajeswari
		/*http.authorizeRequests().antMatchers("/static/**").hasAnyRole("ADMIN","USER").
		and().formLogin();*/
		http.authorizeRequests().antMatchers("/static/**").hasAnyRole("ADMIN","USER").
		and().csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication().withUser("ram").password("ram123").roles("ADMIN");
		  auth.inMemoryAuthentication().withUser("ravan").password("ravan123").roles("USER");
		  auth.inMemoryAuthentication().withUser("kans").password("kans123").roles("USER");
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
	    webSecurity.ignoring().antMatchers("/static/**");
	}
	
	/*protected void configure(final HttpSecurity http) throws Exception {
	    http
	    .csrf()
	    .disable()
	    .sessionManagement()
	    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
	    .enableSessionUrlRewriting(false)
	    .and()
	    .authorizeRequests()
	    .antMatchers("/static/**").permitAll()
	    .anyRequest().authenticated()
	    .and()
	    .addFilterBefore(this.rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class)
	    .formLogin()
	    .loginPage("/login")
	    .permitAll()
	    .and()
	    .logout()
	   .deleteCookies(cookieName, "JSESSIONID")
	    .permitAll()
	    .and()
	    .rememberMe().rememberMeServices(this.tokenBasedRememberMeService())
	    ;
	}*/
	
	/*@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
	    auth.
	    .userDetailsService(this.userDetailsServiceImpl)
	    .passwordEncoder(this.bCryptPasswordEncoder());
	    auth.authenticationProvider(this.rememberMeAuthenticationProvider());
	}*/

}
