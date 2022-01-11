package cn.edu.tyut.sea2.seandisk.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * @author ：klenkiven
 * @date ：2021/7/13 12:14
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * 保存角色和菜单的关系
     * @param roleId 角色ID
     * @param menuIdList 菜单ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Long> listMenuIdByRoleId(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     * @param roleIds 角色ID
     * @return 成功删除数量
     */
    int deleteBatch(Long[] roleIds);

}
