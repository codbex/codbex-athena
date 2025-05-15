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
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigurations.class})
public abstract class BaseIntegrationTest {

    private static final boolean headlessExecution = Boolean.parseBoolean(Configuration.get("selenide.headless", Boolean.TRUE.toString()));

    private static final int EXPOSED_PORT = 80;
    protected static final int RANDOM_PORT = PortUtil.getFreeRandomPort();

    private static final PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(RANDOM_PORT), new ExposedPort(EXPOSED_PORT));

    protected static GenericContainer<?> testContainer;

    @Autowired
    protected Browser browser;
    @Autowired
    protected IDE ide;

    protected abstract String getTestImage();

    protected String getStartupLogMessage() {
        return "Application has started";
    }


    // Placeholder: will be overridden per subclass
    @BeforeAll
    public static void setUpContainerStatic() {
    }

    protected static void startContainer(GenericContainer<?> container, String expectedLog) {
        container.start();
        await().atMost(60, TimeUnit.SECONDS)
               .pollInterval(2, TimeUnit.SECONDS)
               .untilAsserted(() -> {
                   String logs = container.getLogs();
                   assertThat(logs).contains(expectedLog);
               });
    }

    @BeforeEach
    final void setUpBrowser() {
        com.codeborne.selenide.Configuration.headless = headlessExecution;
    }

    @AfterAll
    public static void stopContainer() {
        if (testContainer != null) {
            testContainer.stop();
        }
    }

    protected void assertAsyncContainerLog(String expectedLog) {
        await().atMost(60, TimeUnit.SECONDS)
               .pollInterval(1, TimeUnit.SECONDS)
               .untilAsserted(() -> assertContainerLog(expectedLog));
    }

    protected void assertContainerLog(String expectedLog) {
        assertThat(testContainer.getLogs()).contains(expectedLog);
    }

    protected GenericContainer<?> createContainer(String imageName) {
        return new GenericContainer<>(imageName)
                .withExposedPorts(EXPOSED_PORT)
                .withCreateContainerCmdModifier(cmd -> cmd.withPortBindings(portBinding))
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("ContainerLogger")))
                .withReuse(false);
    }
}
