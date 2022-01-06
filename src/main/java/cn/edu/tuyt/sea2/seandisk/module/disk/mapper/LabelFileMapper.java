package cn.edu.tuyt.sea2.seandisk.module.disk.mapper;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.LabelFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件标签关系表
 * 
 * @author klenkiven
 */
@Mapper
public interface LabelFileMapper extends BaseMapper<LabelFileEntity> {
	
}
