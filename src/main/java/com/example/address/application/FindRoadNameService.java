package com.example.address.application;

import com.example.address.infrastructure.OpenAPIClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Service
public class FindRoadNameService {
    private final OpenAPIClient openAPIClient;
    private final String confmKey;

    public FindRoadNameService(OpenAPIClient openAPIClient,
                               @Value("${confmKey}") String confmgKey) {
        this.openAPIClient = openAPIClient;
        this.confmKey = confmgKey;
    }

    public String findRoadName(String str) {
        String removedSpaceStr = str.replaceAll("\\p{Z}|[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
        List<String> roadNameList = new ArrayList<>();
        List<String> searchedRoadNameList = new ArrayList<>();

        findAllRoadName(removedSpaceStr, roadNameList);
        searchRoadNameByOpenApi(roadNameList, searchedRoadNameList);

        return searchedRoadNameList.get(0);
    }

    private void searchRoadNameByOpenApi(List<String> roadNameList, List<String> searchedRoadNameList) {
        for (String roadName : roadNameList) {
            String result = openAPIClient.findAddress(confmKey, roadName, "json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(result);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            JSONObject results = (JSONObject) jsonObject.get("results");
            JSONArray juso = (JSONArray) results.get("juso");
            if (juso.isEmpty()) continue;
            JSONObject jusoDetail = (JSONObject) juso.get(0);
            String searchedRoadName = (String) jusoDetail.get("rn");
            String searchedSggName = (String) jusoDetail.get("sggNm");
            searchedRoadNameList.add(searchedSggName + " " + searchedRoadName);
        }
    }

    private void findAllRoadName(String removedSpaceStr, List<String> roadNameList) {
        while (removedSpaceStr.contains("로") || removedSpaceStr.contains("길")) {
            int endIndex = removedSpaceStr.length() - 1;
            if (removedSpaceStr.contains("로")) {
                int roIdx = removedSpaceStr.indexOf("로");
                String ro = removedSpaceStr.substring(0, roIdx + 1);
                roadNameList.add(ro);
                endIndex = min(endIndex, roIdx);
            }
            if (removedSpaceStr.contains("길")) {
                int gilIdx = removedSpaceStr.indexOf("길");
                String gil = removedSpaceStr.substring(0, gilIdx + 1);
                roadNameList.add(gil);
                endIndex = min(endIndex, gilIdx);
            }

            if (endIndex < removedSpaceStr.length() - 1) {
                removedSpaceStr = removedSpaceStr.substring(endIndex + 1);
            } else {
                break;
            }
        }
    }
}
