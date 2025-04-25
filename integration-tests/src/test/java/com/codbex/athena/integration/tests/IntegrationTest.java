package com.codbex.athena.integration.tests;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.tests.framework.browser.Browser;
import org.eclipse.dirigible.tests.framework.ide.IDE;
import org.eclipse.dirigible.tests.framework.util.PortUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigurations.class})
class IntegrationTest {

    // set config to false if you want to disable the headless mode
    private static final boolean headlessExecution = Boolean.parseBoolean(Configuration.get("selenide.headless", Boolean.TRUE.toString()));

    private static final String APP_IMAGE_TAG = System.getenv().getOrDefault("APP_IMAGE_TAG", "latest");
    private static final String APP_IMAGE = System.getenv().getOrDefault("APP_IMAGE", "ghcr.io/codbex/codbex-athena:" + APP_IMAGE_TAG);

    private static final int EXPOSED_PORT = 80;

    static final int RANDOM_PORT = PortUtil.getFreeRandomPort();

    private static final PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(RANDOM_PORT), new ExposedPort(EXPOSED_PORT));
    @Container
    protected static final GenericContainer<?> appContainer = new GenericContainer<>(APP_IMAGE)//
                                                                                               .withExposedPorts(EXPOSED_PORT)
                                                                                               .withCreateContainerCmdModifier(
                                                                                                       cmd -> cmd.withPortBindings(
                                                                                                               portBinding))
                                                                                               .withLogConsumer(new Slf4jLogConsumer(
                                                                                                       LoggerFactory.getLogger(
                                                                                                               "ContainerLogger")))
                                                                                               // ensures container is not reused between
                                                                                               // test runs
                                                                                               .withReuse(false);

    protected static final String PROJECT_REPO_URL = "https://github.com/codbex/codbex-athena.git";

    @Autowired
    protected Browser browser;

    @Autowired
    protected IDE ide;

    @BeforeAll
    public static void setUpContainer() {
        appContainer.start();
    }

    @BeforeEach
    final void setUpBrowser() {
        com.codeborne.selenide.Configuration.headless = headlessExecution;
    }

    @AfterAll
    public static void stopContainer() {
        appContainer.stop();
    }

    protected void assertAsyncContainerLog(String expectedLog) {
        await().atMost(60, TimeUnit.SECONDS)
               .pollInterval(1, TimeUnit.SECONDS)
               .untilAsserted(() -> {
                   assertContainerLog(expectedLog);
               });
    }

    protected void assertContainerLog(String expectedLog) {
        String containerLogs = appContainer.getLogs();
        assertThat(containerLogs).contains(expectedLog);
    }

}
