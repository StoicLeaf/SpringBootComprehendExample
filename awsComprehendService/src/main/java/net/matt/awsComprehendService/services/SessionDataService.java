package net.matt.awsComprehendService.services;

import net.matt.awsComprehendService.entities.TextSentiment;

public interface SessionDataService {

    String getFeedback();

    void setFeedback(String feedback);

    TextSentiment getFeedbackEvaluation();

    void setFeedbackEvaluation(TextSentiment feedbackEvaluation);
}
