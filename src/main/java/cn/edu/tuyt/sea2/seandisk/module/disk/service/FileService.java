package cn.edu.tuyt.sea2.seandisk.module.disk.service;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 云盘文件表
 *
 * @author klenkiven
 */
public interface FileService extends IService<FileEntity> {

    Page<FileEntity> queryPage(Page<FileEntity> queryPage, String key);

    /**
     * 上传文件
     * @param file 文件流
     * @param user 当前登录的用户
     */
    void saveFile(MultipartFile file, SysUserEntity user);

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
}

