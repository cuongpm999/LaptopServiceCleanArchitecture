package vn.ptit.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.statistic.StatShipmentWithTotalShippedService;

import java.util.Collections;

@RestController
@RequestMapping("/statistic")
@CrossOrigin(origins = "*")
public class StatShipmentWithTotalShippedController {
    private final StatShipmentWithTotalShippedService statShipmentWithTotalShippedService;

    public StatShipmentWithTotalShippedController(StatShipmentWithTotalShippedService statShipmentWithTotalShippedService) {
        this.statShipmentWithTotalShippedService = statShipmentWithTotalShippedService;
    }

    @GetMapping("/shipment-with-total-shipped")
    public ResponseEntity<?> shipmentWithTotalShipped(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statShipmentWithTotalShippedService.shipmentWithTotalShipped(page, limit, sort)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
