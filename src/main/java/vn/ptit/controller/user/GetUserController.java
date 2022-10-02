package vn.ptit.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.user.IGetUserService;

import java.util.Collections;

@RequestMapping("/user")
@RestController
public class GetUserController {
    private final IGetUserService getUserService;

    public GetUserController(IGetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getUserService.getList(page,limit));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping ("/get-by-id/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable("id") Long id){
        if (id == null)
            throw new InvalidRequestException("Require [id]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getUserService.getById(id)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping ("/get-by-username/{username}")
    public ResponseEntity<?> getDetailByUsername(@PathVariable("username") String username){
        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getUserService.getByUsername(username)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping ("/get-by-email/{email}")
    public ResponseEntity<?> getDetailByEmail(@PathVariable("email") String email){
        if (email == null || email.isEmpty())
            throw new InvalidRequestException("Require [email]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(Collections.singletonList(getUserService.getByEmail(email)));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

}
