package cn.edu.tyut.sea2.seandisk.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色信息服务
 *
 * @author ：klenkiven
 * @date ：2021/7/14 17:32
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 保存角色信息
     * @param role 角色信息
     */
    void saveRole(SysRoleEntity role);

    /**
     * 更新角色信息
     * @param role 角色信息
     */
    void update(SysRoleEntity role);

    /**
     * 删除角色信息
     * @param roleIds 角色ID列表
     */
    void deleteBatch(Long[] roleIds);


    /**
     * 查询用户创建的角色ID列表
     * @param createUserId 创建用户ID
     * @return 角色ID列表
     */
    List<Long> listRoleIdByCreateUserId(Long createUserId);

}
