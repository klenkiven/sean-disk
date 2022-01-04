package cn.edu.tuyt.sea2.seandisk.module.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import cn.edu.tuyt.sea2.seandisk.common.utils.Constant;
import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import cn.edu.tuyt.sea2.seandisk.common.validation.Assert;
import cn.edu.tuyt.sea2.seandisk.common.validation.ValidatorUtils;
import cn.edu.tuyt.sea2.seandisk.common.validation.group.AddGroup;
import cn.edu.tuyt.sea2.seandisk.common.validation.group.UpdateGroup;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.form.PasswordForm;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserRoleService;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 * @author ：klenkiven
 * @date ：2021/7/13 15:21
 */
@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
public class SysUserController extends AbstractController {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public Result<?> info(){
        SysUserEntity user = new SysUserEntity();
        user.setUserId(getUserId());
        user.setUsername(getUser().getUsername());
        user.setUid(getUser().getUid());
        return Result.ok(user);
    }

    /**
     * 修改密码
     * TODO  需要系统日志
     */
    @PostMapping("/password")
    public Result<?> password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return Result.fail().message("原密码不正确");
        }

        return Result.ok();
    }

    /**
     * 选择用户列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:user:select")
    public Result<List<JSONObject>> select() {
        List<Long> userIdList = sysUserRoleService.listUserIdByRoleName("员工");
        List<SysUserEntity> sysUserEntities = sysUserService.listByIds(userIdList);

        List<JSONObject> result = new ArrayList<>();
        if (sysUserEntities == null) {
            return Result.fail(result).message("尚未创建员工");
        }
        for(SysUserEntity user: sysUserEntities) {
            JSONObject item = new JSONObject();
            // 参与会的人员选择上不包含自己
            if (getUserId().equals(user.getUserId())) {
                continue;
            }
            item.put("name", user.getName());
            item.put("uid", user.getUid());

            result.add(item);
        }

        return Result.ok(result);
    }

    /**
     * 所有用户列表
     */
    @GetMapping("/list/{current}/{limit}")
    @RequiresPermissions("sys:user:list")
    public Result<?> list(@PathVariable("current") Integer current,
                          @PathVariable("limit") Integer limit,
                          @RequestParam Map<String, Object> params){

        String username = (String)params.get("username");
        // 创建Page对象，传递当前页，每页记录数
        Page<SysUserEntity> page = new Page<>(current, limit);

        QueryWrapper<SysUserEntity> query = new QueryWrapper<>();
        query.like(!StringUtils.isBlank(username),"username", params.get("username"));

        //只有超级管理员，才能查看所有管理员列表
        if(getUserId() != Constant.ADMIN){
            query.eq("create_user_id", getUserId());
        }

        Page<SysUserEntity> result = sysUserService.page(page, query);

        return Result.ok(result);
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result<?> info(@PathVariable("userId") Long userId){
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.listRoleIdByUserId(userId);
        user.setRoleIdList(roleIdList);

        return Result.ok(user);
    }

    /**
     * 保存用户
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result<?> save(@RequestBody SysUserEntity user){
        ValidatorUtils.validateEntity(user, AddGroup.class);

        try {
            user.setCreateUserId(getUserId());
            sysUserService.saveUser(user);
        } catch (DuplicateKeyException e) {
            return Result.fail().message("工号或者用户名重复");
        }

        return Result.ok();
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result<?> update(@RequestBody SysUserEntity user){
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.update(user);

        return Result.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result<?> delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return Result.fail().message("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, getUserId())){
            return Result.fail().message("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return Result.ok();
    }

}
