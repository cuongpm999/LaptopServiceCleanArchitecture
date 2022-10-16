package vn.ptit.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.statistic.StatLaptopWithTotalSoldService;

import java.util.Collections;

@RestController
@RequestMapping("/statistic")
public class StatLaptopWithTotalSoldController {
    private final StatLaptopWithTotalSoldService statLaptopWithTotalSoldService;

    public StatLaptopWithTotalSoldController(StatLaptopWithTotalSoldService statLaptopWithTotalSoldService) {
        this.statLaptopWithTotalSoldService = statLaptopWithTotalSoldService;
    }

    @GetMapping("/laptop-with-total-sold")
    public ResponseEntity<?> laptopWithTotalSold(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort) {
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statLaptopWithTotalSoldService.laptopWithTotalSold(page, limit, sort)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
