package com.codbex.athena.integration.tests;

import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;
import org.junit.jupiter.api.Test;


class ExampleIT extends IntegrationTest {

    @Test
    void test() {
        browser.assertElementExistsByTypeAndContainsText(HtmlElementType.SPAN, "Dirigible");
        browser.assertElementExistsByTypeAndContainsText(HtmlElementType.SPAN, "Gross Profit");
    }

}
