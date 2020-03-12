package net.matt.awsComprehendService.services.implementations;

import net.matt.awsComprehendService.entities.TextSentiment;
import net.matt.awsComprehendService.services.AWSdatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AWSdatabaseServiceImp implements AWSdatabaseService {

    @Value("${db_username}")
    private String userName;

    @Value("${db_password}")
    private String password;

    @Value("${db_endpoint}")
    private String awsEndpoint;

    private String SQLtemplate = "INSERT INTO text_sentiment (feedback, sentiment, positive, negative, neutral, mixed, detectedLanguage) VALUES(?, ?, ?, ?, ?, ?, ?)";

    @Override
    public String persistEvaluatedFeedback(TextSentiment textSentiment) {

        String connectionUrl = "jdbc:postgresql://" + awsEndpoint + "/postgres";
        
        try {
            Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            PreparedStatement preparedStatement = connection.prepareStatement(SQLtemplate);
            preparedStatement.setString(1, textSentiment.getText());
            preparedStatement.setString(2, textSentiment.getSentiment());
            preparedStatement.setDouble(3, textSentiment.getPositiveEstimate());
            preparedStatement.setDouble(4, textSentiment.getNegativeEstimate());
            preparedStatement.setDouble(5, textSentiment.getNeutralEstimate());
            preparedStatement.setDouble(6, textSentiment.getMixedEstimate());
            preparedStatement.setString(7, textSentiment.getDetectedLanguage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "result saved!";
    }
}
