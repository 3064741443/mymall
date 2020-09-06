package com.james.mall.controller;

import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.UmsAdminLoginParam;
import com.james.mall.dto.UmsAdminParam;
import com.james.mall.model.UmsAdmin;
import com.james.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mymall
 * @description：用户后台管理
 * @create: 2020-07-10 14:26
 * @author: James
 * @version: 1.0
 */
@Controller
@Api(tags = "UmsAdminController",description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
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
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result){
        String token= adminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
        if(token==null){
            CommonResult.validateFailed("用户名或者密码错误");
        }
        Map<String,Object> tokenMap=new HashMap<>(2);
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @ResponseBody
    @GetMapping
    public CommonResult refreshToken(HttpServletRequest request){
        String token=request.getHeader(tokenHeader);
        String refreshToken=adminService.refreshToken(token);
        if(StringUtils.isEmpty(refreshToken)){
            CommonResult.failed("token已经过期!");
        }
        Map<String, String> tokenMap=new HashMap<>();
        tokenMap.put("refreshToken",refreshToken);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @ResponseBody
    @GetMapping
    public CommonResult refreshToken(HttpServletRequest request){
        String token=request.getHeader(tokenHeader);
        String refreshToken=adminService.refreshToken(token);
        if(StringUtils.isEmpty(refreshToken)){
            CommonResult.failed("token已经过期!");
        }
        Map<String, String> tokenMap=new HashMap<>();
        tokenMap.put("refreshToken",refreshToken);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }
}
