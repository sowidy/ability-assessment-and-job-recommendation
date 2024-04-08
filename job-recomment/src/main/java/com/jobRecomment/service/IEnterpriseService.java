package com.jobRecomment.service;

import com.jobRecomment.domain.bean.Enterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.EnterpriseQuery;

import java.util.List;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author HUANG
 * @since 2024-03-09
 */
public interface IEnterpriseService extends IService<Enterprise> {
    Enterprise getByPhone(String phone);

    void addUser(String phone,String name, String password);

    List<Enterprise> getEnterpriseList();

    List<Enterprise> getAllAuthList(Integer size);

    PageDTO<Enterprise> conditionQuery(EnterpriseQuery query);

    Enterprise findById(String id);

    void updateAvatar(String avatar);

    void updateEnterprise(Enterprise enterprise);

    void addOrUploadMaterial(String material);

    List<Enterprise> queryByName(String name);

    void uploadInfoById(Enterprise enterprise);

    void deleteByIds(String ids);

    Integer getTotalCount();

    void updatePassword(String newPwd);

    void updatePhone(String phone);

    List<Enterprise> getByRandomSize(Integer size);
}
