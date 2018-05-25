package com.test.oauth2Server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
    private final CustomTokenEnhancer tokenEnhancer;
    private final JwtTokenStore jwtTokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthConfig(AuthenticationManager authenticationManager, JwtTokenStore jwtTokenStore,
                      JwtAccessTokenConverter jwtAccessTokenConverter,CustomTokenEnhancer tokenEnhancer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenStore = jwtTokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.tokenEnhancer = tokenEnhancer;
    }

//    @Bean
//    public ClientDetailsService clientDetailsService() {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
//        userDetailsManager.setDataSource(dataSource);
//        return userDetailsManager;
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancer)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService());
//    }

}
