package vn.ptit.repository.user;

import org.springframework.stereotype.Repository;
import vn.ptit.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements IUserRepository{
    private final UserJpa userJpa;

    public UserRepository(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        userJpa.save(entity);
    }

    @Override
    public User getById(long id) {
        Optional<UserEntity> optionalUser = userJpa.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public User getByUsername(String username) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByUsername(username));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByEmail(email));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public User getByMobile(String mobile) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByMobile(mobile));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userJpa.findByIsDeleteFalse().stream().map(UserEntity::toDomain).collect(Collectors.toList());
    }
}
