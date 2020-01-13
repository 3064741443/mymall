package com.james.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.mapper.PmsProductMapper;
import com.james.mall.model.PmsProduct;
import com.james.mall.model.PmsProductExample;
import com.james.mall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: mymall
 * @description: 商品管理Service实现类
 * @author: james
 * @create: 2020-01-13 01:17
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);
    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsProduct> getProductList(PmsProductQueryParam productQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if(productQueryParam.getProductCategoryId()!=null){
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        if(productQueryParam.getBrandId()!=null){
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if(productQueryParam.getPublishStatus()!=null){
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if(productQueryParam.getVerifyStatus()!=null){
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        return productMapper.selectByExample(productExample);
    }
}


