package engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@JsonPropertyOrder({"id", "completedAt"})
public class Completion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QuizID")
    private Quiz quiz;

//    @JsonView(View.Summary.class)
    @Formula("QuizID")
    private Integer QuizID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

//    @JsonView(View.Summary.class)
    private LocalDateTime completedAt;

    public Completion() {
    }

    public Completion(Quiz quiz, User user, LocalDateTime completedAt) {
        this.quiz = quiz;
        this.user = user;
        this.completedAt = completedAt;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @JsonProperty("id")
    public Integer getQuizID() {
        return QuizID;
    }
}
