package democode.order.version1;

import democode.order.version0.OrderItem;

import java.util.List;

/**
 * @author nydia
 * Created on 2021/10/18
 */
public interface OrderItemDao {
    void insert(OrderItemPO orderItem);
    void update(OrderItemPO orderItem);
    List<OrderItemPO> getOrderItems(long orderId);
}