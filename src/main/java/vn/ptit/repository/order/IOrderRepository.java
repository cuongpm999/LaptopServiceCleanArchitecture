package vn.ptit.repository.order;

import vn.ptit.model.Order;
import vn.ptit.model.QueryFilter;

import java.util.List;

public interface IOrderRepository {
    void save(Order order);
    List<Order> findByUser(String username, QueryFilter filter);
}
