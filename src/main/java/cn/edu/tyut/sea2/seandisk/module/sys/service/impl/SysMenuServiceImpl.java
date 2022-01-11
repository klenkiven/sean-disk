package cn.edu.tyut.sea2.seandisk.module.sys.service.impl;

import cn.edu.tyut.sea2.seandisk.common.utils.Constant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysMenuEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.mapper.SysMenuMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysMenuService;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysRoleMenuService;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 系统菜单
 * @author ：klenkiven
 * @date ：2021/7/13 12:04
 */
@Service("sysMenuService")
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    private final SysUserService sysUserService;
    private final SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> listParentId(Long parentId, List<Long> menuIdList) {
        // 查询到所有的相对父级的一级子节点
        List<SysMenuEntity> menuList = listParentId(parentId);

        if(menuIdList == null){
            return menuList;
        }

        // 筛选出所有用户有权限使用的菜单
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> listParentId(Long parentId) {

        QueryWrapper<SysMenuEntity> query = new QueryWrapper<>();
        query.eq("parent_id", parentId);
        query.orderBy(true, true, "order_num");

        return baseMapper.selectList(query);
    }

    @Override
    public List<SysMenuEntity> listNotButtonMenu() {
        QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("type", 2);
        queryWrapper.orderBy(true, true, "order_num");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysMenuEntity> listUserMenu(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.listAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        HashMap<String, Object> removeMap = new HashMap<>();
        removeMap.put("menu_id", menuId);
        sysRoleMenuService.removeByMap(removeMap);
    }

    /**
     * 获取所有的菜单列表
     * @param menuIdList 所有有权限的菜单清单
     * @return 所有的菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = listParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归获取所有的Menu列表
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for(SysMenuEntity entity : menuList){
            //目录
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(listParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
