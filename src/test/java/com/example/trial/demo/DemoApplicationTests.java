package com.example.trial.demo;

import com.example.trial.demo.services.SignatureService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SignatureService service;

	@Test
	void contextLoads() throws Exception {
		JSONObject object = new JSONObject();
		object.put("redirect_url", "http://localhost:8080");
		object.put("notify_url", "http://localhost:8080/notify");
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
		MvcResult result = mockMvc.perform(post("https://secure-dev.reddotpayment.com/service/payment-api")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(object)))
				.andReturn();
		System.out.println(object.toString());
		System.out.println(result.getResponse().getContentAsString());

	}

	@Test
	void testSignature() throws NoSuchAlgorithmException {
		String orderId = "TST102";
		String paymentType = "S";
		String amount = "100";
		String cardNo = "4761 3400 0000 0035";
		String expDate = "122022";
		String cvv2 = "846";

		System.out.println(service.createSignature(orderId, paymentType, amount, cardNo, expDate, cvv2));
	}

}
