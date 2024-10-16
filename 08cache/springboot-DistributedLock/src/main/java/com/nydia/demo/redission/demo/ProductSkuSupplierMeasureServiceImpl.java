package com.nydia.demo.redission.demo;

import com.nydia.demo.redission.lock.IDistributedLock;
import com.nydia.demo.redission.lock.ILock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

// 实现类
@Slf4j
@Service
public class ProductSkuSupplierMeasureServiceImpl implements IProductSkuSupplierMeasureService {

    @Resource
    private IDistributedLock distributedLock;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSupplierInfo(ProductSkuSupplierInfoDTO dto) {
        // 手动释放锁
        String sku = dto.getSku();
        ILock lock = null;
        try {
            lock = distributedLock.lock(dto.getSku(),10L, TimeUnit.SECONDS, false);
            if (Objects.isNull(lock)) {
                throw new RuntimeException("Duplicate request for method still in process");
            }
            // 业务代码
        } catch (Exception e) {
            log.error("保存异常", e);
            throw new RuntimeException (e.getMessage());
        } finally {
            if (Objects.nonNull(lock)) {
                distributedLock.unLock(lock);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editSupplierInfo(ProductSkuSupplierInfoDTO dto) {

        String sku = dto.getSku();
        // try-with-resources 语法糖自动释放锁
        try(ILock lock = distributedLock.lock(dto.getSku(),10L, TimeUnit.SECONDS, false)) {
            if(Objects.isNull(lock)){
                throw new RuntimeException ("Duplicate request for method still in process");
            }

            // 业务代码
        } catch (Exception e) {
            log.error("修改异常", e);
            throw new RuntimeException ("修改异常");
        }
        return Boolean.TRUE;
    }
 
}