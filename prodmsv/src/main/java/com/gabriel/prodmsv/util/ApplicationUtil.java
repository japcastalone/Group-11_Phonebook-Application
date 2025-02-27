package com.gabriel.prodmsv.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ApplicationUtil {

    private final String contactServiceUrl;

    public ApplicationUtil(@Value("${service.api.endpoint}") final String contactServiceUrl){
        this.contactServiceUrl = contactServiceUrl;
    }
}
