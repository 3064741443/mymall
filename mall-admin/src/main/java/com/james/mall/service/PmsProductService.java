package com.james.mall.service;

import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.model.PmsProduct;

import java.util.List;

/**
 * @program: mymall
 * @description: 商品管理Service
 * @author: james
 * @create: 2020-01-13 01:14
 */
public interface PmsProductService {
   /**
    * 分页商品查询
    */
   List<PmsProduct> getProductList(PmsProductQueryParam productQueryParam,Integer pageNum,Integer pageSize);

   /**
    * 根据商品名称或者货号模糊查询
    */
   List<PmsProduct> getProductList(String keyword);

   /**
    * 商品批量上下架
    */
   int updatePublishStatus(List<Long> ids,Integer publishStatus);

   /**
    * 商品批量推荐
    */
   int updateRecommendStatus(List<Long> ids,Integer recommendStatus);
}


