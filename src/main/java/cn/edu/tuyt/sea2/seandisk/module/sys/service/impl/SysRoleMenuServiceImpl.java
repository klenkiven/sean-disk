package cn.edu.tuyt.sea2.seandisk.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysRoleMenuEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.mapper.SysRoleMenuMapper;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysRoleMenuService;

import java.util.HashMap;
import java.util.List;

/**
 * 角色菜单联系服务
 * @author ：klenkiven
 * @date ：2021/7/13 12:14
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        // 删除所有关系
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("role_id", roleId);
        this.removeByMap(condition);

        if (menuIdList == null || menuIdList.size() == 0) {
            return;
        }

        // 保存所有关系
        for (Long menuId: menuIdList) {
            SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);

            this.save(roleMenuEntity);
        }
    }

    @Override
    public List<Long> listMenuIdByRoleId(Long roleId) {
        return baseMapper.listMenuIdByRoleId(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
