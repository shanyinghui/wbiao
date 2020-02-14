package com.wbiao.oauth.config;

import com.wbiao.oauth.util.UserJwt;
import com.wbiao.user.feignService.UserFeign;
import com.wbiao.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/*****
 * 自定义授权认证类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    UserFeign userFeign;

    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //================客户端认证 start====================
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            //查询数据库
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
                //return new User(username,new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        //================客户端认证 end====================

        //================用户的账户密码认证 start====================
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        ResultUtil<com.wbiao.user.pojo.User> result = userFeign.findUserByUsername(username);
        if(result == null || result.getData()==null){
            return null;
        }

        com.wbiao.user.pojo.User user = (com.wbiao.user.pojo.User)result.getData();

        //根据用户名查询用户信息
        String pwd = user.getPassword();

        //创建User对象
        String permissions = "user,vip";
        UserJwt userDetails = new UserJwt(username,pwd==null?"":pwd,AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        //================用户的账户密码认证 end====================
        return userDetails;
    }
}
