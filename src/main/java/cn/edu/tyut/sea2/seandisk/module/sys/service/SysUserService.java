package cn.edu.tyut.sea2.seandisk.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;

import java.util.List;

/**
 * 系统用户
 *
 * @author ：klenkiven
 * @date ：2021/7/12 9:04
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     * @return 用户所有权限列表
     */
    List<String> listAllPerms(Long userId);

    /**
     * 根据用户ID查询所有的菜单ID
     * @param userId 用户ID
     * @return 菜单ID列表
     */
    List<Long> listAllMenuId(Long userId);

    /**
     * 根据用户的Name查询某个系统用户
     * @param userName 用户ID
     * @return 系统用户
     */
    SysUserEntity getSysUserByUserName(String userName);

    /**
     * 保存用户
     * @param user 用户对象
     */
    void saveUser(SysUserEntity user);

    /**
     * 更新用户信息
     * @param user 新的用户对象
     */
    void update(SysUserEntity user);

    /**
     * 删除用户
     * @param userIds 用户ID列表
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     * @return 是否更新成功
     */
    boolean updatePassword(Long userId, String password, String newPassword);

}
