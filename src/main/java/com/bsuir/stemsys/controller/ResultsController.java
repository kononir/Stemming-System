package com.bsuir.stemsys.controller;

import com.bsuir.stemsys.model.StemResult;
import com.bsuir.stemsys.view.ErrorAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultsController {
    private static final String HELP_FXML_FILE_PATH = "view/results_help.fxml";

    @FXML
    public VBox resultsVBox;
    @FXML
    public Text savingFileText;

    public void controlShowingResults(Map<String, List<StemResult>> documentsStemResults, String savingFilePath) {
        savingFileText.setText(savingFilePath);
        savingFileText.setOnMouseClicked(event -> openFile(savingFilePath));

        for (Map.Entry<String, List<StemResult>> entry : documentsStemResults.entrySet()) {
            Text documentPathText = new Text(entry.getKey());
            documentPathText.setId("active-link");
            documentPathText.setOnMouseClicked(event -> openFile(entry.getKey()));

            VBox documentResultsVBox = new VBox();
            documentResultsVBox.setId("document-results");
            for (StemResult stemResult : entry.getValue()) {
                Text baseText = new Text(stemResult.getBase());
                Text languageText = new Text(stemResult.getLanguage().name());
                Text frequencyText = new Text(String.valueOf(stemResult.getFrequency()));

                VBox resultVBox = new VBox(baseText, languageText, frequencyText);
                documentResultsVBox.getChildren().add(resultVBox);
            }

            VBox documentVBox = new VBox(documentPathText, documentResultsVBox);
            documentVBox.setId("document");
            resultsVBox.getChildren().add(documentVBox);
        }
    }

    private void openFile(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            new ErrorAlert().show("Unable to open file");
        }
    }

    @FXML
    private void controlShowingHelp() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(HELP_FXML_FILE_PATH));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Help");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
