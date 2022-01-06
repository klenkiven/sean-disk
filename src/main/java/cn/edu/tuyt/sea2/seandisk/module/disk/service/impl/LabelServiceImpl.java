package cn.edu.tuyt.sea2.seandisk.module.disk.service.impl;

import cn.edu.tuyt.sea2.seandisk.module.disk.mapper.LabelMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.LabelEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.LabelService;


@Service("labelService")
public class LabelServiceImpl extends ServiceImpl<LabelMapper, LabelEntity> implements LabelService {
}