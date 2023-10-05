package fr.hb.asl.calendrier_gif.configuration;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import fr.hb.asl.calendrier_gif.handler.CustomFailHandler;
import fr.hb.asl.calendrier_gif.handler.CustomSuccessHandler;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor

public class SecurityConfiguration {
	private PasswordEncoder passwordEncoder;
	private UtilisateurDao utilisateurDao;
	private final HttpSession httpSession;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).formLogin()
				.successHandler(new CustomSuccessHandler(utilisateurDao, httpSession))

				.failureHandler(new CustomFailHandler(utilisateurDao))

				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/index").and()

				.headers().frameOptions().disable().and()

				.authorizeHttpRequests(requests -> requests.antMatchers("/swagger-ui/index.html").permitAll()
						.antMatchers("/h2-console/**").permitAll()
						.antMatchers("/calendrier", "/calendrier/placerGifDistant").authenticated().anyRequest()
						.permitAll());
		return http.build();
	}
  
}