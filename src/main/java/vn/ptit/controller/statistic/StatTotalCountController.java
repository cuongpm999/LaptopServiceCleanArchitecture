package vn.ptit.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.statistic.StatTotalCountService;

import java.util.Collections;

@RestController
@RequestMapping("/statistic")
@CrossOrigin(origins = "*")
public class StatTotalCountController {
    private final StatTotalCountService statTotalCountService;

    public StatTotalCountController(StatTotalCountService statTotalCountService) {
        this.statTotalCountService = statTotalCountService;
    }

    @GetMapping("/total-laptop")
    public ResponseEntity<?> totalLaptop(){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statTotalCountService.totalLaptop()));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/total-money-received")
    public ResponseEntity<?> totalMoneyReceived(){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statTotalCountService.totalMoneyReceived()));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/total-user-purchased")
    public ResponseEntity<?> totalUserPurchased(){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statTotalCountService.totalUserPurchased()));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
