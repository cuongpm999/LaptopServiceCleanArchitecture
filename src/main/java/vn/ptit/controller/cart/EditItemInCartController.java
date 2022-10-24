package vn.ptit.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.cart.EditItemInCartService;

@RequestMapping("/cart")
@RestController
@CrossOrigin(origins = "*")
public class EditItemInCartController {
    private final EditItemInCartService editItemInCartService;

    public EditItemInCartController(EditItemInCartService editItemInCartService) {
        this.editItemInCartService = editItemInCartService;
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestHeader(name = "username") String username,
                                    @RequestHeader(name = "item-id") Long itemId,
                                    @RequestHeader(name = "quantity") Integer quantity){

        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");
        if (itemId == null)
            throw new InvalidRequestException("Require [item-id]");
        if (quantity == null)
            throw new InvalidRequestException("Require [quantity]");

        editItemInCartService.editItemInCart(username,itemId, quantity);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
