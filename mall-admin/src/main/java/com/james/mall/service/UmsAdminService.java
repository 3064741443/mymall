package com.james.mall.service;

import com.james.mall.dto.UmsAdminParam;
import com.james.mall.dto.UpdateAdminPasswordParam;
import com.james.mall.model.UmsAdmin;
import com.james.mall.model.UmsPermission;
import com.james.mall.model.UmsResource;
import com.james.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员Service
 * Created by macro on 2020/4/26.
 */
public interface UmsAdminService {

    /**
     * description: 根据用户名获取后台管理员
     * version: 1.0
     * date 2020/7/10 14:51
     * author luoqiang
     * @param userName
     * @return com.james.mall.model.UmsAdmin
     */
    UmsAdmin getAdminByUserName(String userName);

    /**
     * description: 注册功能
     * version: 1.0
     * date 2020/7/10 14:49
     * author luoqiang
     * @param umsAdminParam
     * @return com.james.mall.model.UmsAdmin
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * description:登录功能
     * version: 1.0
     * date 2020/7/10 14:49
     * author luoqiang
     * @param userName 用户名
     * @param password 密码
     * @return com.james.mall.model.UmsAdmin
     */
    UmsAdmin login(String userName,String password);

    /**
     * description: 刷新token的功能
     * version: 1.0
     * date 2020/7/10 14:54
     * author luoqiang
     * @param ordToken 旧的token
     * @return java.lang.String
     */
    String refreshToken(String ordToken);

    /**
     * description: 根据用户ID获取用户
     * version: 1.0
     * date 2020/7/10 14:57
     * author luoqiang
     * @param id
     * @return com.james.mall.model.UmsAdmin
     */
    UmsAdmin getAdminById(Long id);

    /**
     * description: 根据用户名或者昵称分页查询用户
     * version: 1.0 
     * date 2020/7/10 15:04
     * author luoqiang
     * @param keyWord
     * @param pageNum
     * @param pageSize 
     * @return java.util.List<com.james.mall.model.UmsAdmin> 
     */
    List<UmsAdmin> listUmsAdmin(String keyWord,Integer pageNum, Integer pageSize);

    /**
     * description: 修改指定用户信息
     * version: 1.0 
     * date 2020/7/10 15:18
     * author luoqiang
     * @param id
     * @param umsAdmin 
     * @return int 
     */
    int updateById(Long id,UmsAdmin umsAdmin);

    /**
     * description: 删除指定用户
     * version: 1.0 
     * date 2020/7/10 15:18
     * author luoqiang
     * @param id 
     * @return int 
     */
    int deleteById(Long id);

    /**
     * description: 修改用户角色关系
     * version: 1.0 
     * date 2020/7/10 15:19
     * author luoqiang
     * @param adminId
     * @param roleIds 
     * @return int 
     */
    @Transactional
    int updateRole(Long adminId,List<Long> roleIds);

    /**
     * description: 获取用户角色列表
     * version: 1.0 
     * date 2020/7/10 15:20
     * author luoqiang
     * @param adminId 
     * @return java.util.List<com.james.mall.model.UmsRole> 
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * description: 获取指定用户的可访问资源
     * version: 1.0 
     * date 2020/7/10 15:21
     * author luoqiang
     * @param adminId 
     * @return java.util.List<com.james.mall.model.UmsResource> 
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * description:修改用户的+-权限
     * version: 1.0 
     * date 2020/7/10 15:22
     * author luoqiang
     * @param adminId
     * @param permissionIds 
     * @return int 
     */
    @Transactional
    int updatePermission(Long adminId, List<Long> permissionIds);

    /**
     * description: 获取用户所有权限(包括角色权限和+-权限)
     * version: 1.0
     * date 2020/7/10 15:24
     * author luoqiang
     * @param adminId
     * @return java.util.List<com.james.mall.model.UmsPermission>
     */
    List<UmsPermission> getPermissionList(Long adminId);

    /**
     * description: 修改用户密码
     * version: 1.0
     * date 2020/7/10 15:28
     * author luoqiang
     * @param updateAdminPasswordParam
     * @return int
     */
    int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam);

    /**
     * description: 获取用户信息
     * version: 1.0
     * date 2020/7/10 15:29
     * author luoqiang
     * @param userName
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserDetails getUserByUserName(String userName);









}
