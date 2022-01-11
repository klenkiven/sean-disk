package cn.edu.tyut.sea2.seandisk.module.disk.mapper;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 云盘标签表
 * 
 * @author klenkiven
 */
@Mapper
public interface LabelMapper extends BaseMapper<LabelEntity> {
	
}
