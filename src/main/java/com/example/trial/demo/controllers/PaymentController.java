package com.example.trial.demo.controllers;

import com.example.trial.demo.pojo.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class PaymentController {

    private ApiResponse response = null;

    @PostMapping(value = "/payment")
    public String getPaymentUrl(Model model) throws IOException, JSONException {
        JSONObject object = new JSONObject();
        object.put("redirect_url", "http://localhost:8080");
        object.put("notify_url", "http://localhost:8080/notify");
        object.put("back_url", "http://localhost:8080");
        object.put("mid", "1007780388");
        object.put("order_id", "TST102");
        object.put("amount", "100");
        object.put("ccy", "SGD");
        object.put("api_mode", "redirection_sop");
        object.put("payment_type", "S");
        object.put("card_no", "4761340000000035");
        object.put("exp_date", "122022");
        object.put("cvv2", "846");
        object.put("payer_name", "Reddot Payment");
        object.put("payer_email", "email@email.com");
        object.put("signature", "12e8287f5a29fedabfde1c30fea857028ec92dd2103702f918733b53da18f0bd4bb96670c94f66c4df4a4ccb85cc4cbdafe02c2ac43836435fdf20becdbd6ac1");

        URL url = new URL("https://secure-dev.reddotpayment.com/service/payment-api");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", String.valueOf(object.toString().length()));
        connection.setUseCaches(false);
        try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            dos.writeBytes(object.toString());
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            String line;
            while ((line = br.readLine()) != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                response = objectMapper.readValue(line, ApiResponse.class);
                System.out.println(response);
            }
        }
        model.addAttribute("object", response);
        return "notify";
    }

    @GetMapping("/payment")
    public String redirectToPaymentUrl(){
        return "redirect:" + response.getPayment_url();
    }

}
