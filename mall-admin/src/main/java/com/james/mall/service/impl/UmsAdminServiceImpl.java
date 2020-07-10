package com.james.mall.service.impl;

import com.james.mall.dto.UmsAdminParam;
import com.james.mall.dto.UpdateAdminPasswordParam;
import com.james.mall.mapper.UmsAdminMapper;
import com.james.mall.model.*;
import com.james.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: mymall
 * @description：UmsAdminService实现类
 * @create: 2020-07-10 16:01
 * @author: luoqiang
 * @version: 1.0
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UmsAdmin getAdminByUserName(String userName) {
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin=new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);

        UmsAdminExample umsAdminExample=new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList=adminMapper.selectByExample(umsAdminExample);
        if(umsAdminList.size()>0){
            return null;
        }
        String password=passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(password);
        adminMapper.insert(umsAdmin);
        return  umsAdmin;
    }

    @Override
    public UmsAdmin login(String userName, String password) {
        
        return null;
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
        return null;
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
    public UserDetails getUserByUserName(String userName) {
        return null;
    }
}
