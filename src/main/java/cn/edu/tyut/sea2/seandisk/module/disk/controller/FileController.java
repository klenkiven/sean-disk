package cn.edu.tyut.sea2.seandisk.module.disk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.edu.tyut.sea2.seandisk.common.utils.FileUtils;
import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import cn.edu.tyut.sea2.seandisk.module.disk.annotation.DiskOpLog;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.FileListParam;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.LabelVO;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 云盘文件表
 *
 * @author klenkiven
 */
@RestController
@RequestMapping("disk/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("disk:file:save")
    public Result<?> save(@RequestBody MultipartFile file) {
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        fileService.saveFile(file, user);
        return Result.ok();
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
//    @RequiresPermissions("disk:file:download")
    public void downloadFile(@RequestParam String fileId, HttpServletResponse response) throws IOException {
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            FileEntity file = fileService.getFileById(fileId);
            String filenameEncoded = URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8);
            response.addHeader("Content-Disposition", "attachment; filename=" + filenameEncoded);
            response.addHeader("Content-Length", file.getLength().toString());
            InputStream fileInputStream = fileService.getFileInputStream(file.getPhysicalHash());
            FileUtils.inputToOutputStream(fileInputStream, outputStream);
        }
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("disk:file:list")
    @DiskOpLog("list-file")
    public Result<Page<FileEntity>> list(FileListParam params){
        // 处理前端传来的标签ID列表
        // 如果ID列表为null，则默认传递空的列表对象
        List<String> labelIdList = params.getLabelIdList();
        if (labelIdList == null) {
            labelIdList = new ArrayList<>();
        } else {
            labelIdList = labelIdList.stream()
                    .filter((labelId) -> !labelId.equals(LabelVO.DEFAULT_LABEL.getLabelId()))
                    .collect(Collectors.toList());
        }
        // 获取当前登录用户的信息
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        // 查询文件查询结果
        Page<FileEntity> queryPage = new Page<>(params.getPage(), params.getLimit());
        Page<FileEntity> page = fileService.queryPage(queryPage, params.getKey(), labelIdList, user);

        return Result.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileId}")
    @RequiresPermissions("disk:file:info")
    public Result<FileEntity> info(@PathVariable("fileId") String fileId){
		FileEntity file = fileService.getById(fileId);

        return Result.ok(file);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("disk:file:update")
    public Result<?> update(@RequestBody FileEntity file){
		fileService.updateById(file);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("disk:file:delete")
    public Result<?> delete(@RequestBody String[] fileIds){
		fileService.removeByIds(Arrays.asList(fileIds));
        return Result.ok();
    }

}
