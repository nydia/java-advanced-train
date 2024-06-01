package democode.aggregation.order;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author zhengyin
 * Created on 2021/9/30
 */
@Getter
public class OrderItem {
    private long orderId;
    private long itemId;
    private BigDecimal price;
}
