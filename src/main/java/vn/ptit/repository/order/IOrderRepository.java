package vn.ptit.repository.order;

import vn.ptit.model.Order;
import vn.ptit.model.QueryFilter;

import java.util.List;

public interface IOrderRepository {
    void save(Order order);
    List<Order> findByUser(String username, QueryFilter filter);
    List<Order> findAll(QueryFilter filter);
    Order findById(long id);
    void updateOrderStatus(int status, long id);
}
