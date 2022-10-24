package vn.ptit.controller.laptop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidDataException;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.laptop.CreateLaptopService;
import vn.ptit.service.laptop.IGetLaptopService;

import java.util.Collections;
import java.util.List;

@RequestMapping("/laptop")
@RestController
@CrossOrigin(origins = "*")
public class GetLaptopController {
    private final IGetLaptopService getLaptopService;

    public GetLaptopController(IGetLaptopService getLaptopService) {
        this.getLaptopService = getLaptopService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) {
        if (id == null)
            throw new InvalidRequestException("Require [id]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getLaptopService.getById(id)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort) {
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.getList(page, limit, sort));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/same")
    public ResponseEntity<?> list(@RequestHeader(name = "manufacturer-id") Long manufacturerId,
                                  @RequestHeader(name = "laptop-id") Long id,
                                  @RequestParam(value = "limit", required = false, defaultValue = "4") Integer limit) {
        if (manufacturerId == null)
            throw new InvalidRequestException("Require [manufacturer-id]");

        if (id == null)
            throw new InvalidRequestException("Require [laptop-id]");

        if (limit != null && limit < 0) {
            throw new InvalidRequestException("QueryFilter[limit] must >= 0");
        }

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.getSameManufacturer(manufacturerId, id, limit));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                    @RequestParam(value = "key", required = false) String searchText,
                                    @RequestParam(value = "manufacturer", required = false) List<Long> manufacturerIds,
                                    @RequestParam(value = "category", required = false) List<Integer> categories,
                                    @RequestParam(value = "cpu", required = false) List<String> cpus,
                                    @RequestParam(value = "ram", required = false) List<String> rams,
                                    @RequestParam(value = "hard-drive", required = false) List<String> hardDrives,
                                    @RequestParam(value = "vga", required = false) List<String> vgas) {
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.filter(page, limit, sort, searchText, manufacturerIds, categories, cpus, rams, hardDrives, vgas));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
