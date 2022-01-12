package cn.edu.tyut.sea2.seandisk.module.disk.service;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.LabelVO;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 云盘标签表
 *
 * @author klenkiven
 */
public interface LabelService extends IService<LabelEntity> {

    /**
     * 根据前端请求获取文件标签的列表
     * @param userId 用户ID（由Session获取）
     * @return 返回用户文件标签列表
     */
    List<LabelVO> listAllByUserId(Long userId);

    /**
     * 修改用户的标签名
     * @param userId 用户ID，用来验证用户是否有权限修改
     * @param labelId 标签ID
     * @param name 标签名
     */
    void rename(Long userId, String labelId, String name);

    /**
     * 新增用户标签
     * <p>使用随机的UUID来生成唯一ID保存到数据库中</p>
     * @param labelName 标签名
     * @param user 当前登录用户
     */
    void save(String labelName, SysUserEntity user);

    /**
     * 根据标签ID删除单个标签
     * @param labelId 标签ID
     * @param user 用户
     */
    void removeById(String labelId, SysUserEntity user);
}

