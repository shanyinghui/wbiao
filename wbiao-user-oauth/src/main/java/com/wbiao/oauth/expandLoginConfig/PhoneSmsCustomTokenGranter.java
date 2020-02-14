package com.wbiao.oauth.expandLoginConfig;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class PhoneSmsCustomTokenGranter extends AbstractCustomTokenGranter {

    private static final String GRANT_TYPE = "sms_code";

    private CustomUserDetailsService customUserDetailsService;

    public PhoneSmsCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, CustomUserDetailsService userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory,GRANT_TYPE);
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String phone = parameters.get("phone");
        String smsCode = parameters.get("code");
        UserDetails userDetails = customUserDetailsService.loadUserByPhoneAndSmsCode(phone, smsCode);

        return userDetails;
    }
}
