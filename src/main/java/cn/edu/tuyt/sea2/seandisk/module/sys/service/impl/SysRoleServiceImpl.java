package cn.edu.tuyt.sea2.seandisk.module.sys.service.impl;

import cn.edu.tuyt.sea2.seandisk.module.sys.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.tuyt.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tuyt.sea2.seandisk.common.utils.Constant;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysRoleEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.mapper.SysRoleMapper;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysRoleMenuService;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysRoleService;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserRoleService;

import java.util.*;

/**
 * 角色信息服务实现类
 * @author ：klenkiven
 * @date ：2021/7/14 17:33
 */
@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = {GeneralException.class, Exception.class})
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        // 检查是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = {GeneralException.class, Exception.class})
    public void update(SysRoleEntity role) {
        this.updateById(role);

        // 检查是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = {GeneralException.class, Exception.class})
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }

    @Override
    public List<Long> listRoleIdByCreateUserId(Long createUserId) {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("create_user_id", createUserId);
        List<SysRoleEntity> sysRoleEntities = baseMapper.selectByMap(condition);
        List<Long> result = new ArrayList<>();
        sysRoleEntities.forEach((item) -> {
            result.add(item.getRoleId());
        });
        return result;
    }

    /**
     * 检查权限是否越权
     * 薮泽抛出异常回滚事务处理
     */
    private void checkPrems(SysRoleEntity role){
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if(role.getCreateUserId() == Constant.ADMIN){
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserMapper.listAllMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new GeneralException("新增角色的权限，已超出你的权限范围");
        }
    }

}
