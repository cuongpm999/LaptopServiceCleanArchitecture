package vn.ptit.service.laptop;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.*;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.service.manufacturer.GetManufacturerService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetLaptopService implements IGetLaptopService {
    private final ILaptopRepository laptopRepository;

    public GetLaptopService(ILaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }


    @Override
    @SneakyThrows
    public Output getById(long id) {
        Laptop laptop = laptopRepository.findById(id);
        if (laptop == null) {
            throw new DataNotFoundException("Laptop not found");
        }
        return Output.createOutput(laptop);
    }

    @Override
    public List<Output> getList(Integer page, Integer limit, String sort) {
        QueryFilter filter = QueryFilter.create(limit,page, sort);
        return laptopRepository.findAll(filter).stream().map(GetLaptopService.Output::createOutput).collect(Collectors.toList());
    }

    @Override
    public List<Output> getSameManufacturer(Long manufacturerId, Long id, Integer limit) {
        return laptopRepository.findSameManufacturer(manufacturerId, id, limit).stream().map(GetLaptopService.Output::createOutput).collect(Collectors.toList());
    }

    @Override
    public List<Output> filter(Integer page, Integer limit, String sort,
                               String searchText, List<Long> manufacturerIds,
                               List<Integer> categories, List<String> cpus,
                               List<String> rams, List<String> hardDrives, List<String> vgas) {
        LaptopFilter laptopFilter = LaptopFilter.create(limit, page, sort, searchText, manufacturerIds, categories, cpus, rams, hardDrives, vgas);
        return laptopRepository.filter(laptopFilter).stream().map(GetLaptopService.Output::createOutput).collect(Collectors.toList());
    }
}
