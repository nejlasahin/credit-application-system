package com.project.backend.util.scoreService;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    String json = "{\n" +
            "\"success\": true,\n" +
            "\"scores\": {\n" +
            " \"2\": 550,\n" +
            " \"4\": 1000,\n" +
            "\"6\": 400,\n" +
            "\"8\": 900,\n" +
            " \"0\": 2000,\n" +
            " }\n" +
            "}\n";

    JSONObject obj = new JSONObject(json);


    public int getScore(String identityNumber) {
        int score = obj.getJSONObject("scores").getInt(String.valueOf(identityNumber.charAt(identityNumber.length() - 1)));
        return score;
    }
    public Boolean getSuccess() {
        Boolean success = obj.getBoolean("success");
        return success;
    }

}
