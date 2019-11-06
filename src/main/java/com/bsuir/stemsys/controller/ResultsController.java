package com.bsuir.stemsys.controller;

import com.bsuir.stemsys.model.StemResult;
import com.bsuir.stemsys.view.ErrorAlert;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultsController {
    @FXML
    public VBox resultsVBox;
    @FXML
    public Text savingFileText;

    public void controlShowingResults(Map<String, List<StemResult>> documentsStemResults, String savingFilePath) {
        savingFileText.setText(savingFilePath);
        savingFileText.setOnMouseClicked(event -> openFile(savingFilePath));

        for (Map.Entry<String, List<StemResult>> entry : documentsStemResults.entrySet()) {
            Text documentPathText = new Text(entry.getKey());
            documentPathText.setOnMouseClicked(event -> openFile(entry.getKey()));
            VBox documentResultsVBox = new VBox();
            for (StemResult stemResult : entry.getValue()) {
                Text baseText = new Text(stemResult.getBase());
                Text languageText = new Text(stemResult.getLanguage().name());
                Text frequencyText = new Text(String.valueOf(stemResult.getFrequency()));
                documentResultsVBox.getChildren().addAll(baseText, languageText, frequencyText);
            }
            VBox resultVBox = new VBox(documentPathText, documentResultsVBox);
            resultsVBox.getChildren().add(resultVBox);
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

    }
}
