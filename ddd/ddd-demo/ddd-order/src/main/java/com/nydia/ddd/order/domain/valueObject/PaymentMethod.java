package com.nydia.ddd.order.domain.valueObject;

import com.nydia.ddd.base.model.BaseEntity;
import lombok.Data;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/8/3 01:28
 * @Description: 支付方法
 */
@Data
public class PaymentMethod extends BaseEntity {

    private String name;

}
