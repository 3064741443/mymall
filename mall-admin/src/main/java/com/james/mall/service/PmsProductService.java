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
     * description: 分页商品查询
     * version: 1.0
     * date 2020/1/17 下午5:13
     * author James
     *
     * @param productQueryParam 分页查询条件
     * @param pageNum           当前第几页
     * @param pageSize          每页大小
     * @return java.util.List<com.james.mall.model.PmsProduct>
     */
    List<PmsProduct> listProduct(PmsProductQueryParam productQueryParam, Integer pageNum, Integer pageSize);

    /**
     * description: 根据商品名称或者货号模糊查询
     * version: 1.0
     * date 2020/1/17 下午5:14
     * author James
     *
     * @param keyword 关键字
     * @return java.util.List<com.james.mall.model.PmsProduct>
     */
    List<PmsProduct> listProduct(String keyword);

    /**
     * description: 商品批量上下架
     * version: 1.0
     * date 2020/1/17 下午5:14
     * author James
     *
     * @param ids           商品id集合
     * @param publishStatus 是否上架
     * @return int
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * description: 商品批量推荐
     * version: 1.0
     * date 2020/1/17 下午5:15
     * author James
     *
     * @param ids             商品id集合
     * @param recommendStatus 推荐状态
     * @return int
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);


    /**
     * description: 批量修改商品删除状态
     * version: 1.0
     * date 2020/1/17 下午7:47
     * author James
     *
     * @param ids          商品ID集合
     * @param deleteStatus 商品删除状态
     * @return int
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * description:  批量修改为新品状态
     * version: 1.0
     * date 2020/1/17 下午8:18
     * author James
     *
     * @param ids       商品ID集合
     * @param newStatus 新品状态
     * @return int
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);
}


