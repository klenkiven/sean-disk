package cn.edu.tuyt.sea2.seandisk.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import cn.edu.tuyt.sea2.seandisk.common.utils.TokenUtils;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserTokenEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.mapper.SysUserTokenMapper;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserTokenService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户Token服务
 * @author klenkiven
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserTokenEntity> implements SysUserTokenService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Override
    public Result<?> createToken(Long userId) {

        //生成一个token
        String token = TokenUtils.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setExpireTime(expireTime);
            tokenEntity.setUpdateTime(now);
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("expire", EXPIRE);
        return Result.ok(resultMap);
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenUtils.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}