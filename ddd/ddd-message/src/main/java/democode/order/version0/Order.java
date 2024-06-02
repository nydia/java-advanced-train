package democode.order.version0;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author nydia
 * Created on 2021/10/18
 */
@Data
public class Order {
    private long orderId;
    private int buyerId;
    private int sellerId;
    private BigDecimal amount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private String address;
}
