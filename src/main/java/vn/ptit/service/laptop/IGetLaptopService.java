package vn.ptit.service.laptop;

import vn.ptit.model.Laptop;

import java.util.List;

public interface IGetLaptopService {
    GetLaptopService.Output getById(long id);
    List<GetLaptopService.Output> getList(Integer page, Integer limit, String sort);
    List<GetLaptopService.Output> getSameManufacturer(Long manufacturerId, Long id, Integer limit);
    List<GetLaptopService.Output> filter(Integer page, Integer limit, String sort,
                                         String searchText, List<Long> manufacturerIds,
                                         List<Integer> categories, List<String> cpus,
                                         List<String> rams, List<String> hardDrives, List<String> vgas);
}
