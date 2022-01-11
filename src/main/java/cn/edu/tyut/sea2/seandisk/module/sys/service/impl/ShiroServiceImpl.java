package cn.edu.tyut.sea2.seandisk.module.sys.service.impl;

import cn.edu.tyut.sea2.seandisk.common.utils.Constant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysMenuEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserTokenEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.mapper.SysMenuMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.mapper.SysUserMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.mapper.SysUserTokenMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.service.ShiroService;

import java.util.*;

/**
 * Shiro 相关服务
 * 这个服务独立于其他的服务，因此直接就是使用对应的Mapper而不是调用其他的服务
 *
 * @author ：klenkiven
 * @date ：2021/7/12 9:52
 */
@Service("ShiroService")
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {

    private final SysMenuMapper sysMenuDao;
    private final SysUserMapper sysUserDao;
    private final SysUserTokenMapper sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.listAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity selectByToken(String token) {
        QueryWrapper<SysUserTokenEntity> query = new QueryWrapper<>();
        query.eq("token", token);
        return sysUserTokenDao.selectOne(query);
    }

    @Override
    public SysUserEntity selectUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
