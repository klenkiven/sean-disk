package cn.edu.tyut.sea2.seandisk.module.disk.service.impl;

import cn.edu.tyut.sea2.seandisk.module.disk.mapper.FileOpLogMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileOpLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;


@Service("fileOpLogService")
public class FileOpLogServiceImpl extends ServiceImpl<FileOpLogMapper, FileOpLogEntity> implements FileOpLogService {

    @Override
    public Page<FileOpLogEntity> queryPage(Page<FileOpLogEntity> queryPage, String key) {
        return null;
    }
}