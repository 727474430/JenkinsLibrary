package org.devops

def getChangeString() {
    def changeString = ""
    def MAX_MSG_LEN = 20
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            truncatedMsg = entry.msg.take(MAX_MSG_LEN)
            commitTime = new Date(entry.timestamp).format("yyyy-MM-dd HH:mm:ss")
            changeString += " - ${truncatedMsg} [${entry.author} ${commitTime}]\n"
        }
    }
    if (!changeString) {
        changeString = " - No new changes"
    }
    return (changeString)
}

// 构建成功 ✅
// 构建失败 ❌
def DingTalkNotify(webhook, jobName, status, version) {
    wrap([$class: 'BuildUser']) {
        def url = "https://oapi.dingtalk.com/robot/send?access_token=${webhook}"
        def log = getChangeString()
        def data = """{
            "type": "MARKDOWN",
            "title": "构建测试",
            "text": "### 构建信息\\n>- 应用名称: **${jobName}**\\n- 构建结果: **${status}**\\n- 当前版本: **${version}**\\n- 构建发起: 
            **${env.BUILD_USER}**\\n- 持续时间: **${currentBuild.durationString}**\\n- 构建日志: [点击查看详情](${env.BUILD_URL}console)\\n#### 更新记录: \\n${log}"
        }"""

        httpRequest acceptType: 'APPLICATION_JSON_UTF8',
                consoleLogResponseBody: false,
                contentType: 'APPLICATION_JSON_UTF8',
                httpMode: 'POST',
                ignoreSslErrors: true,
                requestBody: data,
                responseHandle: 'NONE',
                url: "${url}",
                quiet: true
    }
}

