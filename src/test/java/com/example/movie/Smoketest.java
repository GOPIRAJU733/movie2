package com.example.movie;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Smoketest {

    @Test
    @Order(1)
    public void context(){}
}
