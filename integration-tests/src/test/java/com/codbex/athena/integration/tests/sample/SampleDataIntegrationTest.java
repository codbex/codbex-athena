package com.codbex.athena.integration.tests.sample;

import com.codbex.athena.integration.tests.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeAll;

public abstract class SampleDataIntegrationTest extends BaseIntegrationTest {

    @BeforeAll
    public static void setUpContainer() {
        testContainer = new SampleDataIntegrationTest() {}.createContainer(System.getProperty("sample.image", "ghcr.io/codbex/codbex-athena-data-sample:latest"));
        startContainer(testContainer, "Application has started");
    }

    @Override
    protected String getTestImage() {
        return System.getProperty("sample.image", "ghcr.io/codbex/codbex-athena-data-sample:latest");
    }
}
