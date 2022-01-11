package cn.edu.tyut.sea2.seandisk.module.disk.service.impl;

import cn.edu.tyut.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tyut.sea2.seandisk.module.disk.component.LocalFileOperation;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.FileMapper;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;


@Service("fileService")
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    private final LocalFileOperation localFileOperation;

    @Override
    public Page<FileEntity> queryPage(Page<FileEntity> queryPage, String key) {
        return this.page(
                queryPage,
                new QueryWrapper<FileEntity>()
                        .like(StringUtils.isNotEmpty(key), "filename", key)
        );
    }

    @Override
    public void saveFile(MultipartFile file, SysUserEntity user) {
        String physicalHash = "";
        physicalHash = localFileOperation.writeFile(file);
        // 设置文件实体属性
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserId(user.getUserId());
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setLength(file.getSize());
        fileEntity.setType(file.getContentType());
        fileEntity.setPhysicalHash(physicalHash);
        // TODO 文件版本暂时不做处理
        fileEntity.setVersion(0L);
        fileEntity.setCreateTime(new Date());
        fileEntity.setUpdateTime(new Date());
        // 防止主键碰撞，循环保存操作直到保存成功
        for (;;) {
            try {
                String randomUUID = UUID.randomUUID().toString();
                fileEntity.setFileId(randomUUID);
                this.save(fileEntity);
                break;
            } catch (Exception e) {
                // Do Nothing
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public InputStream getFileInputStream(String physicalFileId) {
        InputStream inputStream;
        try {
            inputStream = localFileOperation.getFileInputStream(physicalFileId);
            return inputStream;
        } catch (IOException e) {
            throw new IllegalStateException("物理文件不存在");
        }
    }

    @Override
    public FileEntity getFileById(String fileId) {
        FileEntity file = this.getById(fileId);
        if (file == null) {
            throw new GeneralException("文件不存在！");
        }
//        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
//        if (!Objects.equals(file.getUserId(), user.getUserId())) {
//            throw new GeneralException("此文件不属于该用户");
//        }
        return file;
    }
}