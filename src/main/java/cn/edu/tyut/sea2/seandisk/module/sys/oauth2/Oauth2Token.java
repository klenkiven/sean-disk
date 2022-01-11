package cn.edu.tyut.sea2.seandisk.module.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 认证Token
 * @author ：klenkiven
 * @date ：2021/7/12 9:45
 */
public class Oauth2Token implements AuthenticationToken {

    private final String token;

    public Oauth2Token(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
