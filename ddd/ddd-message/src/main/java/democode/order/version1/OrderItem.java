package democode.order.version1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author zhengyin
 * Created on 2021/9/30
 */
@Getter
@AllArgsConstructor
public class OrderItem {
    private long orderId;
    private long itemId;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return orderId == orderItem.orderId && itemId == orderItem.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemId);
    }
}
