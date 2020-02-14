package com.wbiao.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  全局过滤器，
 */
@Component
public class AuthorizeFilter extends ZuulFilter {

    //令牌的名字
    private static final String AUTHORIZE_TOKEN = "Authorization";
    /**
     *  filterType：返回一个字符串代表过滤器的类型，
     *      在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     *          pre：可以在请求被路由之前调用
     *          route：在路由请求时候被调用
     *          post：在route和error过滤器之后被调用
     *          error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     *  通过int值来定义过滤器的执行顺序
     *      数字越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     *  返回一个boolean类型来判断该过滤器是否要执行，
     *      所以通过此函数可实现过滤器的开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *  过滤器具体的逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        //判断请求路径是否需要校验

        String uri = request.getRequestURI();
        System.out.println("请求的url:"+uri);
        if(RequestUrlFilter.hasURLAuthorize(uri)){
            return null;
        }

        //获取令牌信息：
        // 1）头文件
        String token = request.getHeader(AUTHORIZE_TOKEN);
        //参数为true，令牌在头文件中，false不在头文件中
        boolean hasToken = true;

        // 2）参数
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(AUTHORIZE_TOKEN);
            hasToken = false;
            //
        }

        // 3）cookie
        if(StringUtils.isEmpty(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies!=null&&cookies.length!=0){
                for(Cookie cookie :cookies){
                    String name = cookie.getName();
                    if(AUTHORIZE_TOKEN.equals(name)){
                        token = cookie.getValue();
                    }
                }
            }
        }

        //如果没有令牌则拦截
        if(StringUtils.isEmpty(token)){
            // 过滤该请求，不对其进行路由
            currentContext.setSendZuulResponse(false);
            //设置没有权限的状态码401
            currentContext.setResponseStatusCode(401);
            // 设置响应信息
            currentContext.setResponseBody("");
        }else{
            if(!hasToken){
                if(!token.startsWith("bearer ") && !token.startsWith("Bearer ")){
                    token = "Bearer " + token;
                }
                //将令牌封装到头文件中
                currentContext.addZuulRequestHeader(AUTHORIZE_TOKEN, token);
            }
        }

        //如果存在令牌，校验令牌
        /*try {
             JWTUtil.parseJWT(token);
        }catch (Exception e){
            // 过滤该请求，不对其进行路由
            currentContext.setSendZuulResponse(false);
            //设置没有权限的状态码401
            currentContext.setResponseStatusCode(401);
            // 设置响应信息
            currentContext.setResponseBody("");
        }*/


        //有效放行
        return null;
    }
}
