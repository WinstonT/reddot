package com.example.trial.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private String created_timestamp;

    private String expired_timestamp;

    private String mid;

    private String order_id;

    private String transaction_id;

    private String payment_url;

    private String response_code;

    private String response_msg;

    private String signature;
}
