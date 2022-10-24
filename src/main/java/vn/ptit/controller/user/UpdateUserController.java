package vn.ptit.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.user.UpdateUserService;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "*")
public class UpdateUserController {
    private final UpdateUserService updateUserService;

    public UpdateUserController(UpdateUserService updateUserService) {
        this.updateUserService = updateUserService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateUserService.UpdateInput input){
        updateUserService.update(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
