package com.example.stanfordappdesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ApplicationController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private TextField countryOfOriginTextField;
    @FXML
    private CheckBox studiedInUsCheckBox;
    @FXML
    private TextField degreeTextField;
    @FXML
    private CheckBox mcatCheckBox;
    @FXML
    private TextField mcatTextField;
    @FXML
    private CheckBox prevMatriculationCheckBox;
    @FXML
    private TextField gpaTextField;
    @FXML
    private TextArea courseWorkTextArea;
    @FXML
    private TextArea lettersOfRecTextArea;
    @FXML
    private TextArea workExperienceTextArea;
    @FXML
    private TextArea essayTextArea;
    @FXML
    private TextField schoolAttendedTextField;
    @FXML
    private CheckBox firstGenCheckBox;
    @FXML
    private Button closeButton;

    @FXML
    protected void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSubmitButtonClick(ActionEvent event) throws IOException {

        try {
            Applicant applicant = processApplication();
            List<Integer> scoreList = DecisionTree.evaluate(applicant);
            if (scoreList == null) {
                showApplicationDenied(applicant);
            }
            else {
                showResultsPage(applicant, scoreList);
            }
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Input Error");
            errorAlert.setHeaderText("An error has occurred.");
            errorAlert.setContentText("MCAT Score must be an integer. GPA must be a double.");
            errorAlert.showAndWait();
        }
    }

    public Applicant processApplication() throws NumberFormatException{
        Random random = new Random();
        Applicant applicant = new Applicant();
        applicant.setName(nameTextField.getText());
        applicant.setCountryOfOrigin(countryOfOriginTextField.getText());
        applicant.setStudiedInUs(studiedInUsCheckBox.isSelected());
        applicant.setHighestDegree(degreeTextField.getText());
        applicant.setMcatTaken(mcatCheckBox.isSelected());
        applicant.setMcatScore(Integer.parseInt(mcatTextField.getText()));
        applicant.setPrevMatriculation(prevMatriculationCheckBox.isSelected());
        applicant.setGpa(Double.parseDouble(gpaTextField.getText()));
        applicant.setCoursework(GenerateApplicants.generatePSU(random));
        applicant.setLettersOfRecommendation(GenerateApplicants.generatePSU(random));
        applicant.setWorkExperience(GenerateApplicants.generatePSU(random));
        applicant.setEssay(GenerateApplicants.generatePSU(random));
        applicant.setSchoolAttended(GenerateApplicants.generateSchoolTier(random));
        applicant.setFirstGeneration(firstGenCheckBox.isSelected());
        return applicant;
    }

    private void showResultsPage(Applicant applicant, List<Integer> scoreList) throws IOException {
        Stage resultsStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
        Parent root = loader.load();

        ResultsController resultsController = loader.getController();
        resultsController.fillInformation(applicant, scoreList);

        Scene resultsScene = new Scene(root, 720, 600);
        resultsStage.initStyle(StageStyle.UNDECORATED);
        resultsStage.setScene(resultsScene);
        resultsStage.show();

    }

    public void showApplicationDenied(Applicant applicant) throws IOException {
        Stage denialStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("denied.fxml"));
        Parent root = loader.load();

        DeniedController deniedController = loader.getController();
        deniedController.getDenialMessage(applicant);

        Scene denialScene = new Scene(root, 400, 300);
        denialStage.initStyle(StageStyle.UNDECORATED);
        denialStage.setScene(denialScene);
        denialStage.show();
    }


}
