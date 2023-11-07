package com.example.stanfordappdesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeniedController {
    @FXML
    private Button closeButton;

    @FXML
    private Label denialLabel;

    @FXML
    protected void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void getDenialMessage(Applicant applicant) {
        String denialMessage = "";

        if (!applicant.getCountryOfOrigin().equals("United States")) {
            if (!applicant.isStudiedInUs()) {
                denialMessage = "Applicant has not studied at least one year in the United States.";
            }
        }
        // check degree
        if (applicant.getHighestDegree().equals("Doctorate") ||
                applicant.getHighestDegree().equals("Masters") ||
                applicant.getHighestDegree().equals("Bachelors")) {
            if (!applicant.isMcatTaken()) {
                denialMessage = "Applicant has not taken the MCAT exam.";
            }

            // check previous matriculation
            else if (applicant.isPrevMatriculation()) {
                denialMessage = "Applicant has previously attended medical school.";
            }
        }
        else {
            denialMessage = "Applicant does not possess at least a Bachelors degree.";
        }


        denialLabel.setText(denialMessage);

    }
}
