package cn.edu.tyut.sea2.seandisk.module.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;

/**
 * Controller的公共抽象组件
 *
 * @author ：klenkiven
 * @date ：2021/7/13 12:10
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

}
