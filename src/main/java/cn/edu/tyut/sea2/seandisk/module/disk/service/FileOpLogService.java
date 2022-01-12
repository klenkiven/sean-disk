package cn.edu.tyut.sea2.seandisk.module.disk.service;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文件操作日志表
 *
 * @author klenkiven
 */
public interface FileOpLogService extends IService<FileOpLogEntity> {

    Page<FileOpLogEntity> queryPage(Page<FileOpLogEntity> queryPage, String key);

    /**
     * 为文件记录操作
     * @param operation 文件操作
     * @param user 操作者
     * @param file 文件信息
     */
    void logFileOp(String operation, SysUserEntity user, FileEntity file);
}

