package vn.ptit.repository.order;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.ptit.model.Cash;
import vn.ptit.model.DigitalWallet;
import vn.ptit.model.Order;
import vn.ptit.model.QueryFilter;
import vn.ptit.repository.payment.CashEntity;
import vn.ptit.repository.payment.IPaymentRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {
    private final OrderJpa orderJpa;
    private final IPaymentRepository paymentRepository;

    public OrderRepository(OrderJpa orderJpa, IPaymentRepository paymentRepository) {
        this.orderJpa = orderJpa;

        this.paymentRepository = paymentRepository;
    }

    @Override
    public void save(Order order) {
        orderJpa.save(OrderEntity.fromDomain(order));
    }

    @Override
    public List<Order> findByUser(String username, QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit());
        List<OrderEntity> orderEntities = orderJpa.findByUser_UsernameOrderByCreatedAtAsc(username, pageable);
        List<Order> result = new ArrayList<>();
        orderEntities.forEach(
                orderEntity -> {
                    Cash cash = paymentRepository.getCashById(orderEntity.getPayment().getId());
                    DigitalWallet digitalWallet = paymentRepository.getDigitalWalletById(orderEntity.getPayment().getId());
                    if (cash != null) {
                        Order order = orderEntity.toDomain();
                        order.setPayment(cash);
                        result.add(order);
                    } else {
                        Order order = orderEntity.toDomain();
                        order.setPayment(digitalWallet);
                        result.add(order);
                    }
                }
        );
        return result;
    }
}
