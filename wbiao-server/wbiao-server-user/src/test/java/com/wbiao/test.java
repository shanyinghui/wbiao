package com.wbiao;

import com.wbiao.util.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootTest
public class test {

    @Test
    public void BCryptTest(){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder ();
        String encode = bCrypt.encode("wanbiao");

        System.out.println(encode);
    }

    @Test
    public void JWTTest(){
        JwtBuilder jwt = Jwts.builder();
        jwt.setIssuer("syh");//颁发者
        jwt.setIssuedAt(new Date()); //颁发时间
        jwt.setSubject("jwt测试"); //主题信息
        jwt.signWith(SignatureAlgorithm.HS256,"itcast"); //1、签名算法  2、秘钥
        String compact = jwt.compact();
        System.out.println(compact);
    }

    @Test
    public void parseJWT(){
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzeWgiLCJpYXQiOjE1Nzg3MjYzMTgsInN1YiI6Imp3dOa1i-ivlSJ9.BIas_peZ0SCtKUn0_dRV7uQ0h_9UoOPua6IXL2Hh9jo";

        Claims itcast = Jwts.parser().setSigningKey("itcast").parseClaimsJws(str).getBody();
        System.out.println(itcast.toString());
    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNTc5OTYxNTk0LCJqdGkiOiI2NWU1ZTYyOS1lZDk4LTQxNjItODc5Zi04MWVlZDE4YWVhOTciLCJjbGllbnRfaWQiOiJ3YW5iaWFvIn0.FhxfMTdGbBca89X64xqB4kQHzVJjYp7yZGznd36hLXGMTb4KCO-vq3MtumiXgxMKw4iCONk5YovHwYfysodkok67XDiA2ngQS5JmtG1SQa_2fWE5U15-0djaPczScH3A6vS6OXjx0_1KEjFqFlRnbLAoLraDGHmF_efq0bdwQaC7R-o4S2JhrDBipRwJC1L7ap5lpl3WlI0f0OyspHHATO1j-BFg3potYtIyMrBnQac3SVSzOa_lq3Ncz0eBVZkgHpr7hVoC7CV_TJt-MCeIzn04M6hr-AgmTHYDbphi4Q3VcHJIhVSrlTMxFUB5x7EhEHXe-GxdcJ-8U12B_i9kuA";
        boolean b = BCrypt.checkpw(token,"123456");
        System.out.println(b);
    }
}
