package cn.edu.tyut.sea2.seandisk.module.disk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件操作日志表
 * 
 * @author klenkiven
 */
@Data
@TableName("sean_file_op_log")
public class FileOpLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志ID
	 */
	@TableId
	private Long id;
	/**
	 * 逻辑文件唯一标识
	 */
	private String fileId;
	/**
	 * 逻辑文件名
	 */
	private String filename;
	/**
	 * 文件操作者标识
	 */
	private Long userId;
	/**
	 * 文件操作者名称
	 */
	private String username;
	/**
	 * 文件操作
	 */
	private String operation;
	/**
	 * 日志发生时间
	 */
	private Date logTime;

}
