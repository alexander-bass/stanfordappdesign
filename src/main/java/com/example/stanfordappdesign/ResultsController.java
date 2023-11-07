package com.example.stanfordappdesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class ResultsController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label mcatScoreLabel;
    @FXML
    private Label mcatPointsLabel;
    @FXML
    private Label gpaLabel;
    @FXML
    private Label gpaPointsLabel;
    @FXML
    private Label courseworkLabel;
    @FXML
    private Label courseworkPointsLabel;
    @FXML
    private Label lettersLabel;
    @FXML
    private Label lettersPointsLabel;
    @FXML
    private Label workExpLabel;
    @FXML
    private Label workExpPointsLabel;
    @FXML
    private Label essayLabel;
    @FXML
    private Label essayPointsLabel;
    @FXML
    private Label degreeLabel;
    @FXML
    private Label degreePointsLabel;
    @FXML
    private Label schoolLabel;
    @FXML
    private Label schoolPointsLabel;
    @FXML
    private Label firstGenLabel;
    @FXML
    private Label firstGenPointsLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label finalDecisionLabel;
    @FXML
    private Button closeButton;

    @FXML
    protected void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void fillInformation(Applicant applicant, List<Integer> scoreList) {
        nameLabel.setText(applicant.getName());
        mcatScoreLabel.setText("Mcat Score: " + applicant.getMcatScore());
        mcatPointsLabel.setText("Points Received: " + scoreList.get(0) + "/20");
        gpaLabel.setText("GPA: " + applicant.getGpa());
        gpaPointsLabel.setText("Points Received: " + scoreList.get(1) + "/20");
        courseworkLabel.setText("Coursework: " + applicant.getCoursework());
        courseworkPointsLabel.setText("Points Received: " + scoreList.get(2) + "/10");
        lettersLabel.setText("Letters: " + applicant.getLettersOfRecommendation());
        lettersPointsLabel.setText("Points Received: " + scoreList.get(3) + "/10");
        workExpLabel.setText("Work Experience: " + applicant.getWorkExperience());
        workExpPointsLabel.setText("Points Received: " + scoreList.get(4) + "/10");
        essayLabel.setText("Essay: " + applicant.getEssay());
        essayPointsLabel.setText("Points Received: " + scoreList.get(5) + "/10");
        degreeLabel.setText("Degree: " + applicant.getHighestDegree());
        degreePointsLabel.setText("Points Received: " + scoreList.get(6) + "/10");
        schoolLabel.setText("School: " + applicant.getSchoolAttended());
        schoolPointsLabel.setText("Points Received: " + scoreList.get(7) + "/5");
        firstGenLabel.setText("First Generation: " + applicant.isFirstGeneration());
        firstGenPointsLabel.setText("Points Received: " + scoreList.get(8) + "/5");
        totalLabel.setText("Total: " + applicant.totalScore + "/100");

        if (applicant.totalScore >= 65) {
            finalDecisionLabel.setText("Congratulations! Your application has been selected for potential admission");
        }
        else if (applicant.totalScore >= 50) {
            finalDecisionLabel.setText("You have been added to the wait list.");
        }
        else {
            finalDecisionLabel.setText("Your application does not meet the minimum requirements.");
        }
    }




}
