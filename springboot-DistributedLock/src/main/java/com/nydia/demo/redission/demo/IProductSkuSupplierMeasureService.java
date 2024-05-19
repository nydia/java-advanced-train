package com.nydia.demo.redission.demo;

// 定义接口
public interface IProductSkuSupplierMeasureService {
    //保存SKU供应商供货信息
    Boolean saveSupplierInfo(ProductSkuSupplierInfoDTO dto);


    //编辑SKU供应商供货信息
    Boolean editSupplierInfo(ProductSkuSupplierInfoDTO dto);
}    
 
 