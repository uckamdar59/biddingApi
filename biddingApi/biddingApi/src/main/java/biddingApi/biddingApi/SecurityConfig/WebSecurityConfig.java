package biddingApi.biddingApi.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		// dont authenticate this particular request
		.authorizeRequests().antMatchers("/**").permitAll()
		.and()
		// make sure we use stateless session; session won't be used to store user's state.
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiNjk1OTA0OTIzMSIsInN1YiI6IjY5NTkwNDkyMzEiLCJqdGkiOiJ0cmFuc3BvcnRlcjo5M2NiMzQ1ZS0wNWZlLTQ5YjUtYTdjYS0xMDc5ZWU3NTUzMzEiLCJpYXQiOjE2MjcwMzc1MDEsImV4cCI6MTYyNzA0MzUwMX0.Ld02IyWMYT4ye3JH6DnDTgANPjkU_PuSU0jMu8d15y4