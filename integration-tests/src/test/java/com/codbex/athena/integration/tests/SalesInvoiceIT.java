package com.codbex.athena.integration.tests;

import org.eclipse.dirigible.tests.framework.browser.HtmlAttribute;
import org.eclipse.dirigible.tests.framework.util.SleepUtil;
import org.junit.jupiter.api.Test;
import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;

import java.util.Map;

public class SalesInvoiceIT extends IntegrationTest {
    @Test
    void testAddingSalesInvoice() {
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Sales");
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Sales Invoice");

        SleepUtil.sleepMillis(2000);

        browser.clickElementByAttributes(HtmlElementType.BUTTON,
                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));

        // Enter text in fields

        // Now is disabled
        // browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");
    }
}
