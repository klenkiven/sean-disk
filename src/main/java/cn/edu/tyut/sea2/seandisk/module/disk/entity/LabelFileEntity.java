package cn.edu.tyut.sea2.seandisk.module.disk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件标签关系表
 * 
 * @author klenkiven
 */
@Data
@TableName("sean_label_file")
public class LabelFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 逻辑文件唯一标识
	 */
	private String fileId;
	/**
	 * 文件标签唯一标识
	 */
	private String labelId;

}
