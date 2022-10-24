package vn.ptit.controller.laptop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.laptop.UpdateLaptopService;

@RequestMapping("/laptop")
@RestController
@CrossOrigin(origins = "*")
public class UpdateLaptopController {
    private final UpdateLaptopService updateLaptopService;

    public UpdateLaptopController(UpdateLaptopService updateLaptopService) {
        this.updateLaptopService = updateLaptopService;
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateLaptopService.UpdateInput input){
        updateLaptopService.update(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
