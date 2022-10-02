package vn.ptit.controller.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.shipment.CreateShipmentService;

@RequestMapping("/shipment")
@RestController
public class CreateShipmentController {
    private final CreateShipmentService createShipmentService;

    public CreateShipmentController(CreateShipmentService createShipmentService) {
        this.createShipmentService = createShipmentService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateShipmentService.CreateInput input){
        createShipmentService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
