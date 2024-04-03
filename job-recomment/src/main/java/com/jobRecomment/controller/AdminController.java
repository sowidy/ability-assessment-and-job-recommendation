package com.jobRecomment.controller;


import com.jobRecomment.domain.bean.Admin;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.query.AdminQuery;
import com.jobRecomment.service.IAdminService;
import com.jobRecomment.utils.JwtUtil;
import com.jobRecomment.utils.Md5Util;
import com.jobRecomment.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员信息表 前端控制器
 * </p>
 *
 * @author HUANG
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员接口表")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("管理员登录接口")
    @PostMapping("/login")
    public Result adminLogin(@RequestBody AdminQuery query){
        System.out.println(query+"@@@@@@@@@@@@");
        Admin admin = adminService.getByPhone(query.getPhone());
        if(admin == null){
            return Result.error("不存在该用户");
        }
        if(!admin.getPassword().equals(Md5Util.getMD5String(query.getPassword()))){
            return Result.error("密码错误");
        }
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",admin.getId());
        claim.put("phone",admin.getPhone());
        String token = JwtUtil.genToken(claim);
        return Result.success(token);
    }

    @ApiOperation("添加管理员接口")
    @PostMapping("/add")
    public Result addAdmin(@Param("phone") String phone, @Param("password") String password){
        Admin admin = adminService.getByPhone(phone);
        if(admin != null){
            return Result.error("该管理员已存在");
        }
        adminService.addAdmin(phone,password);
        return Result.success();
    }

    @ApiOperation("获取管理员个人信息接口")
    @GetMapping("/info")
    public Result getAdminInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone = (String) map.get("phone");
        return Result.success(adminService.getByPhone(phone));
    }
}
