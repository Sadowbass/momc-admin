package com.momc.admin.infra.config.security;

import com.momc.admin.infra.config.PasswordEncoderHolder;
import com.momc.admin.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordEncoderHolder.setDefaultPasswordEncoder(passwordEncoder);

        return passwordEncoder;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistryImpl sessionRegistry = new SessionRegistryImpl();
        SecurityUtils.setSessionRegistry(sessionRegistry);
        return sessionRegistry;
    }

    @Bean
    @Profile("!dev")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/login", "/admins/register").permitAll()
                .antMatchers(HttpMethod.POST, "/admins").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/login").usernameParameter("loginId")
                .defaultSuccessUrl("/admins")
                .failureHandler((request, response, exception) -> {
                    if (exception instanceof DisabledException) {
                        request.setAttribute("error", "아직 승인되지 않았습니다. 관리자, 개발자에게 문의하세요");
                    } else if (exception instanceof BadCredentialsException) {
                        request.setAttribute("error", "ID 혹은 PW가 틀립니다");
                    }
                    request.getRequestDispatcher("/login-failed").forward(request, response);
                });

        httpSecurity.headers().frameOptions().disable();
        httpSecurity.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/expire");

        return httpSecurity.build();
    }

    @Bean
    @Profile("dev")
    public SecurityFilterChain securityFilterChainDev(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/login").usernameParameter("loginId")
                .defaultSuccessUrl("/admins")
                .failureHandler((request, response, exception) -> {
                    if (exception instanceof DisabledException) {
                        request.setAttribute("error", "아직 승인되지 않았습니다. 관리자, 개발자에게 문의하세요");
                    } else if (exception instanceof BadCredentialsException) {
                        request.setAttribute("error", "ID 혹은 PW가 틀립니다");
                    }
                    request.getRequestDispatcher("/login-failed").forward(request, response);
                });

        httpSecurity.headers().frameOptions().disable();
        httpSecurity.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/expire");

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
