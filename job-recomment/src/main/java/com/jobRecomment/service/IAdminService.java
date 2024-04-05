package com.jobRecomment.service;

import com.jobRecomment.domain.bean.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员信息表 服务类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-22
 */
public interface IAdminService extends IService<Admin> {

    Admin getByPhone(String phone);

    void addAdmin(String phone, String password);

    void updateAdminById(Admin admin);
}
