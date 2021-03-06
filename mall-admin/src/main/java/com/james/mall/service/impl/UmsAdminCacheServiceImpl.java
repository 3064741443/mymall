package com.james.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.james.mall.common.service.RedisService;
import com.james.mall.mapper.UmsAdminRoleRelationMapper;
import com.james.mall.model.UmsAdmin;
import com.james.mall.model.UmsAdminRoleRelation;
import com.james.mall.model.UmsAdminRoleRelationExample;
import com.james.mall.model.UmsResource;
import com.james.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mymall
 * @description: UmsAdminCacheService实现类
 * @create: 2020-07-11 00:27
 * @author: James
 * @version: 1.0
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin umsAdmin = adminService.getAdminById(adminId);
        if(umsAdmin!=null){
            String key=REDIS_DATABASE+":"+REDIS_KEY_ADMIN+":"+umsAdmin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key=REDIS_DATABASE+":"+REDIS_KEY_RESOURCE_LIST+":"+adminId;
        redisService.del(key);

    }

    @Override
    public void delResourceListByRole(Long roleId) {
        UmsAdminRoleRelationExample example=new UmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<UmsAdminRoleRelation> adminRoleRelations= adminRoleRelationMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(adminRoleRelations)){
            String keyPrefix=REDIS_DATABASE+":"+REDIS_KEY_RESOURCE_LIST+":";
            List<String> keys=adminRoleRelations.stream().map(ralation -> keyPrefix+ralation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        UmsAdminRoleRelationExample example=new UmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdIn(roleIds);
        List<UmsAdminRoleRelation> adminRoleRelations= adminRoleRelationMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(adminRoleRelations)){
            String keyPrefix=REDIS_DATABASE+":"+REDIS_KEY_RESOURCE_LIST+":";
            List<String> keys=adminRoleRelations.stream().map(ralation -> keyPrefix+ralation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {

    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }
}
