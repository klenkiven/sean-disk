package cn.edu.tuyt.sea2.seandisk.module.disk.service.impl;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.mapper.FileMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tuyt.sea2.seandisk.module.disk.service.FileService;


@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

}