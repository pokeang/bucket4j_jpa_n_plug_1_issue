package com.simple.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple.assignment.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("configure global");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		logger.info("passwordEncoder");
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public AuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder());
//		provider.setUserDetailsService(userDetailsService());
//		return provider;
//	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().loginPage("/login").failureForwardUrl("/login").and().logout().logoutSuccessUrl("/login").and()
				.authorizeRequests().antMatchers("/**", "/login", "/api/**").permitAll().anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedPage("/403").and().sessionManagement().maximumSessions(1)
				.expiredUrl("/login?expired-session=true").and().invalidSessionUrl("/login?invalid-session=true");
	}

}
