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

import cn.edu.tuyt.sea2.seandisk.module.disk.entity.LabelEntity;
import cn.edu.tuyt.sea2.seandisk.module.disk.service.LabelService;



/**
 * 云盘标签表
 *
 * @author klenkiven
 */
@RestController
@RequestMapping("disk/label")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    @RequiresPermissions("disk:label:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = labelService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{labelId}")
    @RequiresPermissions("disk:label:info")
    public Result<LabelEntity> info(@PathVariable("labelId") String labelId){
		LabelEntity label = labelService.getById(labelId);

        return Result.ok(label);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("disk:label:save")
    public Result<?> save(@RequestBody LabelEntity label){
		labelService.save(label);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("disk:label:update")
    public Result<?> update(@RequestBody LabelEntity label){
		labelService.updateById(label);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("disk:label:delete")
    public Result<?> delete(@RequestBody String[] labelIds){
		labelService.removeByIds(Arrays.asList(labelIds));

        return Result.ok();
    }

}
