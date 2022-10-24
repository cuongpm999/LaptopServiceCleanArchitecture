package vn.ptit.controller.laptop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.laptop.DeleteLaptopService;

@RequestMapping("/laptop")
@RestController
@CrossOrigin(origins = "*")
public class DeleteLaptopController {
    private final DeleteLaptopService deleteLaptopService;

    public DeleteLaptopController(DeleteLaptopService deleteLaptopService) {
        this.deleteLaptopService = deleteLaptopService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id){
        if (id == null)
            throw new InvalidRequestException("Require [id]");

        deleteLaptopService.delete(id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

}
