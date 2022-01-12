package cn.edu.tyut.sea2.seandisk.module.disk.service.impl;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.FileOpLogMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileOpLogService;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;

import java.util.Date;


@Service("fileOpLogService")
public class FileOpLogServiceImpl extends ServiceImpl<FileOpLogMapper, FileOpLogEntity> implements FileOpLogService {

    @Override
    public Page<FileOpLogEntity> queryPage(Page<FileOpLogEntity> queryPage, String key) {
        return this.page(queryPage,
                new QueryWrapper<FileOpLogEntity>()
                        .like("filename", key));
    }

    @Override
    public void logFileOp(String operation, SysUserEntity user, FileEntity file) {
        FileOpLogEntity logEntity = new FileOpLogEntity();
        BeanUtils.copyProperties(file, logEntity);
        BeanUtils.copyProperties(user, logEntity);
        logEntity.setOperation(operation);
        logEntity.setLogTime(new Date());
        baseMapper.insert(logEntity);
    }
}