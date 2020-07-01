package com.james.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: mymall
 * @description：查询单个产品进行修改时返回的结果
 * @create: 2020-06-09 14:16
 * @author: luoqiang
 * @version: 1.0
 */
public class PmsProductResult extends PmsProductParam {
    @ApiModelProperty("商品所选分类的父id")
    private Long cateParentId;

    public Long getCateParentId() {
        return cateParentId;
    }

    public void setCateParentId(Long cateParentId) {
        this.cateParentId = cateParentId;
    }
}
