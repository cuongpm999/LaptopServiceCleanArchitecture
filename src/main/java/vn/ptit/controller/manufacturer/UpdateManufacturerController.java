package vn.ptit.controller.manufacturer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.manufacturer.CreateManufacturerService;
import vn.ptit.service.manufacturer.UpdateManufacturerService;

@RequestMapping("/manufacturer")
@RestController
@CrossOrigin(origins = "*")
public class UpdateManufacturerController {
    private final UpdateManufacturerService updateManufacturerService;

    public UpdateManufacturerController(UpdateManufacturerService updateManufacturerService) {
        this.updateManufacturerService = updateManufacturerService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateManufacturerService.UpdateInput input){
        updateManufacturerService.update(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
