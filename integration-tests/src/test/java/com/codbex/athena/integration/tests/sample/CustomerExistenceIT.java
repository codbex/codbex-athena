package com.codbex.athena.integration.tests.sample;

import org.eclipse.dirigible.tests.framework.browser.HtmlAttribute;
import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;
import org.junit.jupiter.api.Test;

public class CustomerExistenceIT extends SampleDataIntegrationTest {
    @Test
    void testCustomerDataPopulated() {
        browser.openPath("/");
        ide.login(false);

        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Partners");
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Customers");

        browser.assertElementExistsByTypeAndText(HtmlElementType.DIV, "Betamax");
    }
}
