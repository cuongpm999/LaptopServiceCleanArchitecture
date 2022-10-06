package vn.ptit.controller.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.shipment.IGetShipmentService;

import java.util.Collections;

@RequestMapping("/shipment")
@RestController
public class GetShipmentController {
    private final IGetShipmentService getShipmentService;

    public GetShipmentController(IGetShipmentService getShipmentService) {
        this.getShipmentService = getShipmentService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getShipmentService.getList(page,limit));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping ("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id){
        if (id == null)
            throw new InvalidRequestException("Require [id]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getShipmentService.getById(id)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
