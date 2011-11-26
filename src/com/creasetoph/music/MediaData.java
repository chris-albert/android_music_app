package com.creasetoph.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaData {	
    
    private static MediaData _instance = null;
    
    private Map<String,Object> _mediaMap = new HashMap<String,Object>();
    
    public static MediaData getInstance() {
        if(_instance == null) {
            _instance = new MediaData();
        }
        return _instance;
    }
    
    public void setMap(Map<String,Object> map) {
        Logger.log(map.toString());
        _mediaMap = map;
    }
    
    public void parseJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            setMap(parseJsonObject(jsonObject));
        }catch(JSONException e) {
           Logger.log(e);
        }
    }
    
    private Map<String,Object> parseJsonObject(JSONObject json) {
        Map<String,Object> map = new HashMap<String,Object>();
        for(Iterator<?> i = json.keys(); i.hasNext();) {
            String key = i.next().toString();
            try {
                map.put(key,parseJsonObject(json.getJSONObject(key)));
            }catch(JSONException e) {
                map.put(key,"file");
            }
        }; 
        map.remove(".DS_Store");
        return map;
    }
    
    public ArrayList<String> get() {
        return mapToSortedList(_mediaMap);
    }
    
    public ArrayList<String> get(String selection) {
        Map<String,Object> map = getMap(selection);
        if(map != null) {
            return mapToSortedList(map);
        }
        return null;
    }
    
    private Map<String,Object> getMap(String selection) {
        return getMap(_mediaMap,selection);
    }
    
    @SuppressWarnings("unchecked")
    private Map<String,Object> getMap(Map<String,Object> map,String selection) {
        return (Map<String,Object>) map.get(selection);
    }
    
    private ArrayList<String> mapToSortedList(Map<String,?> map) {
        ArrayList<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list);
        return list;
    }
    
    public ArrayList<String> get(String... selection) { 
        Map<String,Object> map = null;
        for(String key: selection) {
            if(map != null) {
                map = getMap(map,key);
            }else {
                map = getMap(key);
            }
        }
        return mapToSortedList(map);
    }
}
