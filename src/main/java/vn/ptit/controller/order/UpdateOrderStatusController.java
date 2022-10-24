package vn.ptit.controller.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.order.UpdateStatusOrderService;


@RequestMapping("/order")
@RestController
@CrossOrigin(origins = "*")
public class UpdateOrderStatusController {
    private final UpdateStatusOrderService updateStatusOrderService;

    public UpdateOrderStatusController(UpdateStatusOrderService updateStatusOrderService) {
        this.updateStatusOrderService = updateStatusOrderService;
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestHeader(name = "status") int status, @RequestHeader(name = "order-id" ) long id) {
        if (status < 0 || status > 3)
            throw new InvalidRequestException("Status must in range [0,3]");
        updateStatusOrderService.updateStatusOrder(status, id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
