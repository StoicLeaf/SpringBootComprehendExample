package net.matt.awsComprehendService.services;

public interface SessionDataService {

    String getFeedback();

    void setFeedback(String feedback);

    String getFeedbackEvaluation();

    void setFeedbackEvaluation(String feedbackEvaluation);
}
