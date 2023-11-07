package com.example.stanfordappdesign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class GenerateController {

    @FXML
    private ListView<Applicant> applicantListView;

    @FXML
    private Button closeButton;

    @FXML
    private BarChart<String, Number> barChart;

    private XYChart.Series<String, Number> scoreSeries = new XYChart.Series<>();

    @FXML
    protected void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void createList(List<Applicant> applicants) {
        ObservableList<Applicant> applicantObservableList = FXCollections.observableArrayList(applicants);
        applicantListView.setItems(applicantObservableList);
        applicantListView.setCellFactory(param -> new ListCell<Applicant>() {
            @Override
            protected void updateItem(Applicant applicant, boolean empty) {
                super.updateItem(applicant, empty);
                if (empty || applicant == null) {
                    setText(null);
                } else {
                    Hyperlink hyperlink = new Hyperlink(applicant.getName());
                    hyperlink.setOnAction(event -> {
                        if (applicant.totalScore > 0) applicant.totalScore = 0;
                        List<Integer> scoreList = DecisionTree.evaluate(applicant);
                        try {
                            displayApplication(applicant, scoreList);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    setGraphic(hyperlink);
                }
            }
        });
    }

    private void displayApplication(Applicant applicant, List<Integer> scoreList) throws IOException {
        if (scoreList == null) {
            showApplicationDenied(applicant);
        }
        else {
            showResultsPage(applicant, scoreList);
        }
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

    public void createGraph(List<Applicant> applicants) {
        int ineligibleCount = 0;
        int less50Count = 0;
        int less65Count = 0;
        int bestCount = 0;

        for(Applicant applicant : applicants){
            DecisionTree.evaluate(applicant);
            if (applicant.totalScore == 0) {
                ineligibleCount++;
            }
            else if (applicant.totalScore < 50) {
                less50Count++;
            }
            else if (applicant.totalScore < 65) {
                less65Count++;
            }
            else {
                bestCount++;
            }
        }

        barChart.getData().clear();
        scoreSeries.getData().add(new XYChart.Data<>("Score = 0", ineligibleCount));
        scoreSeries.getData().add(new XYChart.Data<>("Score < 50", less50Count));
        scoreSeries.getData().add(new XYChart.Data<>("50 <= Score < 65", less65Count));
        scoreSeries.getData().add(new XYChart.Data<>("Score >= 65", bestCount));
        barChart.getData().add(scoreSeries);
    }



}
