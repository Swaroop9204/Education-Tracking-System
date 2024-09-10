package com.project.ets.requstdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {

    private String email;
    private int otp;
}
