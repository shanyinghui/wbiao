package com.wbiao.oauth.expandLoginConfig;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class WbLoginCustomTokenGranter extends AbstractCustomTokenGranter {

    private static final String GRANT_TYPE = "wbLogin";

    private CustomUserDetailsService customUserDetailsService;

    public WbLoginCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, CustomUserDetailsService userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory,GRANT_TYPE);
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String access_token = parameters.get("access_token");
        String uid = parameters.get("uid");
        UserDetails userDetails = customUserDetailsService.loadUserByAccess_token(access_token, uid);

        return userDetails;
    }
}
