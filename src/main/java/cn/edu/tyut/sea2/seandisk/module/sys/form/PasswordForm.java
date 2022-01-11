package cn.edu.tyut.sea2.seandisk.module.sys.form;

import lombok.Data;

/**
 * 密码表单
 * @author ：klenkiven
 * @date ：2021/7/13 17:13
 */
@Data
public class PasswordForm {

    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

}
