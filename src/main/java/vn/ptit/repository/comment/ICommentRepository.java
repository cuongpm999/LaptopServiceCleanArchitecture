package vn.ptit.repository.comment;

import vn.ptit.model.Comment;
import vn.ptit.model.QueryFilter;

import java.util.List;

public interface ICommentRepository {
    void save(Comment comment);
    List<Comment> findByLaptopIdAndIsDeleteFalse(QueryFilter filter, long laptopId);
}
