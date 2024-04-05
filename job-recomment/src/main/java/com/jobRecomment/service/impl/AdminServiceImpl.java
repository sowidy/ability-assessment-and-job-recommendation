package com.jobRecomment.service.impl;

import com.jobRecomment.domain.bean.Admin;
import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.mapper.AdminMapper;
import com.jobRecomment.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.utils.Md5Util;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Admin getByPhone(String phone) {
        return lambdaQuery().eq(Admin::getPhone, phone).one();
    }

    @Override
    public void addAdmin(String phone, String password) {
        baseMapper.add(phone, Md5Util.getMD5String(password));
    }

    @Override
    public void updateAdminById(Admin admin) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        admin.setId(id);
        updateById(admin);
    }
}
