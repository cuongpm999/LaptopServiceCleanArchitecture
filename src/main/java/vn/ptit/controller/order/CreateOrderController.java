package vn.ptit.controller.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.manufacturer.CreateManufacturerService;
import vn.ptit.service.order.CreateOrderService;

@RequestMapping("/order")
@RestController
public class CreateOrderController {
    private final CreateOrderService createOrderService;

    public CreateOrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateOrderService.CreateInput input){
        createOrderService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
