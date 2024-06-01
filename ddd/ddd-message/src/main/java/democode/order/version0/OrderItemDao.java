package democode.order.version0;

import java.util.List;

/**
 * @author zhengyin
 * Created on 2021/10/18
 */
public interface OrderItemDao {
    void insert(OrderItem orderItem);
    void update(OrderItem orderItem);
    List<OrderItem> getOrderItems(long orderId);
}
