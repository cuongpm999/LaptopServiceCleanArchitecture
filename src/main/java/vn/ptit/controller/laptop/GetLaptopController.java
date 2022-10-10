package vn.ptit.controller.laptop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.laptop.CreateLaptopService;
import vn.ptit.service.laptop.IGetLaptopService;

import java.util.Collections;

@RequestMapping("/laptop")
@RestController
public class GetLaptopController {
    private final IGetLaptopService getLaptopService;

    public GetLaptopController(IGetLaptopService getLaptopService) {
        this.getLaptopService = getLaptopService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id){
        if (id == null)
            throw new InvalidRequestException("Require [id]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getLaptopService.getById(id)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.getList(page,limit, sort));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/get-same-manufacturer")
    public ResponseEntity<?> list(@RequestParam(value = "manufacturer-id") Long manufacturerId,
                                  @RequestParam(value = "laptop-id") Long id,
                                  @RequestParam(value = "limit", required = false, defaultValue = "4") Integer limit){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.getSameManufacturer(manufacturerId, id, limit));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                    @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
                                    @RequestParam(value = "manufacturerId", required = false, defaultValue = "") String manufacturerId,
                                    @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                    @RequestParam(value = "cpu", required = false, defaultValue = "") String cpu,
                                    @RequestParam(value = "ram", required = false, defaultValue = "") String ram,
                                    @RequestParam(value = "hardDrive", required = false, defaultValue = "") String hardDrive,
                                    @RequestParam(value = "vga", required = false, defaultValue = "") String vga){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getLaptopService.filter(page, limit, sort, searchText, manufacturerId, category, cpu, ram, hardDrive, vga));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
