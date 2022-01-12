package cn.edu.tyut.sea2.seandisk.module.disk.vo;

import lombok.Data;

import java.util.List;

/**
 * 请求文件列表参数包装类
 * @author KlenKiven
 */
@Data
public class FileListParam {

    private Integer page;

    private Integer limit;

    private String key;

    private List<String> labelIdList;

}
