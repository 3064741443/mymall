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
   List<PmsProduct> getProductList(PmsProductQueryParam productQueryParam,Integer pageNum,Integer pageSize);
}


