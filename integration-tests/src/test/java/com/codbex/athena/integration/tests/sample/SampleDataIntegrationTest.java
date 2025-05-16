package com.codbex.athena.integration.tests.sample;

import com.codbex.athena.integration.tests.BaseIntegrationTest;
import org.eclipse.dirigible.commons.config.Configuration;

abstract class SampleDataIntegrationTest extends BaseIntegrationTest {

    @Override
    protected final String getTestContainerImage() {
        return Configuration.get("sample.image", "ghcr.io/codbex/codbex-athena-data-sample:latest");
    }

}
