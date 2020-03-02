package net.matt.awsComprehendService.services.implementations;

import net.matt.awsComprehendService.services.SessionDataService;
import org.springframework.stereotype.Service;

@Service
public class SessionDataServiceImp implements SessionDataService {

    private String feedback;
    private String feedbackEvaluation;

    @Override
    public String getFeedback() {
        return feedback;
    }

    @Override
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String getFeedbackEvaluation() {
        return feedbackEvaluation;
    }

    @Override
    public void setFeedbackEvaluation(String feedbackEvaluation) {
        this.feedbackEvaluation = feedbackEvaluation;
    }
}
