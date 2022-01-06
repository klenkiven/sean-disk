package cn.edu.tuyt.sea2.seandisk.module.disk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import cn.edu.tuyt.sea2.seandisk.common.utils.FileUtils;
import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import cn.edu.tuyt.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.FileService;
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
    @RequiresPermissions("disk:file:download")
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
    @RequestMapping("/list")
    @RequiresPermissions("disk:file:list")
    public Result<Page<FileEntity>> list(@RequestParam Map<String, Object> params){
        int pageIndex = Integer.parseInt((String) params.get("page"));
        int limit = Integer.parseInt((String) params.get("limit"));
        String key = (String) params.get("key");
        Page<FileEntity> queryPage = new Page<>(pageIndex, limit);
        Page<FileEntity> page = fileService.queryPage(queryPage, key);

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
