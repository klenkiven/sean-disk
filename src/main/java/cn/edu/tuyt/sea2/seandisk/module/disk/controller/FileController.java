package cn.edu.tuyt.sea2.seandisk.module.disk.controller;

import java.util.Arrays;

import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.FileService;

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
     * 列表
     */
//    @RequestMapping("/list")
//    @RequiresPermissions("disk:file:list")
//    public  list(@RequestParam Map<String, Object> params){
//        PageUtils page = fileService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


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
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("disk:file:save")
    public Result<?> save(@RequestBody FileEntity file){
		fileService.save(file);

        return Result.ok();
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
