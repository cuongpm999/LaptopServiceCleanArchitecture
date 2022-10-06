package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.json.MyObjectMapper;
import vn.ptit.model.Manufacturer;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

import java.util.Date;
import java.util.List;

@Service
public class GetUserService implements IGetUserService{
    private final IUserRepository userRepository;

    public GetUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Output> getList() {
        return null;
    }

    @Override
    public Output getByUsername(String username) {
        return null;
    }

    @Override
    public Output getByEmail(String email) {
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Output {
        @JsonAlias("id")
        private Long id;
        @JsonProperty("full_name")
        @JsonAlias("fullName")
        private String fullName;
        @JsonAlias("address")
        private String address;
        @JsonAlias("email")
        private String email;
        @JsonAlias("mobile")
        private String mobile;
        @JsonAlias("sex")
        private Boolean sex;
        @JsonProperty("date_of_birth")
        @JsonAlias("dateOfBirth")
        private Date dateOfBirth;
        @JsonAlias("username")
        private String username;
        @JsonAlias("position")
        private String position;
        @JsonAlias("avatar")
        private String avatar;
        @JsonProperty("created_at")
        @JsonAlias("createdAt")
        private Date createdAt;
        @JsonAlias("updatedAt")
        @JsonProperty("updated_at")
        private Date updatedAt;
        @JsonProperty("is_delete")
        @JsonAlias("isDelete")
        private Boolean isDelete;

        public static GetUserService.Output createOutput(User user){
            try {
                GetUserService.Output output = MyObjectMapper.get()
                        .readValue(MyObjectMapper.get().writeValueAsString(user), GetUserService.Output.class);
                return output;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
