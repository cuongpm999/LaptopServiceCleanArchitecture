package vn.ptit.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.cart.FindCurrentCartService;

import java.util.Collections;

@RequestMapping("/cart")
@RestController
@CrossOrigin(origins = "*")
public class FindCurrentCartController {
    private final FindCurrentCartService findCurrentCartService;

    public FindCurrentCartController(FindCurrentCartService findCurrentCartService) {
        this.findCurrentCartService = findCurrentCartService;
    }


    @GetMapping("/current")
    public ResponseEntity<?> delete(@RequestHeader(name = "username") String username) {

        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");
        FindCurrentCartService.Output result = findCurrentCartService.findCurrentCart(username);

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(result == null ? Collections.emptyList() : Collections.singletonList(result));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

}
