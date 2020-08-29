package engine.services.impl;

import engine.entities.Quiz;
import engine.repos.QuizRepository;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Override
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>)quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(final Integer id) {
        return quizRepository.findById(id).get();
    }

    @Override
    public Quiz saveQuiz(final Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    @Override
    public Page<Quiz> getAllQuizzes(Integer page) {
        Pageable paging = PageRequest.of(page, 10);
        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        return pagedResult;
//        if (pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<>();
//        }
    }
}
