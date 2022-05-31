package innowise.zuevsky.helpdesk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html", "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"
            ,"/helpdesk-service/**", "/actuator/**"
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/helpdesk-service/feedbacks/**").hasAnyRole("EMPLOYEE","MANAGER")
                .antMatchers( "/helpdesk-service/tickets/all").hasAnyRole("EMPLOYEE","MANAGER","ENGINEER")
                .antMatchers( "/helpdesk-service/tickets/my").hasAnyRole("EMPLOYEE","MANAGER")
                .antMatchers( "/helpdesk-service/tickets").hasAnyRole("EMPLOYEE","MANAGER")
                .antMatchers( "/helpdesk-service/tickets/update/**").hasAnyRole("EMPLOYEE","MANAGER")
                .antMatchers( "/helpdesk-service/tickets/get/**").hasAnyRole("EMPLOYEE","MANAGER","ENGINEER")
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
        return jwtConverter;
    }

}