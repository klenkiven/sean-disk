package cn.edu.tuyt.sea2.seandisk.module.disk.controller;

import java.util.Arrays;
import java.util.Map;

import cn.edu.tuyt.sea2.seandisk.common.utils.Result;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.FileOpLogService;



/**
 * 文件操作日志表
 *
 * @author klenkiven
 */
@RestController
@RequestMapping("disk/fileoplog")
@RequiredArgsConstructor
public class FileOpLogController {
    private final FileOpLogService fileOpLogService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    @RequiresPermissions("disk:fileoplog:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = fileOpLogService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("disk:fileoplog:info")
    public Result<FileOpLogEntity> info(@PathVariable("id") Long id){
		FileOpLogEntity fileOpLog = fileOpLogService.getById(id);

        return Result.ok(fileOpLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("disk:fileoplog:save")
    public Result<?> save(@RequestBody FileOpLogEntity fileOpLog){
		fileOpLogService.save(fileOpLog);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("disk:fileoplog:update")
    public Result<?> update(@RequestBody FileOpLogEntity fileOpLog){
		fileOpLogService.updateById(fileOpLog);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("disk:fileoplog:delete")
    public Result<?> delete(@RequestBody Long[] ids){
		fileOpLogService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
