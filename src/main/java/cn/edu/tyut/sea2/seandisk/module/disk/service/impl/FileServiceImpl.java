package cn.edu.tyut.sea2.seandisk.module.disk.service.impl;

import cn.edu.tyut.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tyut.sea2.seandisk.common.validation.Assert;
import cn.edu.tyut.sea2.seandisk.module.disk.component.LocalFileOperation;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelFileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.FileMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.LabelFileMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.FileDetailVO;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("fileService")
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    private final LocalFileOperation localFileOperation;
    private final LabelFileMapper labelFileMapper;

    @Override
    public Page<FileEntity> queryPage(Page<FileEntity> queryPage, String key,
                                      List<String> labelIdList, SysUserEntity user) {
        Assert.isNull(labelIdList, "标签列表对象不为null");
        // 构造文件列表查询条件
        QueryWrapper<FileEntity> query = new QueryWrapper<FileEntity>()
                .like(StringUtils.isNotEmpty(key), "filename", key)
                .eq("user_id", user.getUserId())
                .orderByDesc("create_time");

        // 查询带有条件标签的文件ID列表
        // 如果文件标签不为空（去除默认标签），且根据标签查询到的文件数量不为0
        if (labelIdList.size() != 0) {
            List<String> fileIdList = labelFileMapper.selectFileIdListByLabel(labelIdList);
            query.in(fileIdList.size() > 0, "file_id", fileIdList);
            // 如果查询不到文件，说明此标签没有文件，需要插入一条恒为false的语句
            query.in(fileIdList.size() == 0, "file_id", "-1");
        }

        return this.page(queryPage, query);
    }

    @Override
    public FileEntity saveFile(MultipartFile file, SysUserEntity user) {
        String physicalHash;
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
                return fileEntity;
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

    @Override
    public void updateFile(FileDetailVO file) {
        // 保存文件基础属性
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileId(file.getFileId());
        fileEntity.setFilename(file.getFilename());
        updateById(fileEntity);

        // 保存文件关联关系
        // 删除所有的原有关系，替换为新的关系
        String fileId = file.getFileId();
        labelFileMapper.delete(new QueryWrapper<LabelFileEntity>().eq("file_id", fileId));
        file.getFileLabelList().forEach((labelId) -> {
            LabelFileEntity labelFileEntity = new LabelFileEntity();
            labelFileEntity.setFileId(file.getFileId());
            labelFileEntity.setLabelId(labelId);
            labelFileMapper.insert(labelFileEntity);
        });
    }

    @Override
    public FileDetailVO getFileDetail(String fileId) {
        FileEntity byId = getById(fileId);
        FileDetailVO vo = new FileDetailVO();
        BeanUtils.copyProperties(byId, vo);

        // 获取文件关系
        List<String> labelIdList = labelFileMapper.selectLabelListByFileId(fileId);
        vo.setFileLabelList(labelIdList);
        return vo;
    }
}