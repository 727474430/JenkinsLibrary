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
        def PATH = tool 'maven-3.6.3'
        def BUILD_HOME = "${PATH}/bin/mvn"
    } else if (buildType == "gradle") {
        def PATH = tool 'gradle'
        def BUILD_HOME = "${PATH}/bin/gradle"
    } else {
        error '代码构建失败，目前只支持【maven|gradle】方式！'
    }

    echo "Build code starting. buildType: ${buildType}; buildParams: ${buildParams};"

    sh """
        ${BUILD_HOME} ${buildParams}
    """
}
