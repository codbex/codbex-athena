package com.codbex.athena.integration.tests;

import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;
import org.junit.jupiter.api.Test;


class ExampleIT extends IntegrationTest {

    @Test
    void test() {
        openHomepage();
    }

    private void openHomepage(){
        ide.openPath("/services/web/dashboard");
        // browser.assertElementExistsByTypeAndContainsText(HtmlElementType.HEADER1, "Athena");
    }
}
