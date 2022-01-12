package cn.edu.tyut.sea2.seandisk.module.disk.vo;

import lombok.Data;

import java.util.List;

/**
 * 文件内容修改类
 */
@Data
public class FileDetailVO {

    private String fileId;

    private String filename;

    private List<String> fileLabelList;

}
