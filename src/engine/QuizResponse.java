package engine;

public class QuizResponse {
    boolean success;
    String feedback;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public QuizResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;

    }
}
