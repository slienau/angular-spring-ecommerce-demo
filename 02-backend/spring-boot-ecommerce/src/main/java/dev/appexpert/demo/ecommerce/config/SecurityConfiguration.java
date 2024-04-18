package dev.appexpert.demo.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        //protect endpoint /api/orders
        httpSecurity.authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/orders/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

        // + CORS filters
        httpSecurity.cors(Customizer.withDefaults());

        // + content negotiation strategy
        httpSecurity.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // + non-empty response body for 401 (more friendly)
        Okta.configureResourceServer401ResponseBody(httpSecurity);

        // we are not using Cookies for session tracking >> disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}
