package democode.order.version1;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author zhengyin
 * Created on 2021/10/18
 */
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 创建订单
     * @param buyerId
     * @param sellerId
     * @param orderItems
     */
    public void createOrder(int buyerId, int sellerId, Set<OrderItem> orderItems){
        Order order = new Order(1L,buyerId,sellerId,new Address(),orderItems);
        //运费不随订单其它信息一同构造，因为运费可能在后期会进行修改，因此提供一个设置运费的方法
        order.setShippingFee(BigDecimal.TEN);
        orderRepository.save(order);
    }

    /**
     * 更新收货地址
     * @param orderId
     */
    public void updateAddress(long orderId,Address address){
        Order order = orderRepository.find(orderId);
        order.updateAddress(address);
        orderRepository.save(order);
    }

    /**
     * 设置优惠
     * @param orderId
     * @param discountAmount
     */
     public void setDiscount(long orderId, BigDecimal discountAmount){
        Order order = orderRepository.find(orderId);
        order.setDiscount(discountAmount);
        orderRepository.save(order);
    }

}
