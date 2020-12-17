package com.james.mall.portal.service;

import com.james.mall.model.OmsCartItem;
import com.james.mall.portal.domain.CartPromotionItem;

import java.util.List;

/**
 * 促销管理Service
 * Created by james on 2018/8/27.
 */
public interface OmsPromotionService {
    /**
     * 计算购物车中的促销活动信息
     * @param cartItemList 购物车
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
