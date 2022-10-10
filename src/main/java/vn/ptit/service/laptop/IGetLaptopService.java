package vn.ptit.service.laptop;

import vn.ptit.model.Laptop;

import java.util.List;

public interface IGetLaptopService {
    GetLaptopService.Output getById(long id);
    List<GetLaptopService.Output> getList(Integer page, Integer limit, String sort);
    List<GetLaptopService.Output> getSameManufacturer(Long manufacturerId, Long id, Integer limit);
    List<GetLaptopService.Output> filter(Integer page, Integer limit, String sort, String searchText, String manufacturerId, String category, String cpu, String ram, String hardDrive, String vga);
}
