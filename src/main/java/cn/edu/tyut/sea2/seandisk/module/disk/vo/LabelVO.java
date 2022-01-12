package cn.edu.tyut.sea2.seandisk.module.disk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件标签前后端传输包装类
 * @author KlenKiven
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelVO {

    /**
     * 默认标签
     */
    public static LabelVO DEFAULT_LABEL =
            new LabelVO("00000000000000000000000000000000", "所有文件", true);

    /**
     * 文件标签ID
     */
    private String labelId;

    /**
     * 文件标签名
     */
    private String labelName;

    /**
     * 是否是默认标签
     */
    private Boolean isDefault;
}
