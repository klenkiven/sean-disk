package cn.edu.tuyt.sea2.seandisk.module.disk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 云盘文件表
 * 
 * @author klenkiven
 */
@Data
@TableName("sean_file")
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 逻辑文件唯一标识
	 */
	@TableId
	private String fileId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 文件名
	 */
	private String filename;
	/**
	 * 文件大小
	 */
	private Long length;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 物理文件哈希
	 */
	private String physicalHash;
	/**
	 * 文件版本
	 */
	private Long version;
	/**
	 * 文件创建时间
	 */
	private Date createTime;
	/**
	 * 文件更新时间
	 */
	private Date updateTime;

}
