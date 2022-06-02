package com.example.trial.demo.services;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SignatureService {

    private static final String mid = "1007780388";

    private static final String ccy = "SGD";

    private static final String secretKey = "HijVoDF8DJi4KkCjHsjAanO6dNIyNWmzRC1A6UgqJTNRASLUVr0VHDqiFW5KvO53tH2fJVNJx47KyVIwZPTGqkE7oxLNMsSEuu1CF1C0kcoP67v4HErBvGUwjvmDgpP6";

    public String createSignature(String orderId, String paymentType, String amount, String cardNo, String expDate, String cvv2) throws NoSuchAlgorithmException {
        String aggregateStr = "";
        aggregateStr = aggregateStr + mid.trim();
        aggregateStr = aggregateStr + orderId.trim();
        aggregateStr = aggregateStr + paymentType.trim();
        aggregateStr = aggregateStr + amount.trim();
        aggregateStr = aggregateStr + ccy.trim();
        cardNo = cardNo.replaceAll(" ", "");
        if(!cardNo.equals("")){
            aggregateStr = aggregateStr + cardNo.substring(0, 6) + cardNo.substring(cardNo.length() - 4);
            aggregateStr = aggregateStr + expDate;
            aggregateStr = aggregateStr + cvv2.charAt(2);
        }

        aggregateStr = aggregateStr + secretKey;
        System.out.println(aggregateStr);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] digested = messageDigest.digest(aggregateStr.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < digested.length; i++){
            stringBuilder.append(Integer.toString((digested[i] & 0xff) + 0x100, 16).substring(1));
        }
        String signature = stringBuilder.toString();
        return signature;
    }
}
