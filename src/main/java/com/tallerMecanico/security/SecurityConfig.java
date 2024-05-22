package com.tallerMecanico.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicación
@EnableWebSecurity //Indicamos que se activa la seguridad web en nuestra aplicación y además esta será una clase la cuál contendrá toda la configuración referente a la seguridad
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}
	
	//Este bean va a encargarse de verificar la información de los usuarios que se loguearán en nuestra api
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// Con este bean nos encargaremos de encriptar todas nuestras contraseñas
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Este beab incorporará el filtro de seguridad de json web token que creamos en nuestra clase anterior
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	// Vamos a crear un bean el cual va a establecer una cadena de filtros de seguridad en nuestra aplicación. Y es aquí donde determinaremos los permisos según los roles de los usuarios para acceder a nuestra aplicación.
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
			.csrf(csrf -> csrf.disable())
			.exceptionHandling(handling -> handling //Permitimos el manejo de excepciones
					.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.sessionManagement(management -> management //Permite la gestión de sessiones
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(requests -> requests //Toda petición http debe ser autorizada
					.requestMatchers("/api/auth/**").permitAll()
					//.requestMatchers("/api/**").permitAll()
					//CLIENTES
					.requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyAuthority("ADMIN", "CLIENTE")
					.requestMatchers(HttpMethod.POST,"/api/clientes/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/clientes/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/clientes/**").hasAuthority("ADMIN")
					
					//DEPARTAMENTOS
					.requestMatchers(HttpMethod.GET, "/api/departamentos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/departamentos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/departamentos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/departamentos/**").hasAuthority("ADMIN")
					
					//EMPLEADOS
					.requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/empleados/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/empleados/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/empleados/**").hasAuthority("ADMIN")
					
					//ESTATUS SERVICIO
					.requestMatchers(HttpMethod.GET, "/api/estatusServicios/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/estatusServicios/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/estatusServicios/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/estatusServicios/**").hasAuthority("ADMIN")
					
					//MARCAS
					.requestMatchers(HttpMethod.GET, "/api/marcas/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/marcas/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/marcas/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/marcas/**").hasAuthority("ADMIN")
					
					//MODELOS
					.requestMatchers(HttpMethod.GET, "/api/modelos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/modelos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/modelos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/modelos/**").hasAuthority("ADMIN")
					
					//ORDEN SERVICIO
					.requestMatchers(HttpMethod.GET, "/api/ordenesServicio/**").hasAnyAuthority("ADMIN", "CLIENTE")
					.requestMatchers(HttpMethod.POST,"/api/ordenesServicio/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/ordenesServicio/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/ordenesServicio/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					
					//TIPO MOTOR
					.requestMatchers(HttpMethod.GET, "/api/tiposMotor/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.POST,"/api/tiposMotor/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/tiposMotor/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/tiposMotor/**").hasAuthority("ADMIN")
					
					//VEHICULO
					.requestMatchers(HttpMethod.GET, "/api/vehiculos/**").hasAnyAuthority("ADMIN", "CLIENTE")
					.requestMatchers(HttpMethod.POST,"/api/vehiculos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/api/vehiculos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/api/vehiculos/**").hasAuthority("ADMIN")
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					

					.anyRequest().authenticated())
			.httpBasic(withDefaults());
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
    
	/*
	 * 
	 * 
    
    .requestMatchers(HttpMethod.OPTIONS).permitAll()
    */

}


