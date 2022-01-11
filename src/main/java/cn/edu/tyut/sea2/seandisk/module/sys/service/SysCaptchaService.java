package cn.edu.tyut.sea2.seandisk.module.sys.service;



import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 系统验证码
 *
 * @author klenkiven
 * @email wzl709@outlook.com
 * @date 2021-07-12 09:57:43
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

    /**
     * 根据UUID获取验证码图片
     * @param uuid UUID唯一标识符
     * @return 验证码图片
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);

}

