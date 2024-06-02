package democode.order.version0;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author nydia
 * Created on 2021/9/30
 */
@Data
public class OrderItem {
    private long orderId;
    private long itemId;
    private BigDecimal price;
}
