package com.nydia.demo.redission.demo;

import com.nydia.demo.redission.lockAspect.DistributedLock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/13 22:46
 * @Description: RedissionDemo
 */
@Slf4j
@RestController("demo")
public class RedissionDemoController {

    @Resource
    private IProductSkuSupplierMeasureService productSkuSupplierMeasureService;

    // @ApiOperation("编辑SKU供应商供货信息")
    @PostMapping("/editSupplierInfo")
    //@DistributedLock(key = "#dto.sku + '-' + #dto.skuId", lockTime = 10L, keyPrefix = "sku-")
    @DistributedLock(key = "#dto.sku", lockTime = 10L, keyPrefix = "sku-")
    public R<Boolean> editSupplierInfo(@RequestBody @Validated ProductSkuSupplierInfoDTO dto) {
        return R.ok(productSkuSupplierMeasureService.editSupplierInfo(dto));
    }

}
