package vn.ptit.service.order;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Order;
import vn.ptit.repository.order.IOrderRepository;

@Service
public class UpdateStatusOrderService {
    private final IOrderRepository orderRepository;

    public UpdateStatusOrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @SneakyThrows
    public void updateStatusOrder(int status, long id){
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new DataNotFoundException("Order not found");
        }
        orderRepository.updateOrderStatus(status, id);
    }

}
