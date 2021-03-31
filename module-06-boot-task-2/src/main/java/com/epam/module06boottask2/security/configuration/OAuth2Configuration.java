package com.epam.module06boottask2.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter{

    private String clientId = "pixeltrice";
    private String clientSecret = "pixeltrice-secret-key";
    private String privateKey =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
                    "MIIEpAIBAAKCAQEAxCXhvSqq1m2m7co5sZNaXTdb8O5imAZV99ebUhVUZLzMw4we\n" +
                    "SDmbf9nEg9+CmwZAEe+MWNT+z5Wg/lqXVNdPy9xEnygV1vv3QADN3GOiqQ4ZxD6V\n" +
                    "SbmILws/Nugouw4ojIimEFwBg19AShGHJ0MdUgvGetBd+9baguFHqLyVdZ9lV9/B\n" +
                    "uYv2MyRoM5LeX9MaED2ku4VbR5NFw6IrOH3jJW+cWUspbAIAnMCVSgo08XX+e0b5\n" +
                    "a5Q+ujfY1ZLYdizeKLXr2IWPFQOiQFN/LkEH/A9vtIl8sLTiHSCd1MfGKJVmfCND\n" +
                    "K5u1IKRCtVfO7uSQwQY+mLR+gbH+qP5xSetqjwIDAQABAoIBAD+nW36cHhzAMO/y\n" +
                    "NI7gFu8xS/EAK5bSV4u6uHkG2wBmFL1Q93LQ3xm6ff+dxTCx8pDfgFhDpv2OvVRN\n" +
                    "6dZqmUa7ELnltFC0LCTlHelrnBnorYv2E1R9lShPKhCTfsRVb+tLXTjp4sPTYXWo\n" +
                    "AohHS3iYwayRei6EJx1aJnsn2Aj3Q8wi2UGVO+N9nrh0Fr6K6LojLnMjjKR8udcl\n" +
                    "1nJ4ZY0SzB1uYQtzXND+HXyDj33vtKhGfxBdCyjvCf+ihnSfsHzrd5OdcgpI2CuM\n" +
                    "hnWbvTb6Fcuzhjjmg6b/0V+2n+0KkKjWxYeST4sNNHTLpfsIc11ZsFUI/Emll3xW\n" +
                    "datMiyECgYEA6MacPe542/Jf6tUQrdbepJaAcxqH7sfGISLTv1kupu+GZsLLQ3dr\n" +
                    "tjFJ845kuyWDB3vtgBtWIbpmyMFA/riX5WMO+bFx1VAQTFh4Ra76+hZygu5UYMC0\n" +
                    "MKLKrulpHL/njxUJxzoHSRpLZki8qUkFbihELu9Ff+9xS9/cllusvzMCgYEA17e/\n" +
                    "9Y98eseolfKTiEavIqY6d2oskBRqglMZqBne6Czzi/YvzJubDdiUEXOa7nCffnwP\n" +
                    "xKE5sH/QcyJ4AZ3sWJQDPRTEysTFeDrm/x3knjXVxxn316jIpJqyd9s9imftR39G\n" +
                    "wyx3TaztpIA/utMZ2J7cL1u1LW9rZDvkcWsc1zUCgYAOuABJKjsaLUlDYBWd6GhL\n" +
                    "n/ifTp4pyxgMIFejUdBRXfPxq3+O1I5LBvLWI7ra2pRedMefwhrAJzk4mH+zm5h/\n" +
                    "OLRJtZ6tDlL5gN6aCWZ1SdYT0V4kgArkyGZoiPnMh5XsxjfIETNEvBToctD7I1Lk\n" +
                    "lEc7cIXEgS94zL72AvX5YQKBgQCUcftkFYCY0UqfHVYPIm0kz/VN9b+CyrncwyjV\n" +
                    "uC/f4AcdGqYcDDywU4jZVY+ZY+Et3lvPeQ9E+T/N6ztgCXf6IKj14JPn4W7uZMJP\n" +
                    "bxfDE9Lv91vJiXnBZD9KIZIA67GqUNi2OKREzKqgWC0C+IBBxFhLMrXD9XeWe2yh\n" +
                    "7A7lvQKBgQDXMdMOAw9tNF97N3142wrzsr7HSG3Vj0rBnSheIXKon7OAKZ6Tw+U6\n" +
                    "lfR5mMWxAIjxLDApzur/7k6kVaFzffmNZlEgtn2u7ruNFq7Ati1dv6RdIv/ZRL7T\n" +
                    "N3w0zdfFCnZO98905XMp055dQf2ecMf12T4JThDNuXlwWu/HRO/2RA==\n" +
                    "-----END RSA PRIVATE KEY-----";
    private String publicKey =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxCXhvSqq1m2m7co5sZNa\n" +
                    "XTdb8O5imAZV99ebUhVUZLzMw4weSDmbf9nEg9+CmwZAEe+MWNT+z5Wg/lqXVNdP\n" +
                    "y9xEnygV1vv3QADN3GOiqQ4ZxD6VSbmILws/Nugouw4ojIimEFwBg19AShGHJ0Md\n" +
                    "UgvGetBd+9baguFHqLyVdZ9lV9/BuYv2MyRoM5LeX9MaED2ku4VbR5NFw6IrOH3j\n" +
                    "JW+cWUspbAIAnMCVSgo08XX+e0b5a5Q+ujfY1ZLYdizeKLXr2IWPFQOiQFN/LkEH\n" +
                    "/A9vtIl8sLTiHSCd1MfGKJVmfCNDK5u1IKRCtVfO7uSQwQY+mLR+gbH+qP5xSetq\n" +
                    "jwIDAQAB\n" +
                    "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }

}