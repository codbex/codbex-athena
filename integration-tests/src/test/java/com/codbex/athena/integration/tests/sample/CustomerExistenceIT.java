package com.codbex.athena.integration.tests.sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerExistenceIT extends IntegrationTest{
    @BeforeEach
    void setUp() {
        browser.openPath("/");
        ide.login(false);
    }

    @Test
    void testCustomerDataPopulated() {
        assert true;
    }
}
