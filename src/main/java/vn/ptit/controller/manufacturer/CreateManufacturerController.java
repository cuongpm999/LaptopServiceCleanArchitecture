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
public class CreateManufacturerController {
    private final CreateManufacturerService createManufacturerService;

    public CreateManufacturerController(CreateManufacturerService createManufacturerService) {
        this.createManufacturerService = createManufacturerService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateManufacturerService.CreateInput input){
        createManufacturerService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
