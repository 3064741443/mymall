package com.james.mall.controller;

import com.james.mall.common.api.CommonPage;
import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.model.PmsProduct;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: mymall
 * @description: 商品管理控制层
 * @author: james
 * @create: 2020-01-13 01:02
 */
@RestController
public class PmsProductController {
    public CommonResult<CommonPage<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productList));
    }

}


