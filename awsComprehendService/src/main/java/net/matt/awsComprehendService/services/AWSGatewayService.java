package net.matt.awsComprehendService.services;

import org.springframework.stereotype.Service;

public interface AWSGatewayService {
    String callAWSComprehend(String feedbackEntry);
}
