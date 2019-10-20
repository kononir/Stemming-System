package com.bsuir.stemsys.controller;

import com.bsuir.stemsys.Director;
import com.bsuir.stemsys.api.DocumentParser;
import com.bsuir.stemsys.api.DocumentReader;
import com.bsuir.stemsys.data.HtmlParser;
import com.bsuir.stemsys.data.TextDocumentReader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {
    @FXML
    public Text chosenStemmingFilePath;
    @FXML
    public Text chosenSavingFilePath;
    @FXML
    private VBox root;

    private File stemmingFile;
    private File savingFile;

    @FXML
    private void controlChoosingStemmingFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML", "*.html"));
        stemmingFile = fileChooser.showOpenDialog(root.getScene().getWindow());
    }

    @FXML
    private void controlChoosingSavingFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        savingFile = fileChooser.showSaveDialog(root.getScene().getWindow());
    }

    @FXML
    private void controlPerformingStem() {
        if (stemmingFile == null || savingFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please, choose stemming and saving files before perform stem");

            alert.show();
        }

        Director director = new Director();
        director.handleWork();
    }
}
