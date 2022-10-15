package vn.ptit.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.statistic.StatShipmentWithTotalShippedService;

import java.util.Collections;

@RestController
@RequestMapping("/statistic")
public class StatShipmentWithTotalShippedController {
    private final StatShipmentWithTotalShippedService statShipmentWithTotalShippedService;

    public StatShipmentWithTotalShippedController(StatShipmentWithTotalShippedService statShipmentWithTotalShippedService) {
        this.statShipmentWithTotalShippedService = statShipmentWithTotalShippedService;
    }

    @GetMapping("/shipment-with-total-shipped")
    public ResponseEntity<?> shipmentWithTotalShipped(){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(statShipmentWithTotalShippedService.shipmentWithTotalShipped()));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
