package cn.edu.tyut.sea2.seandisk.module.disk.service;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.FileUpdateParam;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 云盘文件表
 *
 * @author klenkiven
 */
public interface FileService extends IService<FileEntity> {

    /**
     * 分页查询文件列表
     * <p>根据多种条件查询文件列表</p>
     * @param queryPage 查询页
     * @param key 查询关键字
     * @param labelIdList 查询所属的文件标签
     * @param user 登录用户信息
     * @return 返回页
     */
    Page<FileEntity> queryPage(Page<FileEntity> queryPage, String key, List<String> labelIdList, SysUserEntity user);

    /**
     * 上传文件
     * @param file 文件流
     * @param user 当前登录的用户
     */
    FileEntity saveFile(MultipartFile file, SysUserEntity user);

    /**
     * 根据文件的逻辑ID下载文件
     * @param fileId 逻辑文件标识符
     * @return 文件输出流
     */
    InputStream getFileInputStream(String fileId);

    /**
     * 根据文件的逻辑ID获取文件
     * @param fileId 逻辑文件标识符
     * @return 文件实体对象
     */
    FileEntity getFileById(String fileId);

    /**
     * 根据文件ID修改文件的相关属性
     * @param file 请求参数
     */
    void updateFile(FileUpdateParam file);
}

