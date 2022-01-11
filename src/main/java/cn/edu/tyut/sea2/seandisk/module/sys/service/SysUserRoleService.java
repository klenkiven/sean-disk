package cn.edu.tyut.sea2.seandisk.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户角色服务
 * @author ：klenkiven
 * @date ：2021/7/13 15:22
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    /**
     * 保存或者更新
     * @param userId 用户ID
     * @param roleIdList 用户角色列表
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Long> listRoleIdByUserId(Long userId);

    /**
     * 根据角色的名称，获得用户ID列表
     * @param roleName 角色名称
     * @return 用户ID列表
     */
    List<Long> listUserIdByRoleName(String roleName);

    /**
     * 根据角色ID数组，批量删除
     * @param roleIds 角色ID
     * @return 删除成功数
     */
    int deleteBatch(Long[] roleIds);

}
