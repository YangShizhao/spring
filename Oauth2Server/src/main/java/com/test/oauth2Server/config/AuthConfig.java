package com.test.oauth2Server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
    private final JwtTokenStore jwtTokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final UserDetailsService userDetailsService;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthConfig(AuthenticationManager authenticationManager, JwtTokenStore jwtTokenStore,
                      JwtAccessTokenConverter jwtAccessTokenConverter, UserDetailsService userDetailsService,
                      ClientDetailsService clientDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenStore = jwtTokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(jwtTokenStore)
                .tokenEnhancer(jwtAccessTokenConverter)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

}
