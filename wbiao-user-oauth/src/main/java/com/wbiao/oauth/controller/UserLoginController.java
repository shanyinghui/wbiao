package com.wbiao.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.annotation.Log;
import com.wbiao.oauth.service.UserLoginService;
import com.wbiao.util.AuthToken;
import com.wbiao.util.HttpClient;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserLoginController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    private static final String APP_KEY = "4074085384";

    private static final String APP_SECRET = "89d738bdbc4fea464f70dfbcda3fd93c";

    private static final String REDIRECT_URI= "http://www.wbiao.com/userPage/login.html";

    @Autowired
    private UserLoginService userLoginService;

    /**
     *  用户名密码登录
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    @Log
    public ResultUtil<AuthToken> login(@RequestParam("username") String username, @RequestParam("password") String password){
        //授权模式
      String grant_type = "password";
      AuthToken token = null;
      try {
          token = userLoginService.login(username, password, clientId, clientSecret, grant_type);
      }catch (Exception e){
          e.printStackTrace();
          return ResultUtil.error();
      }
        return ResultUtil.ok(token);
    }

    /**
     *  手机登录
     * @param phone
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/smsLogin")
    @Log
    public ResultUtil<AuthToken> smsLogin(@RequestParam("phone") String phone, @RequestParam("code") String code){
        //授权模式
        String grant_type = "sms_code";
        AuthToken token = null;
        try {
            token = userLoginService.login(phone, code, clientId, clientSecret, grant_type);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok(token);
    }

    /**
     *  微博登录回调地址
     *
     * @param code
     * @return
     */
    @RequestMapping("/wbLogin")
    public ResultUtil<AuthToken> wbLogin(@RequestParam("code")String code){
        try {
            //通过状态码，获取access_token
            String url = "https://api.weibo.com/oauth2/access_token?client_id="+APP_KEY+"&client_secret="+APP_SECRET+"&grant_type=authorization_code&code="+code+"&redirect_uri="+REDIRECT_URI;

            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);

            httpClient.post();
            //获取返回的参数
            String content = httpClient.getContent();
            Map<String,String> map = JSONObject.parseObject(content, Map.class);
            if(map.containsKey("access_token")){
                String access_token = map.get("access_token");
                String uid = map.get("uid");
                //把微博登录扩展到认证系统中
                String grant_type = "wbLogin";
                AuthToken authToken = userLoginService.login(access_token, uid, clientId, clientSecret, grant_type);

                //返回token
                return ResultUtil.ok(authToken);


            }else{
                return ResultUtil.error();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }

    }


}
