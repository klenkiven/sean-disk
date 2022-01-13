package cn.edu.tyut.sea2.seandisk.module.disk.service.impl;

import cn.edu.tyut.sea2.seandisk.common.exception.GeneralException;
import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelFileEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.LabelFileMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.mapper.LabelMapper;
import cn.edu.tyut.sea2.seandisk.module.disk.vo.LabelVO;
import cn.edu.tyut.sea2.seandisk.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.tyut.sea2.seandisk.module.disk.entity.LabelEntity;
import cn.edu.tyut.sea2.seandisk.module.disk.service.LabelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service("labelService")
@RequiredArgsConstructor
public class LabelServiceImpl extends ServiceImpl<LabelMapper, LabelEntity> implements LabelService {
    private final LabelFileMapper labelFileMapper;

    @Override
    public List<LabelVO> listAllByUserId(Long userId) {
        List<LabelEntity> labelEntityList = baseMapper.selectList(
                new QueryWrapper<LabelEntity>()
                        .eq("user_id", userId));
        // 将Entity转化为VO
        List<LabelVO> labelVOS = labelEntityList.stream()
                .map((entity) -> {
                    LabelVO vo = new LabelVO();
                    BeanUtils.copyProperties(entity, vo);
                    vo.setIsDefault(false);
                    return vo;
                })
                .collect(Collectors.toList());
        List<LabelVO> result = new ArrayList<>();
        result.add(LabelVO.DEFAULT_LABEL);
        result.addAll(labelVOS);
        return result;
    }

    @Override
    public void rename(Long userId, String labelId, String name) {
        LabelEntity label = checkPermission(userId, labelId);
        if (label == null) {
            throw new GeneralException("当前用户没有权限修改此标签");
        }

        // 修改标签名
        label.setLabelName(name);
        label.setUpdateTime(new Date());
        baseMapper.updateById(label);
    }

    @Override
    public void save(String labelName, SysUserEntity user) {
        LabelEntity label = new LabelEntity();
        label.setLabelId(UUID.randomUUID().toString());
        label.setLabelName(labelName);
        label.setUserId(user.getUserId());
        label.setIsSystem(0);
        label.setCreateTime(new Date());
        label.setUpdateTime(new Date());

        baseMapper.insert(label);
    }

    @Override
    public void removeById(String labelId, SysUserEntity user) {
        if (checkPermission(user.getUserId(), labelId) == null) {
            throw new GeneralException("当前用户没有权限删除此标签");
        }
        // 根据ID删除标签
        baseMapper.deleteById(labelId);
        // 删除标签相关的记录
        labelFileMapper.delete(new QueryWrapper<LabelFileEntity>()
                .eq("label_id", labelId));
    }

    /**
     * 鉴定用户是否有权限修改此标签
     * @param userId 用户ID
     * @param labelId 标签ID
     * @return 是否有权限修改
     */
    private LabelEntity checkPermission(Long userId, String labelId) {
        return baseMapper.selectOne(
                new QueryWrapper<LabelEntity>()
                        .eq("label_id", labelId)
                        .eq("user_id", userId));
    }
}