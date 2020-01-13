package com.james.mall.controller;

import com.james.mall.common.api.CommonPage;
import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.model.PmsProduct;
import com.james.mall.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService productService;

    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> getProductList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProduct> productList = productService.getProductList(productQueryParam, pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }

}


