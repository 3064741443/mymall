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
    public List<PmsProduct> listProduct(PmsProductQueryParam productQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        return productMapper.selectByExample(productExample);
    }


    @Override
    public List<PmsProduct> listProduct(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct product = new PmsProduct();
        product.setPublishStatus(publishStatus);
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(product, productExample);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(recommendStatus);
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(product, productExample);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(deleteStatus);
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(product, productExample);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct product=new PmsProduct();
        product.setNewStatus(newStatus);
        PmsProductExample productExample=new PmsProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(product, productExample);
    }


}


