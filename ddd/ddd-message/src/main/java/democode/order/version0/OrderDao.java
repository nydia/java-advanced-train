package democode.order.version0;
/**
 * @author nydia
 * Created on 2021/10/18
 */
public interface OrderDao {
    Order find(long orderId);
    void insert(Order order);
    void updateByPrimaryKey(Order order);
}
