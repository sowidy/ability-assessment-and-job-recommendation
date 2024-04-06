package com.jobRecomment.controller;
import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.domain.bean.Student;
import com.jobRecomment.domain.dto.PageDTO;
import com.jobRecomment.domain.query.RemarkQuery;
import com.jobRecomment.domain.vo.RemarkVO;
import com.jobRecomment.service.IStudentService;
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
 * 学生信息表 前端控制器
 * </p>
 *
 * @author HUANG
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/student")
@Api(tags = "用户接口表")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public Result userLogin(@Param("phone") String phone, @Param("password") String password){
        Student e = studentService.getByPhone(phone);
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

    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public Result userRegister(@Param("phone") String phone,@Param("name")String name, @Param("password") String password){
        Student user = studentService.getByPhone(phone);
        if(user != null){
            return Result.error("该用户已存在,请登录");
        }
        studentService.addUser(phone,name,password);
        return Result.success();
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息接口")
    public Result<Student> getUserInfo() {
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone = (String) map.get("phone");
        Student user = studentService.getByPhone(phone);
        return Result.success(user);
    }

    /**
     * 拦截器先获取到用户的id,将用户id作为条件，结合mybatisPlus更新功能
     * @param user 更新对象信息 id没有
     * @return 成功失败信息
     */
    @PostMapping("/update")
    @ApiOperation("更新用户信息接口")
    public Result updateUser(@RequestBody Student user){
        if(user.getId() == null) studentService.updateUser(user);
        else studentService.updateUserById(user);
        return Result.success();
    }

    @ApiOperation("用户更新头像")
    @PatchMapping("/upload/avatar")
    public Result uploadStudentAvatar(@RequestParam String avatar){
        studentService.updateAvatar(avatar);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取上传简历用户列表接口")
    public Result<List<Student>> getUserList(){
        List<Student> list = studentService.getUserList();
        return  Result.success(list);
    }

    @PatchMapping("/updatepwd")
    @ApiOperation("更新用户密码接口")
    public Result updateUserPassword(@RequestBody Map<String, String> params){
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
        Student loginUser = studentService.getByPhone(phone);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一样");
        }

        //2.调用service完成密码更新
        studentService.updatePassword(newPwd);
        return  Result.success();
    }

    @ApiOperation("修改用户手机号码")
    @PatchMapping("/update/phone")
    public Result updatePhone(@RequestParam("phone") String phone){
        Student student = studentService.getByPhone(phone);
        if(student != null){
            return Result.error("该号码已被使用");
        }
        studentService.updatePhone(phone);
        return  Result.success();
    }

    @ApiOperation("通过ids删除学生")
    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(String ids){
        studentService.deleteByIds(ids);
        return Result.success();
    }

    @ApiOperation("后台-获取学生总人数接口")
    @GetMapping("/count")
    public Result getTotalCount(){
        Integer count = studentService.getTotalCount();
        return Result.success(count);
    }

    @ApiOperation("按3条数获取用户反馈")
    @GetMapping("/remarks")
    public Result getRemarkList(){
        List<RemarkVO> vo = studentService.getRemarkList();
        return Result.success(vo);
    }

    @ApiOperation("后台-获取用户反馈总条数")
    @GetMapping("/remark/count")
    public Result getRemarkCount(){
        Integer count = studentService.getRemarkCount();
        return Result.success(count);
    }

    @ApiOperation("后台-获取用户所有反馈信息")
    @GetMapping("/remark/list")
    public Result<List<RemarkVO>> getAllRemark(){
        List<RemarkVO> vo = studentService.getAllRemark();
        return Result.success(vo);
    }
    @ApiOperation("后台-分页查询用户反馈信息")
    @GetMapping("/remark/page")
    public Result<PageDTO<RemarkVO>> getRemarkVoByPage(RemarkQuery query){
        PageDTO<RemarkVO> list = studentService.QueryRemark(query);
        return Result.success(list);
    }
}
