package org.devops

/*
   ##################################################
   #                                                #
   #         Checkout Code shared library           #
   #                                                #
   ##################################################
*/

// Checkout code from scm
def Checkout(scmType, url, credentialsId, branchName = "master") {
    if (scmType == "svn") {
        checkout([$class                : 'SubversionSCM',
                  additionalCredentials : [],
                  excludedCommitMessages: '',
                  excludedRegions       : '',
                  excludedRevprop       : '',
                  excludedUsers         : '',
                  filterChangelog       : false,
                  ignoreDirPropChanges  : false,
                  includedRegions       : '',
                  locations             : [[cancelProcessOnExternalsFail: true,
                                            credentialsId               : "${credentialsId}",
                                            depthOption                 : 'infinity',
                                            ignoreExternalsOption       : true,
                                            local                       : '.',
                                            remote                      : "${url}"]],
                  quietOperation        : true,
                  workspaceUpdater      : [$class: 'UpdateUpdater']])
    } else if (scmType == "git") {
        checkout([$class                           : 'GitSCM',
                  branches                         : [[name: "${branchName}"]],
                  doGenerateSubmoduleConfigurations: false,
                  extensions                       : [],
                  submoduleCfg                     : [],
                  userRemoteConfigs                : [[credentialsId: "${credentialsId}",
                                                       url          : "${url}"]]])
    } else {
        error '代码拉取失败，目前只支持【svn|git】方式！'
    }
}
