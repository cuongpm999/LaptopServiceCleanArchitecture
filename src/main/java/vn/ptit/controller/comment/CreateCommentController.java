package vn.ptit.controller.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;
import vn.ptit.service.comment.CreateCommentService;

@RequestMapping("/comment")
@RestController
@CrossOrigin(origins = "*")
public class CreateCommentController {
    private final CreateCommentService createCommentService;

    public CreateCommentController(CreateCommentService createCommentService) {
        this.createCommentService = createCommentService;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> create(@RequestBody CreateCommentService.CreateInput input){
        createCommentService.create(input);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(),ResponseBody.Status.SUCCESS,ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
