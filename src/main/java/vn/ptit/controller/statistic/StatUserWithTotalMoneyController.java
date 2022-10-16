package vn.ptit.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.statistic.StatUserWithTotalMoneyService;

import java.util.Collections;

@RestController
@RequestMapping("/statistic")
public class StatUserWithTotalMoneyController {
    private final StatUserWithTotalMoneyService statUserWithTotalMoneyService;

    public StatUserWithTotalMoneyController(StatUserWithTotalMoneyService statUserWithTotalMoneyService) {
        this.statUserWithTotalMoneyService = statUserWithTotalMoneyService;
    }

    @GetMapping("/user-with-total-money")
    public ResponseEntity<?> shipmentWithTotalShipped(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort) {
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statUserWithTotalMoneyService.userWithTotalMoney(page, limit, sort)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
