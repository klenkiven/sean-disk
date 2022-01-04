package cn.edu.tuyt.sea2.seandisk.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import cn.edu.tuyt.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysCaptchaEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.mapper.SysCaptchaMapper;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysCaptchaService;

import java.awt.image.BufferedImage;
import java.util.Date;


/**
 * 验证码服务
 *
 * @author klenkiven
 */
@Service("sysCaptchaService")
@AllArgsConstructor
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaMapper, SysCaptchaEntity> implements SysCaptchaService {

    private final Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {

        if (StringUtils.isBlank(uuid)) {
            throw new GeneralException("UUID不能为空");
        }

        // 生成文字验证码
        String code = producer.createText();

        SysCaptchaEntity captcha = new SysCaptchaEntity();
        captcha.setUuid(uuid);
        captcha.setCode(code);
        // 设置过期时间为5分钟以后
        captcha.setExpireTime(new Date(System.currentTimeMillis() + 5*60*1000));

        this.save(captcha);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {

        QueryWrapper<SysCaptchaEntity> query = new QueryWrapper<>();
        query.eq("uuid", uuid);
        SysCaptchaEntity target = this.getOne(query);

        if (target == null) {
            return false;
        }

        // 直接删除掉这条记录
        this.removeById(target.getUuid());

        return target.getCode().equals(code) && target.getExpireTime().compareTo(new Date()) > 0;
    }
}