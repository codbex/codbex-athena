package com.codbex.athena.integration.tests;

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
        props.put("local.server.port", Integer.toString(IntegrationTest.RANDOM_PORT));
        configurer.setProperties(props);

        return configurer;
    }

    @Bean
    DataSourcesManager provideDataSourcesManager() {
        return Mockito.mock(DataSourcesManager.class);
    }

}
