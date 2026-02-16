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
package com.codbex.athena.integration.tests.application;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.eclipse.dirigible.tests.framework.browser.HtmlElementType;
import org.eclipse.dirigible.tests.framework.browser.HtmlAttribute;
import org.junit.jupiter.api.Test;

import java.util.Map;

class SalesInvoiceIT extends ApplicationIntegrationTest {

    @Test
    void testAddingSalesInvoice() {
        browser.openPath("/");
        ide.login(false);

        createCity();

        createCustomer();

        createEmployee();

        createSalesInvoice();
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

    private void createCustomer() {
        navigateCreate("Partners", "Customers");

        browser.enterTextInElementById("idName", "CustomerA");
        browser.enterTextInElementByAttributePattern("textarea", "id", "idAddress", "somewhere");
        browser.enterTextInElementById("idCountry", "Italy");
        browser.enterTextInElementById("idEmail", "customera@mail.com");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");

        browser.assertElementExistsByTypeAndText("p", "Customer successfully created");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
    }

    private void navigateCreate(String navMenu, String navItem) {
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                navMenu);
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.SPAN, HtmlAttribute.CLASS, "fd-list__navigation-item-text",
                navItem);
        browser.clickElementByAttributes(HtmlElementType.BUTTON,
                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));
    }

    private void createSalesInvoice() {
        navigateCreate("Sales", "Sales Invoice");

        browser.enterTextInElementById("idCustomer", "CustomerA");
        enterDateById("idDate", "01012001");
        enterDateById("idDue", "01022001");
        browser.enterTextInElementById("idStatus", "New");
        browser.enterTextInElementById("idCurrency", "BGN");
        browser.enterTextInElementById("idOperator", "EmployeeA");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");

        browser.assertElementExistsByTypeAndText("p", "SalesInvoice successfully created");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
    }

    private void createEmployee() {
        browser.clickOnElementByAttributePattern("i", "svg-path", "/services/web/codbex-navigation-groups/Employees/employees.svg");
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.LI, HtmlAttribute.CLASS, "fd-list__navigation-item", "Employees");

        browser.clickElementByAttributes(HtmlElementType.BUTTON,
                Map.of(HtmlAttribute.GLYPH, "sap-icon--add", HtmlAttribute.CLASS, "fd-button fd-button--compact fd-button--transparent"));

        browser.enterTextInElementById("idFirstName", "EmployeeA");
        browser.enterTextInElementById("idLastName", "EmployeeA");
        browser.enterTextInElementById("idEmail", "employeea@mail.com");
        enterDateById("idBirthDate", "01012001");
        browser.enterTextInElementById("idIBAN", "IE64IRCE92050112345678");
        browser.enterTextInElementById("idPersonalNumber", "1234567899");
        browser.enterTextInElementById("idNationality", "Italy");
        browser.enterTextInElementById("idGender", "Male");
        browser.enterTextInElementById("idMartialStatus", "Single");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Create");

        browser.assertElementExistsByTypeAndText("p", "Employee successfully created");
        browser.clickOnElementWithText(HtmlElementType.BUTTON, "Close");
    }

    private void enterDateById(String elementId, String date) {
        SelenideElement dateInput = Selenide.$(Selectors.byId(elementId));
        dateInput.shouldBe(Condition.visible);
        dateInput.setValue(date);
    }

}
