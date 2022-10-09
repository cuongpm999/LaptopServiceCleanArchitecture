package vn.ptit.repository.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import vn.ptit.model.QueryFilter;
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
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByUsernameAndIsDeleteFalse(username));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByEmailAndIsDeleteFalse(email));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public User getByMobile(String mobile) {
        Optional<UserEntity> optionalUser = Optional.ofNullable(userJpa.findByMobileAndIsDeleteFalse(mobile));
        if(optionalUser.isPresent()){
            User user = optionalUser.get().toDomain();
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAll(QueryFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(),
                filter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return userJpa.findByIsDeleteFalse(pageable).stream().map(UserEntity::toDomain).collect(Collectors.toList());
    }
}
