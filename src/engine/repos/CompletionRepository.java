package engine.repos;

import engine.entities.Completion;
import engine.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Integer> {
//    @Query("select c FROM Completion c WHERE c.userid = :UserID")
    Page<Completion> findCompletionsByUser(User user, Pageable paging);
}
