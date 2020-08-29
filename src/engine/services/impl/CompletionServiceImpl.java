package engine.services.impl;

import engine.entities.Completion;
import engine.entities.User;
import engine.repos.CompletionRepository;
import engine.services.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CompletionServiceImpl implements CompletionService {
    @Autowired
    CompletionRepository completionRepository;

    @Override
    public Page<Completion> findCompletionsByUser(User user, Integer page) {
        Pageable paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completionRepository.findCompletionsByUser(user, paging);
    }

    @Override
    public Completion saveCompletion(Completion completion) {
        return completionRepository.save(completion);
    }
}
