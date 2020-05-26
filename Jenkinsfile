def logger = new org.devops.logger()
def notify = new org.devops.notify()
def codescan = new org.devops.codescan()

pipeline {
    agent any
    environment {
        MAVEN_HOME = tool "maven-3.6.3"
    }
    stages {
        stage('Get Code') {
            steps {
                script {
                    logger.Info("============Get Code============")
                    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: '5bd700e3-d180-44d4-a0ab-49c08bdf2ae2', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'https://10.28.1.160/svn/sourcecode/platform/code/groupama-microservice-v2']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
                }
            }
        }
        stage('Code Build') {
            steps {
                script {
                    logger.Info("============Code Build============")
                    sh "${MAVEN_HOME}/bin/mvn clean install"
                }
            }
        }
        stage('Code Scan') {
            steps {
                script {
                    logger.Info("============Code Scan============")
                    codescan.SonarScan("groupama-microservice-v2", "1.0", "1.8", "groupama-admin,groupama-oauth2, groupama-user-service,groupama-zipkin,groupama-gateway,groupama-common")
                }
            }
        }
    }
    post {
        success {
            script {
                logger.Info("Pipeline job execution success!")
                notify.DingTalkNotify("55727fca73f341830a5d2cce86897edc10146ac6dc934c936f4ba7821e1540d3","${JOB_NAME}", "构建成功 ✅", "master")
                notify.MailNotify("727474430@qq.com", "${currentBuild.currentResult}")
            }
        }
        failure {
            script {
                logger.Error("Pipeline job execution failure!")
                notify.DingTalkNotify("55727fca73f341830a5d2cce86897edc10146ac6dc934c936f4ba7821e1540d3","${JOB_NAME}", "构建失败 ❌", "master")
                notify.MailNotify("727474430@qq.com", "${currentBuild.currentResult}")
            }
        }
    }
}
