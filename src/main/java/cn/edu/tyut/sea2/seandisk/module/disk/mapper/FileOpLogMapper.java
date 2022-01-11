package cn.edu.tyut.sea2.seandisk.module.disk.mapper;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件操作日志表
 * 
 * @author klenkiven
 * @email wzl709@outlook.com
 * @date 2022-01-06 09:57:28
 */
@Mapper
public interface FileOpLogMapper extends BaseMapper<FileOpLogEntity> {
	
}
