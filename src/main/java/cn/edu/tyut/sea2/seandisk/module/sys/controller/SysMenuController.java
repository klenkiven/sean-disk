package cn.edu.tyut.sea2.seandisk.module.sys.controller;

import cn.edu.tyut.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tyut.sea2.seandisk.common.utils.Constant;
import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysMenuEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.service.ShiroService;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author ：klenkiven
 * @date ：2021/7/13 12:00
 */
@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
public class SysMenuController extends AbstractController {

    private final SysMenuService sysMenuService;
    private final ShiroService shiroService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public Result<?> nav(){

        List<SysMenuEntity> menuList = sysMenuService.listUserMenu(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("menuList", menuList);
        resultMap.put("permissions", permissions);
        return Result.ok(resultMap);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for(SysMenuEntity sysMenuEntity : menuList){
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if(parentMenuEntity != null){
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result<?> select() {
        // 查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.listNotButtonMenu();

        // 添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return Result.ok(menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result<?> info(@PathVariable("menuId") Long menuId){
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return Result.ok(menu);
    }

    /**
     * 保存
     * TODO 保存系统日志
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result<?> save(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        // 保存数据
        sysMenuService.save(menu);

        return Result.ok();
    }

    /**
     * 更新
     * TODO 保存系统日志
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result<?> update(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        // 保存数据
        sysMenuService.updateById(menu);

        return Result.ok();
    }

    /**
     * 删除
     * 此删除功能不支持递归删除，因此必须是空的才可以删除。
     * TODO 保存系统日志
     */
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public Result<?> delete(@PathVariable("menuId") Long menuId) {

        if(menuId <= 50){
            return Result.fail().message("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.listParentId(menuId);
        if(menuList.size() > 0){
            return Result.fail().message("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return Result.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new GeneralException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new GeneralException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new GeneralException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new GeneralException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new GeneralException("上级菜单只能为菜单类型");
            }
        }
    }
}
