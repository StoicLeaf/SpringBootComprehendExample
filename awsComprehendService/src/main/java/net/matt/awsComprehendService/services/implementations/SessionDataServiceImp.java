package net.matt.awsComprehendService.services.implementations;

import net.matt.awsComprehendService.entities.TextSentiment;
import net.matt.awsComprehendService.services.SessionDataService;
import org.springframework.stereotype.Service;

@Service
public class SessionDataServiceImp implements SessionDataService {

    private String feedback;
    private TextSentiment feedbackEvaluation;

    @Override
    public String getFeedback() {
        return feedback;
    }

    @Override
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public TextSentiment getFeedbackEvaluation() {
        return feedbackEvaluation;
    }

    @Override
    public void setFeedbackEvaluation(TextSentiment feedbackEvaluation) {
        this.feedbackEvaluation = feedbackEvaluation;
    }
}
