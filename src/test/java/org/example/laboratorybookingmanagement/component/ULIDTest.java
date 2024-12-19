package org.example.laboratorybookingmanagement.component;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.component.ULID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ULIDTest {
    @Autowired
    private ULID ulid;

    @Test
    void nextULID() {
        //01JFEYQA86D39APTANNJAYQW2H
        log.info(ulid.nextULID());
        //01JFEYQA86YJBYS10T8AWPMDK1
        log.info(ulid.nextULID());
        //01JFEYQA87XM0RAVSFM6NBKJ1C
        log.info(ulid.nextULID());
    }
}