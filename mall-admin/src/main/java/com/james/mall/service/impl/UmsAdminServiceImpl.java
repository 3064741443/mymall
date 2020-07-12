package com.james.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.james.mall.bo.AdminUserDetails;
import com.james.mall.dao.UmsAdminRoleRelationDao;
import com.james.mall.dto.UmsAdminParam;
import com.james.mall.dto.UpdateAdminPasswordParam;
import com.james.mall.mapper.UmsAdminMapper;
import com.james.mall.model.*;
import com.james.mall.security.util.JwtTokenUtil;
import com.james.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: mymall
 * @description：UmsAdminService实现类
 * @create: 2020-07-10 16:01
 * @author: james
 * @version: 1.0
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Override
    public UmsAdmin getAdminByUserName(String userName) {
        UmsAdmin umsAdmin = umsAdminCacheService.getAdmin(userName);
        if (umsAdmin != null) {
            return umsAdmin;
        }
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(userName);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(umsAdminExample);
        if (umsAdminList != null && umsAdminList.size() > 0) {
            umsAdmin = umsAdminList.get(0);
            umsAdminCacheService.setAdmin(umsAdmin);
            return umsAdmin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);

        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(umsAdminExample);
        if (umsAdminList.size() > 0) {
            return null;
        }
        String password = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(password);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String userName, String password) {
        String token=null;
        try {
            UserDetails userDetails = loadUserByUsername(userName);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token= jwtTokenUtil.generateToken(userDetails);
        } catch (BadCredentialsException e) {
            LOGGER.warn("登录异常:{}"+e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String ordToken) {
        return null;
    }

    @Override
    public UmsAdmin getAdminById(Long id) {
        return null;
    }

    @Override
    public List<UmsAdmin> listUmsAdmin(String keyWord, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public int updateById(Long id, UmsAdmin umsAdmin) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        return 0;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return null;
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> umsResourceList=umsAdminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(umsResourceList)){
            return  umsResourceList;
        }
        umsResourceList=umsAdminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(umsResourceList)){
            umsAdminCacheService.setResourceList(adminId,umsResourceList);
        }
        return umsResourceList;
    }

    @Override
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        return 0;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return null;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam) {
        return 0;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UmsAdmin umsAdmin = getAdminByUserName(userName);
        if (umsAdmin != null) {
            List<UmsResource> umsResourceList = getResourceList(umsAdmin.getId());
            return new AdminUserDetails(umsAdmin, umsResourceList);
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }
}
