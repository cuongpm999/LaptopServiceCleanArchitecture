package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
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

        }
        User user = User.create(input.fullName, input.address, input.email, input.mobile, input.sex,
                input.dateOfBirth, input.username, input.password, input.position, input.avatar);
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CreateInput {
        private String fullName;
        private String address;
        private String email;
        private String mobile;
        private Boolean sex;
        private String dateOfBirth;
        private String username;
        private String password;
        private String position;
        private String avatar;
    }
}
