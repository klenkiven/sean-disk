package cn.edu.tuyt.sea2.seandisk.module.disk.service.impl;

import cn.edu.tuyt.sea2.seandisk.module.disk.mapper.FileOpLogMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.FileOpLogService;


@Service("fileOpLogService")
public class FileOpLogServiceImpl extends ServiceImpl<FileOpLogMapper, FileOpLogEntity> implements FileOpLogService {

}