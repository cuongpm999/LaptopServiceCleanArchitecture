package vn.ptit.service.user;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import vn.ptit.exception.DataNotFoundException;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class DeleteUserService {
    private final IUserRepository userRepository;

    public DeleteUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public void delete(Long id){
        User user = userRepository.getById(id);
        if(user == null){
            throw new DataNotFoundException("User not found");
        }
        user.delete();
        userRepository.save(user);
    }
}
