package engine.services;

import engine.entities.Completion;
import engine.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompletionService {
    Page<Completion> findCompletionsByUser(final User user, Integer page);
    Completion saveCompletion(final Completion completion);
}
