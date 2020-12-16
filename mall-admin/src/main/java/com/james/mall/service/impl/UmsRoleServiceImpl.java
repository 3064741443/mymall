package com.james.mall.service.impl;

import com.james.mall.dao.UmsRoleDao;
import com.james.mall.model.UmsMenu;
import com.james.mall.model.UmsResource;
import com.james.mall.model.UmsRole;
import com.james.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: mymall
 * @description:
 * @date 2020/12/16 14:59
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao roleDao;

    @Override
    public int create(UmsRole role) {
        return 0;
    }

    @Override
    public int update(Long id, UmsRole role) {
        return 0;
    }

    @Override
    public int delete(List<Long> ids) {
        return 0;
    }

    @Override
    public List<UmsRole> list() {
        return null;
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        List<UmsMenu> umsMenuList=roleDao.getMenuList(adminId);
        return umsMenuList;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return null;
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return null;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        return 0;
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        return 0;
    }
}
