package org.devops

/*
   ##################################################
   #                                                #
   #         Code build shared library              #
   #                                                #
   ##################################################
*/

def Build(buildType, buildParams) {
    if (buildType == "maven") {
        PATH = tool 'maven-3.6.3'
        buildHome = "${PATH}/bin/mvn"
    } else if (buildType == "gradle") {
        PATH = tool 'gradle'
        buildHome = "${PATH}/bin/gradle"
    } else {
        error '代码构建失败，目前只支持【maven|gradle】方式！'
    }

    echo "Build code starting. buildType: ${buildType}; buildParams: ${buildParams};"

    sh """
        ${buildHome} ${buildParams}
    """
}
