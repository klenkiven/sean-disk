package cn.edu.tyut.sea2.seandisk.module.disk.mapper;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件标签关系表
 * 
 * @author klenkiven
 */
@Mapper
public interface LabelFileMapper extends BaseMapper<LabelFileEntity> {

    /**
     * 根据标签列表查找文件ID
     * <p>这里默认标签就是检索全部文件，当前端传来ID标签列表的时候，标签ID列表为空。
     * 这个时候无法进行检索，需要在Service层进行处理。</p>
     * @param labelIdList 标签ID列表，对象不能为空而且长度不能为0
     * @return 文件ID列表
     */
    List<String> selectFileIdListByLabel(@Param("labelIdList") List<String> labelIdList);
}
