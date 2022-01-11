package cn.edu.tyut.sea2.seandisk.module.disk.controller;

import java.util.Map;

import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.FileOpLogEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.FileOpLogService;



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
     * 查询出所有用户的操作log
     */
    @RequestMapping("/list")
    @RequiresPermissions("disk:fileoplog:list")
    public Result<Page<FileOpLogEntity>> list(@RequestParam Map<String, Object> params){
        int pageIndex = Integer.parseInt((String) params.get("page"));
        int limit = Integer.parseInt((String) params.get("limit"));
        String key = (String) params.get("key");
        Page<FileOpLogEntity> queryPage = new Page<>(pageIndex, limit);
        Page<FileOpLogEntity> page = fileOpLogService.queryPage(queryPage, key);

        return Result.ok(page);
    }

}
