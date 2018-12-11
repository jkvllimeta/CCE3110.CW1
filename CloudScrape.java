/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cloudscrape;

import static com.google.api.client.util.Charsets.UTF_8;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Joseph
 */
public class CloudScrape {
    
    

    
        JSONObject companyDetails = new JSONObject();
        JSONObject company = new JSONObject();
        JSONArray companyList = new JSONArray();

        JSONObject headlineDetails = new JSONObject();
        JSONObject headlineObject = new JSONObject();
        JSONArray headlineList = new JSONArray();
        
        
        
        JSONObject headlineDetails2 = new JSONObject();
        JSONObject headlineObject2 = new JSONObject();
        JSONArray headlineList2 = new JSONArray();
        
        

    
    public static void main(String args[]) throws Exception {
        
    CloudScrape cs = new CloudScrape();
    
    String stockSite = "https://finance.yahoo.com/trending-tickers";
    String stockFile = "companylisttest.json";
    
    String newsSite = "https://www.wsj.com/europe";
    
    String newsSite2 = "https://www.washingtonpost.com/";
    
    upload("cloudscrape", "testjson", "a simple blob");
    
    cs.headlineScrape1(newsSite);
    cs.headlineScrape2(newsSite2);
    cs.stockScrape(stockSite, stockFile);
    
    // download and display
    System.out.println(download("cloudscrape", "testjson"));
    
    }

public static void upload(String bucket, String object, String text) throws IOException {
Storage storage = StorageOptions.newBuilder().
setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("gcloud-credentials.json"))).build().getService();

// Upload a blob to the newly created bucket
    BlobId blobId = BlobId.of(bucket , object);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
    Blob blob = storage.create(blobInfo , text.getBytes(UTF_8));

}

public static String download(String bucket, String object) throws IOException{

Storage storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.
fromStream(new FileInputStream("gcloud-credentials.json"))).build().getService();

// Upload a blob to the newly created bucket
        BlobId blobId = BlobId.of(bucket, object);
        byte[] content = storage.readAllBytes(blobId);
        String contentString = new String(content, UTF_8);
    return contentString;
}

public void headlineScrape1 (String newsSite){
            try{
            Document newsConnect = Jsoup.connect(newsSite).get();
            
            Elements newsElements = newsConnect.select("h3.wsj-headline").select("a");
            
            
            for (Element headline : newsElements){
                
                //System.out.println(headline.text());
                //System.out.println(headline.attr("href"));
                
                headlineDetails = new JSONObject();
                headlineDetails.put("Title", headline.text());
                headlineDetails.put("Link", headline.attr("href"));
                
                headlineObject = new JSONObject();
                headlineObject.put("Headline", headlineDetails);
                
                System.out.println(headlineObject);
                headlineList.add(headlineObject);

            }
        
        //JSONObject masterCompany = new JSONObject();
        //masterCompany.put("Company List", companyList);
        
        FileWriter file = new FileWriter("headlinesWSJ.json");
        file.write(headlineList.toJSONString());
            System.out.println("File successfully created");
        file.flush();
        
        upload("cloudscrape", "WSJjson", headlineList.toJSONString());
            
        } catch (Exception e){
            e.printStackTrace();
        }

}

public void headlineScrape2 (String newsSite2) {
        
    
        try{
            Document newsConnect = Jsoup.connect(newsSite2).get();
            
            Elements newsElements = newsConnect.select("div.headline").select("a");
            
            
            for (Element headline : newsElements){
                
                System.out.println(headline.text());
                System.out.println(headline.attr("href"));
                
                headlineDetails2 = new JSONObject();
                headlineDetails2.put("Title", headline.text());
                headlineDetails2.put("Link", headline.attr("href"));
                
                headlineObject2 = new JSONObject();
                headlineObject2.put("Headline", headlineDetails2);
                
                //System.out.println(headlineObject2);
                headlineList2.add(headlineObject2);

            }
        
        //JSONObject masterCompany = new JSONObject();
        //masterCompany.put("Company List", companyList);
        
        
        FileWriter file = new FileWriter("headlinesWaPo.json");
        file.write(headlineList2.toJSONString());
            System.out.println("File successfully created");
        file.flush();
        
        upload("cloudscrape", "WaPojson", headlineList2.toJSONString());
        
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }

public void stockScrape(String sLink, String sFile) {
    
        try{
        Document stockTable = Jsoup.connect(sLink).get();

        Elements stockElements = stockTable.getElementsByTag("table").select("tr");
   
        for (Element stockEle : stockElements){
            String symbol = stockEle.select("td.data-col0").text();
            String name = stockEle.select("td.data-col1").text();
            String lastPrice = stockEle.select("td.data-col2").text();
            String marketTime = stockEle.select("td.data-col3").text();
            String change = stockEle.select("td.data-col4").text();
            String changeVol = stockEle.select("td.data-col5").text();
            String avgVol = stockEle.select("td.data-col6").text();
            String marketCap = stockEle.select("td.data-col7").text();
            String intraday = stockEle.select("td.data-col8").text();
            
            /*
            csvPrint.println(symbol + " " + name + "    " + lastPrice + "   " + "   " + marketTime 
                                + " " + change + "  " + changeVol + "   " + avgVol + "  " + marketCap 
                                + " " + intraday + "    " + fiftyTwoWeek);
            */
            
            companyDetails = new JSONObject();
            
            companyDetails.put("Symbol", symbol);
            companyDetails.put("Name", name);
            companyDetails.put("LastPrice", lastPrice);
            companyDetails.put("MarketTime", marketTime);
            companyDetails.put("ChangePercent", change);
            companyDetails.put("ChangeVolume", changeVol);
            companyDetails.put("AverageVolume", avgVol);
            companyDetails.put("MarketCap", marketCap);
            companyDetails.put("IntradayLowHigh", intraday);

            company = new JSONObject();
            company.put("Company", companyDetails);
            
            System.out.println(company);
            
            companyList.add(company);
             
        }
        
        
        JSONObject masterCompany = new JSONObject();
        masterCompany.put("CompanyList", companyList);
        
        FileWriter file = new FileWriter(sFile);
        file.write(companyList.toJSONString());
        file.flush();
        
        upload("cloudscrape", "stocksjson", companyList.toJSONString());

        } catch (Exception e){
            e.printStackTrace();

        }
    
}

}

