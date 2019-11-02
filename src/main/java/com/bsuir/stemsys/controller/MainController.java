package com.bsuir.stemsys.controller;

import com.bsuir.stemsys.Director;
import com.bsuir.stemsys.api.data.DocumentParser;
import com.bsuir.stemsys.api.data.DocumentReader;
import com.bsuir.stemsys.api.data.StemResultsWriter;
import com.bsuir.stemsys.api.service.LanguageDefinerService;
import com.bsuir.stemsys.api.service.StemmingService;
import com.bsuir.stemsys.api.service.StopwordsServiceFactory;
import com.bsuir.stemsys.data.HtmlParser;
import com.bsuir.stemsys.data.JSONStemResultsWriter;
import com.bsuir.stemsys.data.read.TextDocumentReader;
import com.bsuir.stemsys.service.FrequencyShortLanguageDefinerService;
import com.bsuir.stemsys.service.StemmingServiceImpl;
import com.bsuir.stemsys.service.StopwordsServiceFactoryImpl;
import com.bsuir.stemsys.stemmer.StemmerFactoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    private DocumentReader reader = new TextDocumentReader();
    private DocumentParser parser = new HtmlParser();
    private LanguageDefinerService languageDefinerService = new FrequencyShortLanguageDefinerService();
    private StopwordsServiceFactory stopwordsServiceFactory = new StopwordsServiceFactoryImpl();
    private StemmingService stemmingService = new StemmingServiceImpl(new StemmerFactoryImpl());
    private StemResultsWriter stemResultsWriter = new JSONStemResultsWriter();

    @FXML
    public Text chosenStemmingFilePath;
    @FXML
    public Text chosenSavingFilePath;
    @FXML
    private VBox root;

    private List<String> stemmingFilePath;
    private String savingFilePath;

    @FXML
    private void controlChoosingStemmingFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML", "*.html"));
        List<File> chosenStemmingFiles = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
        stemmingFilePath = getStemmingFilePath(chosenStemmingFiles);
    }

    @FXML
    private void controlChoosingSavingFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File savingFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        savingFilePath = savingFile.getPath();
    }

    @FXML
    private void controlPerformingStem() {
        if (stemmingFilePath == null || savingFilePath == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please, choose stemming and saving files before perform stem");

            alert.show();
        }

        Director director = new Director(reader, parser, languageDefinerService,
                stopwordsServiceFactory, stemmingService, stemResultsWriter);
        director.handleWork(stemmingFilePath, savingFilePath);
    }

    private List<String> getStemmingFilePath(List<File> chosenSavingFiles) {
        return chosenSavingFiles.stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
