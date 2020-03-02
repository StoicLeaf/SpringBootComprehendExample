package net.matt.awsComprehendService.services.implementations;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import net.matt.awsComprehendService.services.AWSGatewayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.AWSCredentialsProvider;

@Service
public class AWSGatewayServiceImp implements AWSGatewayService {

    @Value("${AWS_ACCESS_KEY}")
    private String awsAccessKey;

    @Value("${AWS_SECRET_KEY}")
    private String awsSecretKey;

    @Override
    public String callAWSComprehend(String feedbackEntry) {
        AWSCredentialsProvider credentials = DefaultAWSCredentialsProviderChain.getInstance();

        AmazonComprehend comprehendClient =
                AmazonComprehendClientBuilder.standard()
                        .withCredentials(credentials)
                        .withRegion("eu-central-1")
                        .build();

        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(feedbackEntry)
                .withLanguageCode("en");
        DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
        return detectSentimentResult.toString();
    }
}
