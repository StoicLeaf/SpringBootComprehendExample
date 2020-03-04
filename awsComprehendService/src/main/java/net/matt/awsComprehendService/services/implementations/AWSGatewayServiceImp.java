package net.matt.awsComprehendService.services.implementations;

import com.google.gson.JsonObject;
import net.matt.awsComprehendService.services.AWSGatewayService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class AWSGatewayServiceImp implements AWSGatewayService {

    @Override
    public String callAWSComprehend(String feedbackEntry) {
        String detectSentimentResult = "";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", feedbackEntry);
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");

        HttpPost httpPost = new HttpPost("https://yrf4n2ki59.execute-api.eu-central-1.amazonaws.com/test/comprehendcall");
        httpPost.setEntity(stringEntity);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse);
            detectSentimentResult = EntityUtils.toString(httpResponse.getEntity());

        } catch (IOException e) {
            System.out.println("[ERROR] " + e.toString());
            return "error";
        }
        return detectSentimentResult.toString();
    }
}
