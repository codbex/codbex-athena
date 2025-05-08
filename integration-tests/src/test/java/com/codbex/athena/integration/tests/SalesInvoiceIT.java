/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.athena.integration.tests;

import org.eclipse.dirigible.tests.framework.browser.HtmlAttribute;
import org.junit.jupiter.api.Test;
import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;

import java.util.Map;

public class SalesInvoiceIT extends IntegrationTest {
    @Test
    void testAddingSalesInvoice() {
        createCity();
        ide.reload();
        createCustomer();
        ide.reload();
//        createEmployee();
//        ide.reload();
//        createSalesInvoice();
    }

//    private void createSalesInvoice() {
//        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
//                "Sales");
//        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
//                "Sales Invoice");
//
//        browser.clickElementByAttributes(HtmlElementType.BUTTON,
//                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));
//
//        browser.enterTextInElementById("idCustomer", "CustomerA");
//        browser.enterTextInElementById("idDate", "01012001");
//        browser.enterTextInElementById("idDue", "01022001");
//        browser.enterTextInElementById("idCurrency", "BGN");
//        browser.enterTextInElementById("idStatus", "New");
//        browser.enterTextInElementById("idOperator", "EmployeeA");
//        browser.enterTextInElementById("idNationality", "Italy");
//        browser.enterTextInElementById("idMartialStatus", "Single");
//        browser.enterTextInElementById("idIBAN", "IE64IRCE92050112345678");
//        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");
//
//        browser.assertElementExistsByTypeAndText("p", "SalesInvoice successfully created");
//        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
//    }

//    private void createEmployee() {
//        browser.clickOnElementByAttributePattern("i", "svg-path", "/services/web/codbex-navigation-groups/Employees/employees.svg");
//        browser.clickOnElementByAttributePatternAndText(HtmlElementType.LI, HtmlAttribute.CLASS, "fd-list__navigation-item", "Employees");
//
//        browser.clickElementByAttributes(HtmlElementType.BUTTON,
//                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));
//
//        browser.enterTextInElementById("idFirstName", "EmployeeA");
//        browser.enterTextInElementById("idLastName", "EmployeeA");
//        browser.enterTextInElementById("idEmail", "employeea@mail.com");
//        browser.enterTextInElementById("idBirthDate", "01012001");
//        browser.enterTextInElementById("idPersonalNumber", "1234567899");
//        browser.enterTextInElementById("idGender", "Male");
//        browser.enterTextInElementById("idNationality", "Italy");
//        browser.enterTextInElementById("idMartialStatus", "Single");
//        browser.enterTextInElementById("idIBAN", "IE64IRCE92050112345678");
//        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");
//
//        browser.assertElementExistsByTypeAndText("p", "Employee successfully created");
//        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
//    }

    private void createCustomer() {
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Partners");
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Customers");

        browser.clickElementByAttributes(HtmlElementType.BUTTON,
                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));

        browser.enterTextInElementById("idName", "CustomerA");
        browser.enterTextInElementByAttributePattern("textarea", "id", "idAddress", "somewhere");
        browser.enterTextInElementById("idCountry", "Italy");
        browser.enterTextInElementById("idEmail", "customera@mail.com");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");

        browser.assertElementExistsByTypeAndText("p", "Customer successfully created");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
    }

    private void createCity() {
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                "Settings");
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__title", "City");

        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");

        browser.enterTextInElementById("idName", "Rome");
        browser.enterTextInElementById("idCountry", "Italy");

        browser.clickOnElementByAttributePatternAndText(HtmlElementType.BUTTON, HtmlAttribute.CLASS, "fd-button fd-button--emphasized",
                "Create");

        browser.assertElementExistsByTypeAndText("p", "City successfully created");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");

    }
}
