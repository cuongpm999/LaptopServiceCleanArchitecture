package vn.ptit.repository.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import vn.ptit.model.Comment;
import vn.ptit.model.QueryFilter;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepository implements ICommentRepository{
    private final CommentJpa commentJpa;

    public CommentRepository(CommentJpa commentJpa) {
        this.commentJpa = commentJpa;
    }

    @Override
    public void save(Comment comment) {
        commentJpa.save(CommentEntity.fromDomain(comment));
    }

    @Override
    public List<Comment> findByLaptopIdAndIsDeleteFalse(QueryFilter filter, long laptopId) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(),
                filter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return commentJpa.findByLaptopIdAndIsDeleteFalse(pageable, laptopId).stream().map(CommentEntity::toDomain).collect(Collectors.toList());
    }
}
