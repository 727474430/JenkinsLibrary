package org.devops

import org.apache.tools.ant.types.resources.selectors.Date

/*
   ##################################################
   #                                                #
   #         Message Notify shared library          #
   #                                                #
   ##################################################
   DingTalk webSite: https://ding-doc.dingtalk.com/doc#/serverapi2/elzz1p/yCEes
*/

// DingTalk job complete notification
def DingTalkNotify(webhook, jobName, status, version) {
    wrap([$class: 'BuildUser']) {
        def url = "https://oapi.dingtalk.com/robot/send?access_token=${webhook}"
        def log = getChangeString()
        def data = """{
            "msgtype": "markdown",
            "markdown": {
                "title": "监控报警",
                "text": "### 构建信息\n>- 应用名称: **${jobName}**\n>- 构建结果: **${status}**\n>- 当前版本: **${version}**\n>- 构建发起: **${env.BUILD_USER}**\n>- 持续时间: **${currentBuild.durationString}**\n>- 构建日志: [点击查看详情](${env.BUILD_URL}console)\n#### 更新记录\n ${log}"
            },
            "at": {
                "atMobiles": [
                    ""
                ], 
                "isAtAll": false
            }
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

// Email job complete notification
def MailNotify(to, status) {
    wrap([$class: 'BuildUser']) {
        mail to: "${to}",
                subject: "PineLine '${JOB_NAME}' (${BUILD_NUMBER}) result",
                body: "${env.BUILD_USER}'s Pineline '${JOB_NAME}' (${BUILD_NUMBER}) run ${status}\n 请及时前往 ${env.BUILD_URL} 进行查看"
    }
}

// Get commit log
@NonCPS
def getChangeString() {
    def changeLogging = ""
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            def commitMsg = formatCommitMsg(entry.msg)
            def commitTime = formatTimestamp(entry.timestamp)
            def commitAuthor = entry.author
            changeString += " >- ${commitMsg} [${commitAuthor} ${commitTime}] \n"
        }
    }
    if (!changeLogging) {
        changeLogging = " - No new changes"
    }
    return (changeLogging)
}

def formatTimestamp(timestamp) {
    return (new Date(timestamp).format("yyyy-MM-dd HH:mm:ss"))
}

def formatCommitMsg(message) {
    if (message.length() > 18) {
        return (message.take(18) + "...")
    }
    return (message)
}

def combinationChangeLogging(commitMsg, commitTime, commitAuthor) {
    return (" >- ${commitMsg} [${commitAuthor} ${commitTime}] \n")
}