package vn.ptit.service.order;

import java.util.List;

public interface IGetOrderService {
    List<GetOrderService.Output> getListOrderByUser(String username, Integer page, Integer limit, String sort);
    List<GetOrderService.Output> getList(Integer page, Integer limit, String sort);
    GetOrderService.Output getById(long id);
}
