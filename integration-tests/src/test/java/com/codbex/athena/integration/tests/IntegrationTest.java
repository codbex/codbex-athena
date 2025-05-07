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
abstract class IntegrationTest {

    private static final boolean headlessExecution = Boolean.parseBoolean(Configuration.get("selenide.headless", Boolean.TRUE.toString()));

    private static final String TEST_IMAGE = Configuration.get("app.image", "codbex-athena:test");

    private static final int EXPOSED_PORT = 80;
    static final int RANDOM_PORT = PortUtil.getFreeRandomPort();

    private static final PortBinding portBinding = new PortBinding(
            Ports.Binding.bindPort(RANDOM_PORT),
            new ExposedPort(EXPOSED_PORT));

    static {
        System.setProperty("TESTCONTAINERS_DOCKER_IMAGE_PULL_POLICY", "never");
    }

    @Container
    protected static final GenericContainer<?> testContainer = new GenericContainer<>(TEST_IMAGE)
            .withExposedPorts(EXPOSED_PORT)
            .withCreateContainerCmdModifier(cmd -> cmd.withPortBindings(portBinding))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("ContainerLogger")))
            .withReuse(false);

    @Autowired
    protected Browser browser;
    @Autowired
    protected IDE ide;

    @BeforeAll
    public static void setUpContainer() {
        testContainer.start();

        await().atMost(60, TimeUnit.SECONDS)
               .pollInterval(2, TimeUnit.SECONDS)
               .untilAsserted(() -> {
                   String logs = testContainer.getLogs();
                   assertThat(logs).contains("Application has started");
               });

        System.setProperty("selenide.baseUrl",
                "http://" + testContainer.getHost() + ":" + RANDOM_PORT);
    }

    @BeforeEach
    final void setUpBrowser() {
        com.codeborne.selenide.Configuration.headless = headlessExecution;
        browser.openPath("/");
        ide.login(false);
    }

    @AfterAll
    public static void stopContainer() {
        testContainer.stop();
    }

    protected void assertAsyncContainerLog(String expectedLog) {
        await().atMost(60, TimeUnit.SECONDS)
               .pollInterval(1, TimeUnit.SECONDS)
               .untilAsserted(() -> assertContainerLog(expectedLog));
    }

    protected void assertContainerLog(String expectedLog) {
        assertThat(testContainer.getLogs()).contains(expectedLog);
    }
}