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

import org.eclipse.dirigible.components.data.sources.manager.DataSourcesManager;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@ComponentScan(//
        basePackages = {"org.eclipse.dirigible.tests.framework"}, //
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "org\\.eclipse\\.dirigible\\.tests\\.framework\\.security\\..*"))
@TestConfiguration
class TestConfigurations {

    // workaround for annotation @LocalServerPort
    @Bean
    PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        Properties props = new Properties();
        props.put("local.server.port", Integer.toString(BaseIntegrationTest.RANDOM_PORT));
        configurer.setProperties(props);

        return configurer;
    }

    @Bean
    DataSourcesManager provideDataSourcesManager() {
        return Mockito.mock(DataSourcesManager.class);
    }

}
