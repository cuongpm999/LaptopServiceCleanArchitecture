package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Service;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class CreateUserService {
    private final IUserRepository userRepository;

    public CreateUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(CreateInput input){
        if(userRepository.getByEmail(input.email) != null){
            throw new InvalidRequestException("Email existed!");
        }
        if(userRepository.getByUsername(input.username) != null){
            throw new InvalidRequestException("Username existed!");
        }
        if(userRepository.getByMobile(input.mobile) != null){
            throw new InvalidRequestException("Mobile existed!");
        }
        User user = User.create(input.fullName, input.address, input.email, input.mobile, input.sex,
                input.dateOfBirth, input.username, input.password, input.position, input.avatar);
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class CreateInput {
        @JsonAlias("full_name")
        private String fullName;
        private String address;
        private String email;
        private String mobile;
        private Boolean sex;
        @JsonAlias("date_of_birth")
        private String dateOfBirth;
        private String username;
        private String password;
        private String position;
        private String avatar;
    }
}
