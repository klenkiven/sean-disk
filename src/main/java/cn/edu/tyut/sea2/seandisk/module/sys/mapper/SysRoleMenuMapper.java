package cn.edu.tyut.sea2.seandisk.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author klenkiven
 * @email wzl709@outlook.com
 * @date 2021-07-12 09:12:55
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

    /**
     * 根据角色ID查询所有的菜单信息
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> listMenuIdByRoleId(Long roleId);

    /**
     * 根据角色ID批量删除信息
     * @param roleIds 角色ID
     * @return 成功删除数量
     */
    int deleteBatch(Long[] roleIds);
}
