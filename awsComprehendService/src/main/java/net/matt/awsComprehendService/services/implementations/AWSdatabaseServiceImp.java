package net.matt.awsComprehendService.services.implementations;

import net.matt.awsComprehendService.services.AWSdatabaseService;
import org.springframework.stereotype.Service;

@Service
public class AWSdatabaseServiceImp implements AWSdatabaseService {
    @Override
    public String persistEvaluatedFeedback() {
        return "result saved!";
    }
}
