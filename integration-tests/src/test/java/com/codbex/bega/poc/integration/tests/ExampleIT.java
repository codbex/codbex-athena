package com.codbex.bega.poc.integration.tests;

import org.eclipse.dirigible.tests.framework.ide.DatabasePerspective;
import org.eclipse.dirigible.tests.framework.ide.GitPerspective;
import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ExampleIT extends IntegrationTest {

    @Test
    void test() {
        cloneProject();

        Workbench workbench = ide.openWorkbench();
        workbench.clickPublishAll();
    }

    private void cloneProject() {
        ide.openHomePage();

        GitPerspective gitPerspective = ide.openGitPerspective();
        gitPerspective.cloneRepository(PROJECT_REPO_URL, Optional.of(gitUser), Optional.of(gitToken), Optional.of(gitBranch));
    }
}
