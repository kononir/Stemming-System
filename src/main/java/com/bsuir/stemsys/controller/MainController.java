package com.bsuir.stemsys.controller;

import com.bsuir.stemsys.Director;
import com.bsuir.stemsys.api.data.DocumentParser;
import com.bsuir.stemsys.api.data.DocumentReader;
import com.bsuir.stemsys.api.data.StemResultsWriter;
import com.bsuir.stemsys.api.service.LanguageDefinerService;
import com.bsuir.stemsys.api.service.StemmingService;
import com.bsuir.stemsys.api.service.StopwordsServiceFactory;
import com.bsuir.stemsys.data.parser.HtmlParser;
import com.bsuir.stemsys.data.parser.WordsParser;
import com.bsuir.stemsys.data.writer.JSONStemResultsWriter;
import com.bsuir.stemsys.data.reader.text.TextDocumentReader;
import com.bsuir.stemsys.model.StemResult;
import com.bsuir.stemsys.service.FrequencyLanguageDefinerService;
import com.bsuir.stemsys.service.StemmingServiceImpl;
import com.bsuir.stemsys.service.stopwords.StopwordsServiceFactoryImpl;
import com.bsuir.stemsys.stemmer.StemmerFactoryImpl;
import com.bsuir.stemsys.view.ErrorAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {
    private static final String RESULTS_FXML_FILE_PATH = "view/results.fxml";
    private static final String HELP_FXML_FILE_PATH = "view/main_help.fxml";

    private DocumentReader reader = new TextDocumentReader();
    private DocumentParser parser = new HtmlParser(new WordsParser());
    private LanguageDefinerService languageDefinerService = new FrequencyLanguageDefinerService();
    private StopwordsServiceFactory stopwordsServiceFactory = new StopwordsServiceFactoryImpl();
    private StemmingService stemmingService = new StemmingServiceImpl(new StemmerFactoryImpl());
    private StemResultsWriter stemResultsWriter = new JSONStemResultsWriter();

    @FXML
    public VBox chosenStemmingFilePath;
    @FXML
    public Text chosenSavingFilePath;
    @FXML
    private VBox root;

    private List<String> stemmingFilePath;
    private String savingFilePath;

    @FXML
    private void controlPressingButton(KeyEvent event) {
        if (KeyCode.ENTER.equals(event.getCode())) {
            controlPerformingStem();
        }
    }

    @FXML
    private void controlChoosingStemmingFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML", "*.html"));
        List<File> chosenStemmingFiles = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
        if (chosenStemmingFiles != null) {
            stemmingFilePath = getStemmingFilePath(chosenStemmingFiles);

            ObservableList<Node> children = chosenStemmingFilePath.getChildren();
            children.clear();
            for (String path : stemmingFilePath) {
                children.add(new Text(path));
            }
        }
    }

    @FXML
    private void controlChoosingSavingFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File savingFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (savingFile != null) {
            savingFilePath = savingFile.getPath();
            chosenSavingFilePath.setText(savingFilePath);
        }
    }

    @FXML
    private void controlPerformingStem() {
        try {
            if (stemmingFilePath == null || savingFilePath == null) {
                new ErrorAlert().show("Please, choose stemming and saving files before perform stem");
                return;
            }

            Director director = new Director(reader, parser, languageDefinerService,
                    stopwordsServiceFactory, stemmingService, stemResultsWriter);
            Map<String, List<StemResult>> documentsStemResults = director.handleWork(stemmingFilePath, savingFilePath);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(RESULTS_FXML_FILE_PATH));
            Parent root = loader.load();

            ResultsController resultsController = loader.getController();
            resultsController.controlShowingResults(documentsStemResults, savingFilePath);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Stem results");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    private List<String> getStemmingFilePath(List<File> chosenSavingFiles) {
        return chosenSavingFiles.stream()
                .map(File::getPath)
                .collect(Collectors.toList());
    }
}
