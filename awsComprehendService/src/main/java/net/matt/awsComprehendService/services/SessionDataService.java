package net.matt.awsComprehendService.services;

import net.matt.awsComprehendService.entities.TextSentiment;

public interface SessionDataService {

    TextSentiment getTextSentiment();

    void setTextSentiment(TextSentiment textSentiment);
}
