package com.bsuir.stemsys.data.writer;

import com.bsuir.stemsys.api.data.StemResultsWriter;
import com.bsuir.stemsys.model.StemResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONStemResultsWriter implements StemResultsWriter {

    public void write(Map<String, List<StemResult>> results, String path) {
        JSONArray array = new JSONArray();
        for (Map.Entry<String, List<StemResult>> entry : results.entrySet()) {
            JSONArray basesArray = new JSONArray();
            for (StemResult result : entry.getValue()) {
                JSONObject object = new JSONObject();
                object.put("base", result.getBase());
                object.put("frequency", result.getFrequency());
                object.put("language", result.getLanguage().name());
                basesArray.add(object);
            }

            JSONObject fileResultObject = new JSONObject();
            fileResultObject.put("filePath", entry.getKey());
            fileResultObject.put("bases", basesArray);

            array.add(fileResultObject);
        }

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(array.toJSONString());
            writer.flush();
        } catch (IOException e) {
            throw new JSONWriterException("Error when write json model to file with name " + path, e);
        }
    }
}
