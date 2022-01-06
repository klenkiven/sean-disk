package cn.edu.tuyt.sea2.seandisk.module.disk.service.impl;

import cn.edu.tuyt.sea2.seandisk.module.disk.mapper.LabelFileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.LabelFileEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.LabelFileService;


@Service("labelFileService")
public class LabelFileServiceImpl extends ServiceImpl<LabelFileMapper, LabelFileEntity> implements LabelFileService {
}