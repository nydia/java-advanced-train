package democode.order.version1;

/**
 * @author nydia
 * Created on 2021/10/18
 */
public interface OrderDao {
    void insert(OrderPO order);
    void updateByPrimaryKey(OrderPO order);
}
