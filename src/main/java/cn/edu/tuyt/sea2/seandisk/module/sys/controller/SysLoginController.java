package cn.edu.tuyt.sea2.seandisk.module.sys.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.form.SysLoginForm;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysCaptchaService;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserService;
import cn.edu.tuyt.sea2.seandisk.module.sys.service.SysUserTokenService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用户登录
 *
 * @author ：klenkiven
 * @date ：2021/7/12 9:03
 */
@RestController
@AllArgsConstructor
public class SysLoginController extends AbstractController {

    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SysCaptchaService sysCaptchaService;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse resp, String uuid) throws IOException {
        // 设置 不允许前端存储 和 获取的类型
        resp.setHeader("Cache-Control", "no-store, no-cache");
        resp.setContentType("image/jpeg");

        BufferedImage captcha = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(captcha, "jpg", outputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @PostMapping("sys/login")
    public Result<?> login(@RequestBody SysLoginForm loginForm) throws IOException {

        boolean validate = sysCaptchaService.validate(loginForm.getUuid(), loginForm.getCaptcha());
        if (!validate) {
            return Result.fail().message("验证码错误");
        }

        // 用户信息查询
        SysUserEntity user = sysUserService.getSysUserByUserName(loginForm.getUsername());

        // 校验密码和用户名
        if (user == null || !user.getPassword().equals(new Sha256Hash(loginForm.getPassword(), user.getSalt()).toHex())) {
            return Result.fail().message("用户名或者密码错误");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            return Result.fail().message("账号已经被锁定，请联系管理员");
        }

        //生成token，并保存到数据库
        return sysUserTokenService.createToken(user.getUserId());
    }

    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public Result<?> logout() {
        sysUserTokenService.logout(getUserId());
        return Result.ok();
    }

}
