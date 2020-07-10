package com.james.mall.controller;

import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.UmsAdminLoginParam;
import com.james.mall.dto.UmsAdminParam;
import com.james.mall.model.UmsAdmin;
import com.james.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: mymall
 * @description：用户后台管理
 * @create: 2020-07-10 14:26
 * @author: luoqiang
 * @version: 1.0
 */
@Controller
@Api(tags = "UmsAdminController",description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdminParam umsAdminParam, BindingResult result){
        UmsAdmin umsAdmin= adminService.register(umsAdminParam);
        if(umsAdmin==null){
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result){
        UmsAdmin umsAdmin= adminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
        if(umsAdmin==null){
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

}