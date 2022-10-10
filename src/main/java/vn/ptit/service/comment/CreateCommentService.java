package vn.ptit.service.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.Comment;
import vn.ptit.model.Laptop;
import vn.ptit.model.User;
import vn.ptit.repository.comment.ICommentRepository;
import vn.ptit.repository.laptop.ILaptopRepository;
import vn.ptit.repository.user.IUserRepository;

@Service
public class CreateCommentService {
    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final ILaptopRepository laptopRepository;

    public CreateCommentService(ICommentRepository commentRepository, IUserRepository userRepository, ILaptopRepository laptopRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.laptopRepository = laptopRepository;
    }

    @SneakyThrows
    public void create(CreateInput input){
        User user = userRepository.getById(input.user);
        if (user == null) {
            throw new DataNotFoundException("User not found");
        }
        Laptop laptop = laptopRepository.findById(input.laptop);
        if (laptop == null) {
            throw new DataNotFoundException("Laptop not found");
        }
        Comment comment = Comment.create(input.star, input.content, user, laptop);
        commentRepository.save(comment);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        private Integer star;
        private String content;
        private Integer user;
        private Integer laptop;
    }
}
