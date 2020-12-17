package com.james.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.james.mall.bo.AdminUserDetails;
import com.james.mall.common.exception.Asserts;
import com.james.mall.dao.UmsAdminRoleRelationDao;
import com.james.mall.dto.UmsAdminParam;
import com.james.mall.dto.UpdateAdminPasswordParam;
import com.james.mall.mapper.UmsAdminLoginLogMapper;
import com.james.mall.mapper.UmsAdminMapper;
import com.james.mall.mapper.UmsAdminRoleRelationMapper;
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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

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
        //对密码进行加密操作
        String password = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(password);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String userName, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(userName);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            /*if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }*/
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(userName);
            insertLoginLog(userName);
        } catch (BadCredentialsException e) {
            LOGGER.warn("登录异常:{}" + e.getMessage());
        }
        return token;
    }

    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUserName(username);
        if (admin == null) return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        umsAdminLoginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        umsAdminMapper.updateByExampleSelective(record, umsAdminExample);
    }

    @Override
    public String refreshToken(String ordToken) {
        return jwtTokenUtil.refreshHeadToken(ordToken);
    }

    @Override
    public UmsAdmin getAdminById(Long id) {
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsAdmin> listUmsAdmin(String keyWord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyWord)) {
            criteria.andUsernameLike("%" + keyWord + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyWord + "%"));
        }
        return umsAdminMapper.selectByExample(example);
    }

    @Override
    public int updateById(Long id, UmsAdmin umsAdmin) {
        umsAdmin.setId(id);
        UmsAdmin rawAdmin = umsAdminMapper.selectByPrimaryKey(id);
        if (rawAdmin.getPassword().equals(umsAdmin.getPassword())) {
            umsAdmin.setPassword(null);
        } else {
            if (StringUtils.isEmpty(umsAdmin.getPassword())) {
                umsAdmin.setPassword(null);
            } else {
                umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
            }
        }
        int count = umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
        umsAdminCacheService.delAdmin(id);
        return count;
    }

    @Override
    public int deleteById(Long id) {
        return umsAdminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //删除原来用户和角色的关系
        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
        example.createCriteria().andAdminIdEqualTo(adminId);
        umsAdminRoleRelationMapper.deleteByExample(example);
        //建立新的用户和角色关系
        UmsAdminRoleRelation umsAdminRoleRelation=null;
        List<UmsAdminRoleRelation> adminRoleRelations=new ArrayList<>();
        if(!CollectionUtil.isEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                umsAdminRoleRelation = new UmsAdminRoleRelation();
                umsAdminRoleRelation.setAdminId(adminId);
                umsAdminRoleRelation.setRoleId(roleId);
                adminRoleRelations.add(umsAdminRoleRelation);
            }
            umsAdminRoleRelationDao.insertList(adminRoleRelations);
        }
        umsAdminCacheService.delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsAdminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> umsResourceList = umsAdminCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(umsResourceList)) {
            return umsResourceList;
        }
        umsResourceList = umsAdminRoleRelationDao.getResourceList(adminId);
        if (CollUtil.isNotEmpty(umsResourceList)) {
            umsAdminCacheService.setResourceList(adminId, umsResourceList);
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
        if (StrUtil.isEmpty(updateAdminPasswordParam.getUsername())
                || StrUtil.isEmpty(updateAdminPasswordParam.getOldPassword())
                || StrUtil.isEmpty(updateAdminPasswordParam.getNewPassword())) {
            return -1;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(updateAdminPasswordParam.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (CollUtil.isEmpty(umsAdminList)) {
            return -2;
        }
        UmsAdmin umsAdmin = umsAdminList.get(0);
        if (!passwordEncoder.matches(updateAdminPasswordParam.getOldPassword(), umsAdmin.getPassword())) {
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(updateAdminPasswordParam.getNewPassword()));
        umsAdminMapper.updateByPrimaryKey(umsAdmin);
        umsAdminCacheService.delAdmin(umsAdmin.getId());
        return 1;
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
