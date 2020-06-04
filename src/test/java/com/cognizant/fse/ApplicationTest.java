package com.cognizant.fse;

import com.cognizant.fse.controller.ApplicationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    ApplicationController applicationController;

    @Test
    public void contextLoad() {
        assertThat(applicationController).isNotNull();
    }

}
