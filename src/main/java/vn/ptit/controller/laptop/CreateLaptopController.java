package vn.ptit.controller.laptop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.laptop.CreateLaptopService;
import vn.ptit.service.laptop.ImportFileLaptopService;

import java.io.IOException;

@RequestMapping("/laptop")
@RestController
@CrossOrigin(origins = "*")
public class CreateLaptopController {
    private final CreateLaptopService createLaptopService;
    private final ImportFileLaptopService importFileLaptopService;

    public CreateLaptopController(CreateLaptopService createLaptopService,
                                  ImportFileLaptopService importFileLaptopService) {
        this.createLaptopService = createLaptopService;
        this.importFileLaptopService = importFileLaptopService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateLaptopService.CreateInput input) {
        createLaptopService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/import-excel")
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        importFileLaptopService.importExcel(file.getInputStream());
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
