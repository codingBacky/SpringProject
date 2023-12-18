package com.shard.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String payType;

    private int payTotal;

    private String orderName;

    private String yourSuccessUrl;
    
    private String yourFailUrl;
    
    private String imp_uid;


    public PayVO toEntity() {
        return PayVO.builder()
                .payMethod("card")
                .payTotal(payTotal)
                .payId(UUID.randomUUID().toString())
                .payComplete(0)
                .build();
    }
}
	
