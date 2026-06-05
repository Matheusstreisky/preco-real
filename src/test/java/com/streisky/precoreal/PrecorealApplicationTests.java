package com.streisky.precoreal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class PrecorealApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main_doesNotThrow() {
        assertThatNoException().isThrownBy(() -> PrecorealApplication.main(new String[]{}));
    }

}
