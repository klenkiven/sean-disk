package cn.edu.tyut.sea2.seandisk.module.disk.controller;

import java.util.List;
import java.util.Map;

import cn.edu.tyut.sea2.seandisk.common.utils.Result;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.LabelRenameParam;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.LabelVO;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.LabelService;



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
     * 返回所有此用户的标签
     * <p>查询到此用户的所有标签，并且添加默认标签</p>
     */
    @GetMapping("/list")
    @RequiresPermissions("disk:label:list")
    public Result<List<LabelVO>> list(){
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        List<LabelVO> labelList = labelService.listAllByUserId(user.getUserId());
        labelList.add(LabelVO.DEFAULT_LABEL);
        return Result.ok(labelList);
    }


    /**
     * 修改标签的名称
     * <p>修改标签的名称，并且保证文件组织不变</p>
     */
    @PostMapping("/rename")
    @RequiresPermissions("disk:label:update")
    public Result<LabelEntity> rename(@RequestBody LabelRenameParam param){
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		labelService.rename(user.getUserId(), param.getLabelId(), param.getName());
        return Result.ok();
    }

    /**
     * 新增用户标签
     */
    @PostMapping("/add")
    @RequiresPermissions("disk:label:add")
    public Result<?> save(@RequestBody Map<String, String> param){
        String labelName = param.get("labelName");
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		labelService.save(labelName, user);

        return Result.ok();
    }

    /**
     * 删除用户标签
     */
    @PostMapping("/delete")
    @RequiresPermissions("disk:label:delete")
    public Result<?> delete(@RequestBody Map<String, String> param){
        String labelId = param.get("labelId");
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		labelService.removeById(labelId, user);

        return Result.ok();
    }

}
