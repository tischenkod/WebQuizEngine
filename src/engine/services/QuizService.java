package engine.services;

import engine.entities.Quiz;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuizService {
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(final Integer id);
    Quiz saveQuiz(final Quiz quiz);
    void deleteQuiz(final Quiz quiz);

    Page<Quiz> getAllQuizzes(Integer page);
}
