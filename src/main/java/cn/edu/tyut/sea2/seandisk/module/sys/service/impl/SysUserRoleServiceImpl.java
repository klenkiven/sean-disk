package cn.edu.tyut.sea2.seandisk.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserRoleEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.mapper.SysUserRoleMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.service.SysUserRoleService;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：klenkiven
 * @date ：2021/7/13 15:38
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("user_id", userId);
        //先删除用户与角色关系
        this.removeByMap(condition);

        // 如果是空的就不要再访问数据库了
        if(roleIdList == null || roleIdList.size() == 0){
            return;
        }

        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public List<Long> listRoleIdByUserId(Long userId) {
        return baseMapper.listRoleIdByUserId(userId);
    }

    @Override
    public List<Long> listUserIdByRoleName(String roleName) {
        return baseMapper.listUserIdByRoleName(roleName);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
