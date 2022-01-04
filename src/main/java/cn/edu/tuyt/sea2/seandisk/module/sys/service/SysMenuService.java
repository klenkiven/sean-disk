package cn.edu.tuyt.sea2.seandisk.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * @author ：klenkiven
 * @date ：2021/7/13 12:01
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     *                    所谓用户菜单ID就是用户有权限使用的Menu的ID列表
     * @return 菜单列表
     */
    List<SysMenuEntity> listParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @return 菜单列表
     */
    List<SysMenuEntity> listParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     * @return 菜单列表
     */
    List<SysMenuEntity> listNotButtonMenu();

    /**
     * 获取用户菜单列表
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuEntity> listUserMenu(Long userId);

    /**
     * 删除菜单
     * @param menuId 菜单ID
     */
    void delete(Long menuId);

}
