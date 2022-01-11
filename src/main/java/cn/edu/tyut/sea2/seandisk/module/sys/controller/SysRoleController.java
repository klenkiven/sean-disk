package cn.edu.tyut.sea2.seandisk.module.sys.controller;

import cn.edu.tyut.sea2.seandisk.common.utils.Constant;
import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import cn.edu.tyut.sea2.seandisk.common.validation.ValidatorUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysRoleEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysRoleMenuService;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysRoleService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色Controller
 * @author ：klenkiven
 * @date ：2021/7/14 18:38
 */
@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class SysRoleController extends AbstractController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @GetMapping("/list/{current}/{limit}")
    @RequiresPermissions("sys:role:list")
    public Result<?> list(@PathVariable("current") Integer current,
                          @PathVariable("limit") Integer limit,
                          @RequestParam Map<String, Object> params){
        String roleName = (String) params.get("roleName");
        Page<SysRoleEntity> page = new Page<>(current, limit);

        // 查询Wapper
        QueryWrapper<SysRoleEntity> query = new QueryWrapper<>();
        query.like(!StringUtils.isBlank(roleName), "role_name", roleName);

        //如果不是超级管理员，则只查询自己创建的角色列表
        if(getUserId() != Constant.ADMIN){
            query.eq("create_user_id", getUserId());
        }

        Page<SysRoleEntity> result = sysRoleService.page(page, query);

        return Result.ok(page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result<?> select(){
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if(getUserId() != Constant.ADMIN){
            map.put("create_user_id", getUserId());
        }
        List<SysRoleEntity> list = sysRoleService.listByMap(map);

        return Result.ok(list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result<?> info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.listMenuIdByRoleId(roleId);
        role.setMenuIdList(menuIdList);

        return Result.ok(role);
    }

    /**
     * 保存角色
     * TODO 保存角色系统LOG
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Result<?> save(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        role.setCreateTime(new Date());
        sysRoleService.save(role);
        return Result.ok();
    }

    /**
     * 更新角色
     * TODO 更新角色系统LOG
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Result<?> update(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.update(role);
        return Result.ok();
    }

    /**
     * 删除角色
     * TODO 删除角色系统LOG
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Result<?> delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return Result.ok();
    }

}
