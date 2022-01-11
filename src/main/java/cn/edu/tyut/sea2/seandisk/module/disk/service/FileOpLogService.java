package cn.edu.tyut.sea2.seandisk.module.disk.service;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文件操作日志表
 *
 * @author klenkiven
 */
public interface FileOpLogService extends IService<FileOpLogEntity> {

    Page<FileOpLogEntity> queryPage(Page<FileOpLogEntity> queryPage, String key);
}

