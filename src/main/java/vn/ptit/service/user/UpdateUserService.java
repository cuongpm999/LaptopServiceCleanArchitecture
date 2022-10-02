package vn.ptit.service.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.exception.InvalidRequestException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class UpdateUserService {
    private final IUserRepository userRepository;

    public UpdateUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public void update(UpdateInput input){
        User user = userRepository.getById(input.id);
        if(user == null){
            throw new DataNotFoundException("User not found");
        }
        if(userRepository.getByMobile(input.mobile) != null && !input.mobile.equals(user.getMobile())){
            throw new InvalidRequestException("Mobile existed!");
        }
        user.update(input.fullName, input.address, input.mobile, input.sex, input.dateOfBirth,
                input.position, input.avatar);
        userRepository.save(user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class UpdateInput {
        public Long id;
        @JsonProperty("full_name")
        private String fullName;
        private String address;
        private String mobile;
        private Boolean sex;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        private String position;
        private String avatar;
    }
}
