package vn.ptit.controller.manufacturer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.manufacturer.DeleteManufacturerService;
import vn.ptit.service.manufacturer.UpdateManufacturerService;

@RequestMapping("/manufacturer")
@RestController
public class DeleteManufactureController {
    private final DeleteManufacturerService deleteManufacturerService;

    public DeleteManufactureController(DeleteManufacturerService deleteManufacturerService) {
        this.deleteManufacturerService = deleteManufacturerService;
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id){
        deleteManufacturerService.delete(id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
