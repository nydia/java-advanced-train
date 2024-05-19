package com.nydia.demo.redission;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/13 22:46
 * @Description: RedissionDemo
 */
@Slf4j
@RestController("demo")
public class RedissionDemoController {

    // @ApiOperation("编辑SKU供应商供货信息")
    @PostMapping("/editSupplierInfo")
    //@DistributedLock(key = "#dto.sku + '-' + #dto.skuId", lockTime = 10L, keyPrefix = "sku-")
    @DistributedLock(key = "#dto.sku", lockTime = 10L, keyPrefix = "sku-")
    public R<Boolean> editSupplierInfo(@RequestBody @Validated ProductSkuSupplierInfoDTO dto) {
        return R.ok(productSkuSupplierMeasureService.editSupplierInfo(dto));
    }

}
