package cn.edu.tuyt.sea2.seandisk.module.sys.form;

import lombok.Data;

/**
 * 登录表单
 *
 * @author klenkiven
 */
@Data
public class SysLoginForm {

    private String username;

    private String password;

    private String captcha;

    private String uuid;

}
