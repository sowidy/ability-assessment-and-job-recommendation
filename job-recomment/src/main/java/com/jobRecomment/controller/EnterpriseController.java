package com.jobRecomment.controller;
import com.jobRecomment.domain.bean.Enterprise;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Student;
import com.jobRecomment.service.IEnterpriseService;
import com.jobRecomment.utils.JwtUtil;
import com.jobRecomment.utils.Md5Util;
import com.jobRecomment.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 企业信息表 前端控制器
 * </p>
 *
 * @author HUANG
 * @since 2024-03-09
 */
@RestController
@RequestMapping("/enterprise")
@Api(tags = "企业接口表")
public class EnterpriseController {
    @Autowired
    private IEnterpriseService enterpriseService;

    @ApiOperation("企业登录接口")
    @PostMapping("/login")
    public Result enterpriseLogin(@Param("phone") String phone, @Param("password") String password){
        Enterprise e = enterpriseService.getByPhone(phone);
        if(e == null){
            return Result.error("该用户不存在");
        }
        if(!Objects.equals(e.getPassword(), Md5Util.getMD5String(password))){
            return Result.error("密码错误");
        }
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",e.getId());
        claim.put("phone",e.getPhone());
        String token = JwtUtil.genToken(claim);
        return Result.success(token);
    }

    @ApiOperation("企业注册接口")
    @PostMapping("/register")
    public Result enterpriseRegister(@Param("phone") String phone,@Param("name")String name, @Param("password") String password){
        Enterprise enterprise = enterpriseService.getByPhone(phone);
        if(enterprise != null){
            return Result.error("该用户已存在,请登录");
        }
        enterpriseService.addUser(phone,name,password);
        return Result.success();
    }

    @ApiOperation("获取职位列表")
    @GetMapping("/list")
    public Result<List<Enterprise>> getEnterpriseList(){
        return Result.success(enterpriseService.getEnterpriseList());
    }

    @ApiOperation("获取企业信息")
    @GetMapping("/info")
    public Result<Enterprise> getEnterpriseInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone = (String) map.get("phone");
        Enterprise enterprise = enterpriseService.getByPhone(phone);
        return Result.success(enterprise);
    }

    @ApiOperation("修改企业密码")
    @PatchMapping("/updatepwd")
    public Result updateEnterprisePasswd(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        //调用userService根据用户名拿到原密码,再和old_pwd比对
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone = (String) map.get("phone");
        Enterprise loginUser = enterpriseService.getByPhone(phone);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一样");
        }

        //2.调用service完成密码更新
        enterpriseService.updatePassword(newPwd);
        return  Result.success();
    }

    @ApiOperation("修改用户手机号码")
    @PatchMapping("/update/phone")
    public Result updatePhone(@RequestParam("phone") String phone){
        Enterprise enterprise = enterpriseService.getByPhone(phone);
        if(enterprise != null){
            return Result.error("该号码已被使用");
        }
        enterpriseService.updatePhone(phone);
        return  Result.success();
    }

    @ApiOperation("更新企业头像")
    @PatchMapping("/upload/avatar")
    public Result uploadEnterpriseAvatar(@RequestParam String avatar){
        enterpriseService.updateAvatar(avatar);
        return Result.success();
    }

    @ApiOperation("更新企业信息")
    @PostMapping("/update")
    public Result uploadEnterpriseInfo(@RequestBody Enterprise enterprise){
        enterpriseService.updateEnterprise(enterprise);
        return Result.success();
    }

    @ApiOperation("上传或更新认证信息")
    @PostMapping("/upload/material")
    public Result addOrUploadMaterial(String material){
//        System.out.println(material);
        enterpriseService.addOrUploadMaterial(material);
        return Result.success();
    }

    @ApiOperation("通过id修改某个企业信息")
    @PostMapping("/uploadById")
    public Result uploadInfoById(@RequestBody Enterprise enterprise){
        enterpriseService.uploadInfoById(enterprise);
        return Result.success();
    }

    @ApiOperation("通过ids删除企业")
    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(String ids){
        enterpriseService.deleteByIds(ids);
        return Result.success();
    }
    @ApiOperation("后台-获取企业总个数")
    @GetMapping("/count")
    public Result getTotalCount(){
        Integer count = enterpriseService.getTotalCount();
        return  Result.success(count);
    }
}
