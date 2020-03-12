package net.matt.awsComprehendService.services.implementations;

import net.matt.awsComprehendService.entities.TextSentiment;
import net.matt.awsComprehendService.services.SessionDataService;
import org.springframework.stereotype.Service;

@Service
public class SessionDataServiceImp implements SessionDataService {

    private TextSentiment textSentiment;


    @Override
    public TextSentiment getTextSentiment() {
        return textSentiment;
    }

    @Override
    public void setTextSentiment(TextSentiment textSentiment) {
        this.textSentiment = textSentiment;
    }
}
