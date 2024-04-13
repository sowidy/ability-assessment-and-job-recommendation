package com.jobRecomment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Student;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.EnterpriseQuery;
import com.jobRecomment.mapper.EnterpriseMapper;
import com.jobRecomment.service.IEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.utils.Md5Util;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-09
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

    @Override
    public Enterprise getByPhone(String phone) {
        return lambdaQuery().eq(Enterprise::getPhone, phone).one();
    }

    @Override
    public void addUser(String phone,String name, String password) {
        baseMapper.add(phone,name, Md5Util.getMD5String(password));
    }

    @Override
    public List<Enterprise> getEnterpriseList() {
        return list();
    }

    @Override
    public List<Enterprise> getAllAuthList(Integer size) {
//        return lambdaQuery().eq(Enterprise::getEnterpriseAuth,0).list();
        QueryWrapper<Enterprise> queryWrapper = Wrappers.query();
        queryWrapper.isNotNull("title");
        queryWrapper.eq("enterprise_auth", 0);
        queryWrapper.last("LIMIT " + size);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageDTO<Enterprise> conditionQuery(EnterpriseQuery query) {
        Page<Enterprise> page = query.toMpPage();
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("title");
        if (StringUtils.isNotBlank(query.getName())) {
            wrapper.like("name", query.getName());
        }
        if (StringUtils.isNotBlank(query.getTitle())) {
            wrapper.like("title", query.getTitle());
        }
        if (query.getSalaryMin() != null) {
            wrapper.ge("salary_min", query.getSalaryMin());
        }
        if (query.getSalaryMax() != null) {
            wrapper.le("salary_max", query.getSalaryMax());
        }
        if (StringUtils.isNotBlank(query.getAddress())) {
            wrapper.like("address", query.getAddress());
        }
        if (StringUtils.isNotBlank(query.getType())) {
            wrapper.like("type", query.getType());
        }
        if (StringUtils.isNotBlank(query.getEnterpriseAuth())) {
            wrapper.like("enterprise_auth", query.getEnterpriseAuth());
        }
        Page<Enterprise> p = baseMapper.selectPage(page, wrapper);
        return PageDTO.of(p, Enterprise.class);
    }

    @Override
    public PageDTO<Enterprise> getTitleByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();
        wrapper.in("id", idList);
        List<Enterprise> enterpriseList = baseMapper.selectList(wrapper);
        Page<Enterprise> page = new Page<>(1, enterpriseList.size());
        page.setRecords(enterpriseList);
        page.setTotal(enterpriseList.size());
        return PageDTO.of(page, Enterprise.class);
    }
    @Override
    public Enterprise findById(String id) {
        return getById(id);
    }

    @Override
    public void updateAvatar(String avatar) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.updateAvatar(id, avatar);
    }

    @Override
    public void updateEnterprise(Enterprise enterprise) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        enterprise.setId(id);
        enterprise.setUpdateTime(LocalDateTime.now());
        updateById(enterprise);
    }

    /**
     * 对企业上传的信息进行保存，由未认证到待认证
     *
     * @param material
     */
    @Override
    public void addOrUploadMaterial(String material) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Enterprise enterprise = new Enterprise();
        enterprise.setMaterials(material);
        enterprise.setEnterpriseAuth("1");
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<Enterprise>().eq("id", id);
        baseMapper.update(enterprise, wrapper);
    }

    @Override
    public List<Enterprise> queryByName(String title) {
//        Enterprise enterprise = new Enterprise();
        return lambdaQuery().
                like(Enterprise::getTitle, title).
                list();
    }

    @Override
    public void uploadInfoById(Enterprise enterprise) {
        boolean update = updateById(enterprise);
    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {
        ids = ids.replaceAll("\\[|\\]", "").trim();
        String[] idArray = ids.split(",");
        baseMapper.deleteBatchIds(Arrays.asList(idArray));
    }

    @Override
    public Integer getTotalCount() {
        return baseMapper.getTotalCount();
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Enterprise u = new Enterprise();
        u.setPassword(Md5Util.getMD5String(newPwd));
        u.setId(id);
        u.setUpdateTime(LocalDateTime.now());
        updateById(u);
    }

    @Override
    public void updatePhone(String phone) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        baseMapper.updatePhone(id,phone);
    }

    @Override
    public List<Enterprise> getByRandomSize(Integer size) {
        QueryWrapper<Enterprise> queryWrapper = Wrappers.query();
        queryWrapper.last("LIMIT " + size);
        return baseMapper.selectList(queryWrapper);
    }



}
