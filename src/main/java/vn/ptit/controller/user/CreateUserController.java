package vn.ptit.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.user.CreateUserService;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "*")
public class CreateUserController {
    private final CreateUserService createUserService;

    public CreateUserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateUserService.CreateInput input){
        createUserService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
