package vn.ptit.controller.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.shipment.UpdateShipmentService;

@RequestMapping("/shipment")
@RestController
public class UpdateShipmentController {
    private final UpdateShipmentService updateShipmentService;

    public UpdateShipmentController(UpdateShipmentService updateShipmentService) {
        this.updateShipmentService = updateShipmentService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateShipmentService.UpdateInput input){
        updateShipmentService.update(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
