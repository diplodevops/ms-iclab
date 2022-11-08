package com.devopsusach2020.rest;

import org.junit.jupiter.api.Test;

public class RestDataTest {
    @Test
    void testGetData() {

        final RestData rest = new RestData();
         rest.getData("pais");
    }

    @Test
    void testGetTotalPais() {

        final RestData rest = new RestData();
        rest.getTotalPais("pais");

        
    }
}
