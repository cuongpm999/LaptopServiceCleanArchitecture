package vn.ptit.service.laptop;

import vn.ptit.model.Laptop;

import java.util.List;

public interface IGetLaptopService {
    GetLaptopService.Output getById(long id);
    List<GetLaptopService.Output> getList(Integer page, Integer limit);
}
