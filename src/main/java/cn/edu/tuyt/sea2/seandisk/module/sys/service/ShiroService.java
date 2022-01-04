package cn.edu.tuyt.sea2.seandisk.module.sys.service;

import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * Shiro相关服务
 * @author ：klenkiven
 * @date ：2021/7/12 9:51
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     * @param userId 用户ID
     * @return 用户权限集合
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 根据Token寻找用户Token实体
     * @param token Token
     * @return 用户实体
     */
    SysUserTokenEntity selectByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId 用户ID
     * @return 用户实体对象
     */
    SysUserEntity selectUser(Long userId);

}
