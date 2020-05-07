String JENKINS_URL = ""
pipeline {
    agent any
    stages {
        stage('构建代码') {
            steps {
                echo '开始构建'
                echo sh(returnStdout: true, script: 'env')
            }
        }
    }
    post {
        failure {
            echo '构建失败'
            dingTalk accessToken: '',
                    imageUrl: 'http://www.iconsdb.com/icons/preview/soylent-red/x-mark-3-xxl.png',
                    message: '构建失败',
                    jenkinsUrl: "${JENKINS_URL}"

        }
        success {
            echo '构建成功'
            dingTalk accessToken: '',
                    imageUrl: 'http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png',
                    message: '构建成功',
                    jenkinsUrl: "${JENKINS_URL}"
        }
        always {
            echo '开始构建'
            dingTalk accessToken: '',
                    imageUrl: 'http://icon-park.com/imagefiles/loading7_gray.gif',
                    message: '开始构建',
                    jenkinsUrl: "${JENKINS_URL}"
        }
    }
}
