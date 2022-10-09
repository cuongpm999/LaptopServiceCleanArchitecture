package vn.ptit.controller.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.order.IGetOrderService;

@RequestMapping("/order")
@RestController
public class GetOrderController {
    private final IGetOrderService getOrderService;

    public GetOrderController(IGetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @GetMapping("/get-by-username")
    public ResponseEntity<?> list(@RequestHeader(name = "username") String username,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        if (username == null || username.isEmpty())
            throw new InvalidRequestException("Require [username]");

        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(getOrderService.getListOrderByUser(username, page, limit));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
