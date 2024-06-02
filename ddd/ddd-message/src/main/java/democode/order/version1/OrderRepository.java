package democode.order.version1;

/**
 * @author nydia
 * Created on 2021/10/18
 */
public class OrderRepository {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;


    public OrderRepository(OrderDao orderDao, OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    public void save(Order order){
        // 在此处通过Order实体,创建数据对象 new OrderPO() ; new OrderItemPO();
        // orderDao => 存储订单数据
        // orderItemDao => 存储订单商品数据

    }

    public Order find(long orderId){
        //找到数据对象,OrderPO
        //找到数据对象,OrderItemPO
        //组合返回，order实体
        return new Order();
    }
}
