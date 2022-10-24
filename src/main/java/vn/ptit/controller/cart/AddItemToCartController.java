package vn.ptit.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.cart.AddItemToCartService;

@RequestMapping("/cart")
@RestController
@CrossOrigin(origins = "*")
public class AddItemToCartController {
    private final AddItemToCartService addItemToCartService;

    public AddItemToCartController(AddItemToCartService addItemToCartService) {
        this.addItemToCartService = addItemToCartService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestHeader(name = "username") String username,
                                    @RequestHeader(name = "item-id") Long itemId){

        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");
        if (itemId == null)
            throw new InvalidRequestException("Require [item-id]");

        addItemToCartService.addItemToCart(username,itemId);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
