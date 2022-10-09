package vn.ptit.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.cart.DeleteItemInCartService;

@RequestMapping("/cart")
@RestController
public class DeleteItemInCartController {
    private final DeleteItemInCartService deleteItemInCartService;

    public DeleteItemInCartController(DeleteItemInCartService deleteItemInCartService) {
        this.deleteItemInCartService = deleteItemInCartService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader(name = "username") String username,
                                    @RequestHeader(name = "item-id") Long itemId) {

        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");
        if (itemId == null)
            throw new InvalidRequestException("Require [item-id]");

        deleteItemInCartService.deleteItemInCart(username, itemId);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll(@RequestHeader(name = "username") String username) {

        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");

        deleteItemInCartService.deleteAll(username);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
