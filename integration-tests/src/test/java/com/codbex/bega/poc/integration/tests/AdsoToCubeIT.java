package com.codbex.bega.poc.integration.tests;

import org.eclipse.dirigible.tests.framework.ide.DatabasePerspective;
import org.eclipse.dirigible.tests.framework.ide.GitPerspective;
import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class AdsoToCubeIT extends IntegrationTest {

    public static final String ADSO_TABLE_NAME = "ZSD_D21";
    public static final String CUBE_TABLE_NAME = "ZSD_C32";
    public static final int EXPECTED_RECORDS = 913;

    @Test
    void test() {
        cloneProject();

        Workbench workbench = ide.openWorkbench();
        workbench.clickPublishAll();
        assertProjectBuildScriptExecuted();

        assertCamelRouteExecution();
    }

    private void cloneProject() {
        ide.openHomePage();

        GitPerspective gitPerspective = ide.openGitPerspective();
        gitPerspective.cloneRepository(PROJECT_REPO_URL, Optional.of(gitUser), Optional.of(gitToken), Optional.of(gitBranch));
    }

    private void assertProjectBuildScriptExecuted() {
        assertAsyncContainerLog("Build script completed!");
    }

    private void assertCamelRouteExecution() {
        assertAsyncContainerLog("CamelRouteLogger - Execution has completed");

        assertTableRecords(ADSO_TABLE_NAME, EXPECTED_RECORDS);
        assertTableRecords(CUBE_TABLE_NAME, EXPECTED_RECORDS);
    }

    private void assertTableRecords(String tableName, int expectedRecords) {
        DatabasePerspective databasePerspective = ide.openDatabasePerspective();
        databasePerspective.executeSql("SELECT COUNT(*) as COUNT FROM " + tableName);
        databasePerspective.assertHasColumn("COUNT");
        databasePerspective.assertCellContent(Integer.toString(expectedRecords));
    }

}
