package com.example.stanfordappdesign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import net.datafaker.Faker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button applyNowButton;

    @FXML
    protected void onApplyNowButtonClick(ActionEvent event) {
        createNewApplication();
    }

    @FXML
    private Button generateApplicantsButton;

    @FXML TextField numApplicantsTextField;
    @FXML
    protected void onGenerateApplicantsButtonClick(ActionEvent event) throws IOException {
        int numApplicants = Integer.parseInt(numApplicantsTextField.getText());
        generateApplicants(numApplicants);
    }


    public void createNewApplication() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("application.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 720, 600);
            Stage applyStage = new Stage();
            applyStage.initStyle(StageStyle.UNDECORATED);
            applyStage.setScene(scene);
            applyStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void generateApplicants(int numApplicants) throws IOException {
        Faker faker = new Faker();
        Random random = new Random();

        List<Applicant> applicants = new ArrayList<>();

        for (int i = 0; i < numApplicants; i++) {
            Applicant applicant = GenerateApplicants.generateRandom(random, faker);
            applicants.add(applicant);
        }


        Stage generateStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("generate.fxml"));
        Parent root = fxmlLoader.load();

        GenerateController generateController = fxmlLoader.getController();
        generateController.createList(applicants);
        generateController.createGraph(applicants);

        Scene generateScene = new Scene(root, 720, 600);
        generateStage.initStyle(StageStyle.UNDECORATED);
        generateStage.setScene(generateScene);
        generateStage.show();
    }

}