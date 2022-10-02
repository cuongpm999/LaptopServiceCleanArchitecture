package vn.ptit.service.user;

import org.springframework.stereotype.Service;
import vn.ptit.model.User;
import vn.ptit.repository.user.IUserRepository;

@Service
public class DeleteUserService {
    private final IUserRepository userRepository;

    public DeleteUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id){
        User user = userRepository.getById(id);
        user.delete();
        userRepository.save(user);
    }
}
