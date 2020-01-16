package com.james.mall.controller;

import com.james.mall.common.api.CommonPage;
import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.model.PmsProduct;
import com.james.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mymall
 * @description: 商品管理控制层
 * @author: james
 * @create: 2020-01-13 01:02
 */
@Api(tags = "PmsProductController", description = "商品管理")
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService productService;

    @ApiOperation("查询商品")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> getProductList(PmsProductQueryParam productQueryParam,
                                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProduct> productList = productService.getProductList(productQueryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }

    @ApiOperation("根据商品名称或者货号模糊查询")
    @GetMapping("/sampleList")
    public CommonResult getProductList(String keyword) {
        List<PmsProduct> productList = productService.getProductList(keyword);
        return CommonResult.success(productList);
    }

    @ApiOperation("商品批量上下架")
    @PutMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids, @RequestParam("publishStatus") Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if(count>0) {
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品批量推荐")
    @PutMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam("ids")List<Long> ids,@RequestParam("recommendStatus")Integer recommendStatus){
        int count=productService.updateRecommendStatus(ids, recommendStatus);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }


}


