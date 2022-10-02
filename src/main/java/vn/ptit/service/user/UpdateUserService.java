package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Service;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

import java.util.Date;

@Service
public class UpdateUserService {
    private final IUserRepository userRepository;

    public UpdateUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void update(UpdateInput input){
        User user = userRepository.getById(input.id);
        user.update(input.fullName, input.address, input.email, input.mobile, input.sex, input.dateOfBirth,
                input.position, input.avatar);
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpdateInput {
        public Long id;
        private String note;
        private String fullName;
        private String address;
        private String email;
        private String mobile;
        private Boolean sex;
        private String dateOfBirth;
        private String position;
        private String avatar;
    }
}
