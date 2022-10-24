package vn.ptit.controller.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.shipment.DeleteShipmentService;

@RequestMapping("/shipment")
@RestController
@CrossOrigin(origins = "*")
public class DeleteShipmentController {
    private final DeleteShipmentService deleteShipmentService;

    public DeleteShipmentController(DeleteShipmentService deleteShipmentService) {
        this.deleteShipmentService = deleteShipmentService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if (id == null)
            throw new InvalidRequestException("Require [id]");
        deleteShipmentService.delete(id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
