package vn.ptit.service.comment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.Comment;
import vn.ptit.model.QueryFilter;
import vn.ptit.repository.comment.ICommentRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCommentService {
    private final ICommentRepository commentRepository;

    public GetCommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Output> getList(Integer page, Integer limit, String sort, Long laptopId) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        return commentRepository.findByLaptopIdAndIsDeleteFalse(filter, laptopId).stream().map(Output::createOutput).collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
        private Long id;
        private Integer star;
        private String content;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        private UserOutput user;
        @JsonProperty("laptop_id")
        private Long laptopId;
        @JsonAlias("isDelete")
        @JsonProperty("is_delete")
        private Boolean isDelete;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class UserOutput {
            @JsonAlias("id")
            private Long id;
            @JsonProperty("full_name")
            @JsonAlias("fullName")
            private String fullName;
            @JsonAlias("username")
            private String username;
            @JsonAlias("email")
            private String email;
            @JsonAlias("mobile")
            private String mobile;
            @JsonAlias("avatar")
            private String avatar;
        }


        public static Output createOutput(Comment comment) {
            try {
                Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(comment), Output.class);
                output.laptopId = comment.getLaptop().getId();
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
