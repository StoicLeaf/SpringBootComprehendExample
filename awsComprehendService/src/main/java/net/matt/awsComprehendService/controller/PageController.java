package net.matt.awsComprehendService.controller;

import com.google.gson.*;
import net.matt.awsComprehendService.entities.TextSentiment;
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

        String inferenceResponse = awsGatewayService.callAWSComprehend(feedbackEntry);

        if(JsonParser.parseString(inferenceResponse).getAsJsonObject().get("Sentiment") == null)
        {
            return "error";
        }

        TextSentiment textSentiment = new TextSentiment(feedbackEntry, inferenceResponse);

        awsDatabaseService.persistEvaluatedFeedback(textSentiment);

        sessionDataService.setTextSentiment(textSentiment);
        return "redirect:/app/feedbackResult";
    }

    @RequestMapping("/feedbackResult")
    public String feedbackResult(Model model){
        TextSentiment textSentiment = sessionDataService.getTextSentiment();

       String color = "";
       String text = "";

        switch(sessionDataService.getTextSentiment().getSentiment()){
            case "POSITIVE":
                color="color:green";
                text = "positive";
                break;
            case "NEGATIVE":
                color="color:red";
                text = "negative";
                break;
            case"NEUTRAL":
                color="color:grey";
                text = "neutral";
                break;
            case "MIXED":
                color="color:orange";
                text = "mixed";
                break;
        }
        model.addAttribute("textColor", color);
        model.addAttribute("textContent", text);

        DecimalFormat df = new DecimalFormat("##.##");
        String number = df.format(textSentiment.getPositiveEstimate()*100);
        model.addAttribute("positive", number);
        number = df.format(textSentiment.getNegativeEstimate()*100);
        model.addAttribute("negative", number);
        number = df.format(textSentiment.getNeutralEstimate()*100);
        model.addAttribute("neutral", number);
        number = df.format(textSentiment.getMixedEstimate()*100);
        model.addAttribute("mixed", number);

        return "feedbackResult";
    }
}
