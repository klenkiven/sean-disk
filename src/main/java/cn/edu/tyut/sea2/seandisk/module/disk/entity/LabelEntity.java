package cn.edu.tyut.sea2.seandisk.module.disk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 云盘标签表
 * 
 * @author klenkiven
 */
@Data
@TableName("sean_label")
public class LabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件标签唯一标识
	 */
	@TableId(type = IdType.INPUT)
	private String labelId;
	/**
	 * 标签创建者ID
	 */
	private Long userId;
	/**
	 * 文件标签名
	 */
	private String labelName;
	/**
	 * 是否属于系统标签
	 */
	private Integer isSystem;
	/**
	 * 标签创建时间
	 */
	private Date createTime;
	/**
	 * 标签更新时间
	 */
	private Date updateTime;

}
