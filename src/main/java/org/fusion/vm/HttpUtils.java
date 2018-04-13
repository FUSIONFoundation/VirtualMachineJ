package org.fusion.vm;


import java.util.ArrayList;
import java.util.List;

public class HttpUtils {

    private List<JSON> jsons = new ArrayList<>();


    public int httpGet(String url) {
        jsons.add(getJson(url));
        return jsons.size() - 1;
    }

    public long getValue(int index, String key) {
        JSON json = jsons.get(index);
        return json.getValue(key);
    }

    private JSON getJson(String url) {
        // stub code, will replaced by the http object of mainnet code.
        return new JSON();
    }
}
