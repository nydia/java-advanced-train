package democode.order.version0;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 业务规则直接散落在Service层，实体对象只作为数据的载体，代码无法直接反应业务
 * @author nydia
 * Created on 2021/10/18
 */
public class OrderService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    public OrderService(OrderDao orderDao, OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    /**
     * 创建订单
     * @param buyerId
     * @param sellerId
     * @param orderItems
     */
    public void createOrder(int buyerId,int sellerId,List<OrderItem> orderItems){
        //新建一个Order数据对象
        Order order = new Order();
        order.setOrderId(1L);
        //算订单总金额
        BigDecimal amount = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        order.setAmount(amount);
        //运费
        order.setShippingFee(BigDecimal.TEN);
        //优惠金额
        order.setDiscountAmount(BigDecimal.ZERO);
        //支付总额 = 订单总额 + 运费 - 优惠金额
        BigDecimal payAmount = order.getAmount().add(order.getShippingFee()).subtract(order.getDiscountAmount());
        order.setPayAmount(payAmount);
        //设置买卖家
        order.setBuyerId(buyerId);
        order.setSellerId(sellerId);
        //设置收获地址
        order.setAddress(JSON.toJSONString(new Address()));
        //写库
        orderDao.insert(order);
        orderItems.forEach(orderItemDao::insert);
    }

    /**
     * 更新收货地址
     * @param orderId
     */
    public void updateAddress(long orderId,Address address){
        //新建一个Order数据对象
        Order order = new Order();
        order.setOrderId(orderId);
        order.setAddress(JSON.toJSONString(address));
        //orderDao => 通过主键更新订单信息
        orderDao.updateByPrimaryKey(order);
    }


    /**
     * 设置优惠
     * @param orderId
     * @param discountAmount
     */
    public void setDiscount(long orderId, BigDecimal discountAmount){
        Order order = orderDao.find(orderId);
        order.setDiscountAmount(discountAmount);
        //从新计算支付金额
        BigDecimal payAmount = order.getAmount().add(order.getShippingFee()).subtract(discountAmount);
        order.setPayAmount(payAmount);
        //orderDao => 通过主键更新订单信息
        orderDao.updateByPrimaryKey(order);
    }
}
