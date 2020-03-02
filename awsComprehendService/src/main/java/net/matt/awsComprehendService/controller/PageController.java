package net.matt.awsComprehendService.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.matt.awsComprehendService.services.AWSGatewayService;
import net.matt.awsComprehendService.services.AWSdatabaseService;
import net.matt.awsComprehendService.services.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/app")
public class PageController {

    @Autowired
    AWSGatewayService awsGatewayService;

    @Autowired
    AWSdatabaseService awsDatabaseService;

    @Autowired
    SessionDataService sessionDataService;

    @Autowired
    Gson gson;

    @RequestMapping("/feedbackEntry")
    public String feedbackEntryForm() {
        return "feedbackEntry";
    }

    @PostMapping("/feedbackProcessing")
        public String feedbackProcessing(@ModelAttribute("feedbackEntry") String feedbackEntry) throws InterruptedException, IOException {
        //THE FOLLOWING LINES ARE FOR TEST PURPOSES
        Thread.sleep(1000);
        Path path = Paths.get("");
        path = Paths.get(path.toAbsolutePath().toString() + "\\src\\main\\resources\\static\\sentimentResponseExample.txt");
        String mockSentimentJsonResponse = new String(Files.readAllBytes(path));
        //TESTING STUFF DONE

        String inferenceResponse = awsGatewayService.callAWSComprehend(feedbackEntry);
        JsonObject jsonObject = JsonParser.parseString(inferenceResponse).getAsJsonObject();
        System.out.println(jsonObject.toString());

        //String sentiment = jsonObject.get("Sentiment").getAsString();
        //Double positiveDouble = jsonObject.get("SentimentScore").getAsJsonObject().get("Positive").getAsDouble();
        //BigDecimal bd = new BigDecimal(positiveDouble).setScale(4, RoundingMode.HALF_UP);

        String databaseResponse = awsDatabaseService.persistEvaluatedFeedback();

        sessionDataService.setFeedback(feedbackEntry);
        sessionDataService.setFeedbackEvaluation("inferenceResponse.response");
        return "redirect:/app/feedbackResult";
    }

    @RequestMapping("/feedbackResult")
    public String feedbackResult(Model model){
        model.addAttribute("feedbackEntry", sessionDataService.getFeedback());
        model.addAttribute("feedbackEvaluation", sessionDataService.getFeedbackEvaluation());
        return "feedbackResult";
    }
}
