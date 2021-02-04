package com.example.youtube.controller;

import java.io.IOException;
import java.util.List;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YoutubeApiTestController {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final com.google.api.client.json.JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String API_KEY = "AIzaSyBQ333HQ0n75Ab6FAM53Y8Z-7UwJE45yAQ";
    
    @GetMapping("index")
    public String index() throws IOException {
        YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer(){
            public void initialize(HttpRequest request) throws IOException{
            }
        }).setApplicationName("youtube-cmdline-serch-sample").build();
    
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(API_KEY);
    
        search.setQ("エンジニア");
        search.setType("video");
        search.setOrder("viewCount");
        search.setMaxResults((long) 10);
        search.setFields("items");
    
        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        for(SearchResult searchResult : searchResultList){
            System.out.println(searchResult.toString());
        }   
        return "index";
    }
}