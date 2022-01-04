package cn.edu.tuyt.sea2.seandisk.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author klenkiven
 * @email wzl709@outlook.com
 * @date 2021-07-12 09:12:55
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

    /**
     * 根据用户ID获取该用户的角色信息
     * @param userId 用户ID
     * @return 角色ID
     */
    List<Long> listRoleIdByUserId(Long userId);

    /**
     * 根据角色的名称，获得用户ID列表
     * @param roleName 角色名称
     * @return 用户ID列表
     */
    List<Long> listUserIdByRoleName(String roleName);

    /**
     * 根据角色ID数组，批量删除所有有关此角色的信息
     * @param roleIds 角色ID列表
     * @return 成功删除的数量
     */
    int deleteBatch(Long[] roleIds);
	
}
