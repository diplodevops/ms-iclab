package com.devopsusach2020.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class RestDataTest {

    @TestConfiguration
    static class Config {
        @Bean
        public void testGetData() {

            final RestData rest = new RestData();
             rest.getData("pais");
        }
    
        @Bean
        void testGetTotalMundial() {
    
            final RestData rest = new RestData();
            rest.getTotalMundial();
    
            
        }    }    
    
  
}
