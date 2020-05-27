package org.devops

/*
   ##################################################
   #                                                #
   #         Code Scanner shared library            #
   #                                                #
   ##################################################
*/

// SonarQube code scanner
def SonarScan(projectKey, projectVersion, javaVersion, modules, branchName = 'master') {
    try {
        def SONAR_HOME = tool "SonarScanner"
        withSonarQubeEnv("Sonar") {
            sh """${SONAR_HOME}/bin/sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.projectName=${projectKey} -Dsonar.projectVersion=${projectVersion} -Dsonar.language=java -Dsonar.sourceEncoding=UTF-8 -Dsonar.java.source=${javaVersion} -Dsonar.sources=src/main/java -Dsonar.java.binaries=target/classes -Dsonar.modules=${modules} -Dsonar.branch.name=${branchName} -X"""
        }
        sleep(20)
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            currentBuild.description = "代码扫描失败！"
            error "未通过SonarQube代码扫描，请及时修改!"
        }
    } catch (e) {
        currentBuild.description = "代码扫描失败！"
        error "未通过SonarQube代码扫描，请及时修改!"
    }
}
