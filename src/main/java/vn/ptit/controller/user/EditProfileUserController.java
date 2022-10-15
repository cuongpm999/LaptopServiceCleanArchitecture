package vn.ptit.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.user.EditProfileUserService;
import vn.ptit.service.user.UpdateUserService;

@RequestMapping("/user")
@RestController
public class EditProfileUserController {
    private final EditProfileUserService editProfileUserService;

    public EditProfileUserController(EditProfileUserService editProfileUserService) {
        this.editProfileUserService = editProfileUserService;
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<?> update(@RequestBody EditProfileUserService.EditProfileInput input){
        editProfileUserService.editProfile(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
