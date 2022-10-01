package vn.ptit.controller.manufacturer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.manufacturer.CreateManufacturerService;
import vn.ptit.service.manufacturer.ListManufacturerService;

@RequestMapping("/manufacturer")
@RestController
public class ListManufacturerController {
    private final ListManufacturerService listManufacturerService;

    public ListManufacturerController(ListManufacturerService listManufacturerService) {
        this.listManufacturerService = listManufacturerService;
    }


    @GetMapping ("/list")
    public ResponseEntity<?> list(){
        PagingPayload.PagingPayloadBuilder payloadBuilder = PagingPayload.builder();
        payloadBuilder.timestamp(System.currentTimeMillis());
        payloadBuilder.data(listManufacturerService.getList());
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
