package net.matt.awsComprehendService.services;

import net.matt.awsComprehendService.entities.TextSentiment;
import org.springframework.stereotype.Service;

public interface AWSdatabaseService {
    String persistEvaluatedFeedback(TextSentiment textSentiment);
}
