package cn.edu.tyut.sea2.seandisk.module.sys.oauth2;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserTokenEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.service.ShiroService;

import java.util.Set;

/**
 * 认证和鉴权Realm
 *
 * @author ：klenkiven
 */
@Component
@AllArgsConstructor
public class Oauth2Realm extends AuthorizingRealm {

    private final ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.selectByToken(accessToken);

        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        SysUserEntity user = shiroService.selectUser(tokenEntity.getUserId());
        //账号锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        return new SimpleAuthenticationInfo(user, accessToken, getName());
    }
}
