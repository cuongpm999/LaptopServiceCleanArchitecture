package vn.ptit.service.order;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.*;
import vn.ptit.repository.order.IOrderRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOrderService implements IGetOrderService {
    private final IOrderRepository orderRepository;

    public GetOrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Output> getListOrderByUser(String username, Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return orderRepository.findByUser(username, filter).stream().map(GetOrderService.Output::createOutput).collect(Collectors.toList());
    }

    @Override
    public List<Output> getList(Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return orderRepository.findAll(filter).stream().map(Output::createOutput).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public Output getById(long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new DataNotFoundException("Order not found");
        }
        return Output.createOutput(order);
    }
}
