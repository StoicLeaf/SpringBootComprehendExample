package net.matt.awsComprehendService.entities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TextSentiment implements Serializable {

    private String text;
    private String sentiment;
    private double positiveEstimate;
    private double negativeEstimate;
    private double neutralEstimate;
    private double mixedEstimate;

    public TextSentiment(){

    }

    public TextSentiment(String text, String jsonString){
        this.text = text;

        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        this.sentiment = jsonObject.get("Sentiment").getAsString();

        JsonObject sentimentJson = jsonObject.get("SentimentScore").getAsJsonObject();

        Double percentage = Double.valueOf(sentimentJson.get("Positive").getAsString());
        BigDecimal bd = new BigDecimal(percentage).setScale(4, RoundingMode.HALF_UP);
        this.positiveEstimate = bd.doubleValue();

        percentage = Double.valueOf(sentimentJson.get("Negative").getAsString());
        bd = new BigDecimal(percentage).setScale(4, RoundingMode.HALF_UP);
        this.negativeEstimate = bd.doubleValue();

        percentage = Double.valueOf(sentimentJson.get("Neutral").getAsString());
        bd = new BigDecimal(percentage).setScale(4, RoundingMode.HALF_UP);
        this.neutralEstimate = bd.doubleValue();

        percentage = Double.valueOf(sentimentJson.get("Mixed").getAsString());
        bd = new BigDecimal(percentage).setScale(4, RoundingMode.HALF_UP);
        this.mixedEstimate = bd.doubleValue();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public double getPositiveEstimate() {
        return positiveEstimate;
    }

    public void setPositiveEstimate(double positiveEstimate) {
        this.positiveEstimate = positiveEstimate;
    }

    public double getNegativeEstimate() {
        return negativeEstimate;
    }

    public void setNegativeEstimate(double negativeEstimate) {
        this.negativeEstimate = negativeEstimate;
    }

    public double getNeutralEstimate() {
        return neutralEstimate;
    }

    public void setNeutralEstimate(double neutralEstimate) {
        this.neutralEstimate = neutralEstimate;
    }

    public double getMixedEstimate() {
        return mixedEstimate;
    }

    public void setMixedEstimate(double mixedEstimate) {
        this.mixedEstimate = mixedEstimate;
    }

    @Override
    public String toString() {
        return "TextSentiment{" +
                "text='" + text + '\'' +
                ", sentiment='" + sentiment + '\'' +
                ", positiveEstimate=" + positiveEstimate +
                ", negativeEstimate=" + negativeEstimate +
                ", neutralEstimate=" + neutralEstimate +
                ", mixedEstimate=" + mixedEstimate +
                '}';
    }
}
