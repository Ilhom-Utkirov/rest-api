package com.example.demo.controller;


import com.example.demo.Entity.SampleEntity;
import com.example.demo.utils.ApiHelper;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


@RestController
public class RestSenderController {

   @Value("${api.send-url}")
   private String apiSendUrl;

   @Value("${api.send-url-endpoint}")
   private String apiSendUrlEndpoint;

   private static Logger logger = LogManager.getLogger(ApiHelper.class);

//String json = "{"store_id":283,"apartment_id":12577,"amount":1000000,"merchant_id":18,"transaction_id":null,"type":1,"sign":null,"details":null,"transaction_time":1627776000000,"pay_method":2,"paid_for":0,"file":"","pay_commission":100000,"pay_actual":null} ";"

   @RequestMapping("/send")
   public void send (){
      ResponseEntity<String> response = null;
      try {
         response = (ResponseEntity<String>) sendRequest();
      } catch (JSONException e) {
         e.printStackTrace();
      }

      System.out.println( "Recevied response : " +
              response == null ? "error response is null" : response + "\n" +response.toString()
      );

   }

   public ResponseEntity<?> sendRequest() throws JSONException {

      SampleEntity sample = new SampleEntity("John", "Smith");

      String token = "smth very secured";

      JSONObject jsonObject = new JSONObject();

      jsonObject.put("id", 5L);
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

      //4. create RestTemplate ErrorHandler and message Convertors
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setErrorHandler(new ApiHelper.RestErrorHandler() );
      restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

      //5. send request
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class );

      //6. show in console and logs the result of the request
//      if(response.getStatusCode() != HttpStatus.OK){
//         logger.error("Failed request to '{}' with body \n '{}'", url, request);
//         logger.error("with response: STATUS '{}' and BODY \n {}", response.getStatusCode().value(), response.getBody());
//
//         System.out.println("Failed request to "+url+" with body \n "+  request);
//         System.out.println("with response: STATUS "+response.getStatusCode().value()+" with body \n "+  response.getBody());
//
//      } else {
//         logResponse(HttpMethod.POST.toString(), response.getStatusCode().toString(), response.getBody());
//
//         System.out.println("Http Post: "+HttpMethod.POST.toString()+
//                           "\n With status - " +  response.getStatusCode().toString()+
//                           "\n With body - "   +  response.getBody().toString());
//      }
      System.out.println("response : " + response);



      return response;
   }

   public static void logResponse(String method, String statusCode, String body) {
      logger.info("ApiHelper response | {} | {} | {}", method, statusCode, body);
   }

}
