package com.james.mall.controller;

import com.james.mall.common.api.CommonPage;
import com.james.mall.common.api.CommonResult;
import com.james.mall.dto.PmsProductQueryParam;
import com.james.mall.dto.PmsProductResult;
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


/*    @ApiOperation("创建商品")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    public CommonResult getUpdateInfo(@PathVariable("id")String id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return CommonResult.success(productResult);

    }*/

    @ApiOperation("根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    public CommonResult getUpdateInfo(@PathVariable("id")String id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return CommonResult.success(productResult);

    }

    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProduct>> listProduct(PmsProductQueryParam productQueryParam,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProduct> productList = productService.listProduct(productQueryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }

    @ApiOperation("根据商品名称或者货号模糊查询")
    @RequestMapping(value = "/sampleList", method = RequestMethod.GET)
    public CommonResult listProduct(String keyword) {
        List<PmsProduct> productList = productService.listProduct(keyword);
        return CommonResult.success(productList);
    }

    @ApiOperation("商品批量上下架")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.PUT)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids, @RequestParam("publishStatus") Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品批量推荐")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.PUT)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量修改商品删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.PUT)
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids, @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = productService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量修改商品新品状态")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.PUT)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids, @RequestParam("newStatus") Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}


