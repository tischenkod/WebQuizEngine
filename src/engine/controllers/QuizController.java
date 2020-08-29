package engine.controllers;

import engine.Answer;
import engine.QuizResponse;
import engine.entities.Completion;
import engine.entities.User;
import engine.services.CompletionService;
import engine.services.QuizService;
import engine.entities.Quiz;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RestController()
@RequestMapping(path = "/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompletionService completionService;

    @PostMapping(path = "", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz, Principal principal, HttpServletResponse res) {
        if (quiz.getId() != null && quizService.getQuizById(quiz.getId()) != null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (quiz.getAnswer() == null)
            quiz.setAnswer(new int[0]);
        quiz.setUser(userService.getUserByEmail(principal.getName()));
        return quizService.saveQuiz(quiz);
    }

    @GetMapping(path = "/{id}")
    public Quiz getQuiz(@PathVariable int id, HttpServletResponse res) {
        try {
            return quizService.getQuizById(id);
        }catch (NoSuchElementException e) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping(path = "")
    Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0", name = "page") Integer pageNo) {
        Page<Quiz> page = quizService.getAllQuizzes(pageNo);
        return page;
    }

    @PostMapping(path = "/{id}/solve", consumes = "application/json")
    public QuizResponse solveQuiz(@PathVariable int id, @RequestBody Answer answer, Principal principal) {
        Quiz quiz = quizService.getQuizById(id);
        if (Arrays.equals(quiz.getAnswer(), answer.getAnswer())) {
            User user = userService.getUserByEmail(principal.getName());
            completionService.saveCompletion(new Completion(quiz, user, LocalDateTime.now()));
            return new QuizResponse(true, "Congratulations, you're right!");
        } else {
            return new QuizResponse(false, "Wrong answer! Please, try again.");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable int id, Principal principal, HttpServletResponse res) {
        try {
            if (principal == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            if (!quizService.getQuizById(id).getUser().getEmail().equals(principal.getName())) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            quizService.deleteQuiz(quizService.getQuizById(id));
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

//    @JsonView(View.Summary.class)
    @GetMapping("/completed")
    public Page<Completion> getCompletions(@RequestParam(defaultValue = "0", name = "page") Integer pageNo, Principal principal, HttpServletResponse res) {
        if (principal == null) {
            System.out.println("Principal is null");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        System.out.println("Completions for user: " + principal.getName());
        User user = userService.getUserByEmail(principal.getName());
        if (user == null)
            System.out.println("User is null");
        else
            System.out.println("Found user\n" + user);

        return completionService.findCompletionsByUser(user, pageNo);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
