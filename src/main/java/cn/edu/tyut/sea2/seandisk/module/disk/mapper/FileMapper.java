package cn.edu.tyut.sea2.seandisk.module.disk.mapper;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 云盘文件表
 * 
 * @author klenkiven
 */
@Mapper
public interface FileMapper extends BaseMapper<FileEntity> {
	
}
