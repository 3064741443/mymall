package com.james.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.james.mall.dao.UmsRoleDao;
import com.james.mall.mapper.UmsRoleMapper;
import com.james.mall.mapper.UmsRoleMenuRelationMapper;
import com.james.mall.mapper.UmsRoleResourceRelationMapper;
import com.james.mall.model.*;
import com.james.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private UmsRoleDao roleDao;

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return umsRoleMapper.insert(role);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return umsRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample umsRoleExample=new UmsRoleExample();
        umsRoleExample.createCriteria().andIdIn(ids);
        umsRoleMapper.deleteByExample(umsRoleExample);
        umsAdminCacheService.delResourceListByRoleIds(ids);
        return 0;
    }

    @Override
    public List<UmsRole> list() {
        return umsRoleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsRoleExample example=new UmsRoleExample();
        if(!StringUtils.isEmpty(keyword)){
            example.createCriteria().andNameLike("%"+keyword+"%");
        }
        return umsRoleMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        List<UmsMenu> umsMenuList=roleDao.getMenuList(adminId);
        return umsMenuList;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        List<UmsMenu> umsMenuList=roleDao.getMenuListByRoleId(roleId);
        return umsMenuList;
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        List<UmsResource> umsResourceList=roleDao.getResourceListByRoleId(roleId);
        return umsResourceList;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        UmsRoleMenuRelationExample example=new UmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleMenuRelationMapper.deleteByExample(example);
        UmsRoleMenuRelation roleMenuRelation=null;
        if(!StringUtils.isEmpty(menuIds)) {
            for (Long menuId : menuIds) {
                roleMenuRelation = new UmsRoleMenuRelation();
                roleMenuRelation.setRoleId(roleId);
                roleMenuRelation.setMenuId(menuId);
                roleMenuRelationMapper.insert(roleMenuRelation);
            }
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        UmsRoleResourceRelationExample example=new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);
        UmsRoleResourceRelation roleResourceRelation=null;
        if(!StringUtils.isEmpty(resourceIds)){
            for(Long resourceId:resourceIds){
               roleResourceRelation=new UmsRoleResourceRelation();
                roleResourceRelation.setRoleId(roleId);
                roleResourceRelation.setResourceId(resourceId);
                roleResourceRelationMapper.insert(roleResourceRelation);
            }
        }
        umsAdminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
