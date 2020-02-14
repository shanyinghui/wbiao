package com.wbiao.oauth.service;

import com.wbiao.util.AuthToken;

public interface UserLoginService {
    AuthToken login(String username, String password, String clientId, String clientSecret, String grant_type) throws Exception;
}