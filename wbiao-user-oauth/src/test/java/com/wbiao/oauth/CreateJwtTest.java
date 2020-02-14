package com.wbiao.oauth;


public class CreateJwtTest {

   /* @Test
    public void testCreateToken(){
        //加载证书
        ClassPathResource resource = new ClassPathResource("wanbiao.jks");

        //读取证书数据
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(resource,"wanbiao".toCharArray());

        //获取证书中的公钥和私钥
        KeyPair keyPair = factory.getKeyPair("wanbiao","wanbiao".toCharArray());
        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //创建令牌
        Map<String,Object> payload = new HashMap<>();
        payload.put("username","zhangsan");
        payload.put("nickname","aaaa");
        payload.put("age",14);
        payload.put("address","beijing");

        Jwt jwt = JwtHelper.encode(JSONObject.toJSONString(payload),new RsaSigner(privateKey));
        System.out.println(jwt.getEncoded());
    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiYmVpamluZyIsIm5pY2tuYW1lIjoiYWFhYSIsImFnZSI6MTQsInVzZXJuYW1lIjoiemhhbmdzYW4ifQ.LHp9a57WhXVpwKUB9SLbJ0A1cXvipe4BtL3WegNoQzUkAXg6t0mwMORk2H2a_WF0A7hh09m9sFxZk-Dg_LrJfInnFWCrxDOhymPXDPC-AtOZ-e5_sKb41snBHdlFZE3fJ4yhed_IsMM1r10-LrZs3EP1hoQTRitScEzKRB0BHBAG1Gn92ijVx0-b1WgTTXt8OdicULKWzH0YFbdrFFilmD7SdAVKO0763nzKPwhzxvkJ7QEiiM2dOpKUOcvXxviIFuSUPms__DitZ8ijo4A3w0hDARMyhs2SJJ0CRbQ1V4Tlf500UOh25SwUoSHDQHQNbvPDy_VCNfdLSRHMUE76Kg";
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgCxP1ulNkEJuejY07n2rAwNXcPmsHCEYQumksEXbM3WEjdddTP0clAoSI/9ErA8Tq/jlm8ceAlkoeAExdYCCY34Os5Ck6tansFJg5rCqYyrKEg34pvgAxacaOIOZjvy+CtCjBVSU4K3PnE2jvHufTI1Ug3LUzo0tIldhgIWRB60p56g23vNJoNIWpW/Ro7uest7X91P3EN6F8yBS3/ZvblJ4OUk0W6TsPkLBpjVliAxV9UAkKjWqPQhwlsXiVL6AzWYUSLWHUpNMsHXBNu27JeKLT0HdoCwcoj7M3VsIDzmee1Pkq98MZSzWIP9mTwlBYDeSDx5VaHWQyznHcj4RRwIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        System.out.println(jwt.getClaims());
    }
   */
}
