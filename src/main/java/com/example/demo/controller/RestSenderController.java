package com.example.demo.controller;


import com.example.demo.Entity.SampleEntity;
import com.example.demo.utils.ApiHelper;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class RestSenderController {

   @Value("${api.send-url}")
   private String apiSendUrl;

   @Value("${api.send-url-endpoint}")
   private String apiSendUrlEndpoint;




//String json = "{"store_id":283,"apartment_id":12577,"amount":1000000,"merchant_id":18,"transaction_id":null,"type":1,"sign":null,"details":null,"transaction_time":1627776000000,"pay_method":2,"paid_for":0,"file":"","pay_commission":100000,"pay_actual":null} ";"


   public ResponseEntity<?> sendRequest() throws JSONException {

      SampleEntity sample = new SampleEntity("John", "Smith");

      String token = "smth very secured";

      JSONObject jsonObject = new JSONObject();

      jsonObject.put("id", 5);
      jsonObject.put("name", "Andy");
      jsonObject.put("surname", "Smith");


      String url = apiSendUrl + apiSendUrlEndpoint;
      String body = sample.toString();

      //1. Http
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      //2. set token to the header
      if (token != null)
         headers.set("Authorization", "Bearer " + token);

      //3. create request
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(jsonObject.toString(), headers);

      //create RestTemplate ErrorHandler
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setErrorHandler(new );



      return null;

   }

}
