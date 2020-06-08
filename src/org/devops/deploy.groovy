package org.devops

/*
   ##################################################
   #                                                #
   #         Code deploy shared library             #
   #                                                #
   ##################################################
*/

// Deploy app. Command execute on remote server
def Deploy(ips, serverId, command) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshCommand remote: server, command: "${command}"
    }
}

// Script execute on remote server
def Script(ips, serverId, script) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshScript remote: server, script: "${script}"
    }
}

// File copy from local path to remote server path
def CopyFile(ips, serverId, fromPath, toPath) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshPut remote: server, from: "${fromPath}", into: "${toPath}"
    }
}

// File get from local path to remote server path. if the file exists then override old file
def GetFile(ips, serverId, fromPath, toPath) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshGet remote: server, from: "${fromPath}", into: "${toPath}", override: true
    }
}

// Remove file on remote server
def RemoveFile(ips, serverId, path) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshRemove remote: server, path: "${path}"
    }
}

// Write file to local
def WriteFile(fileName, fileContent) {
    writeFile file: "${fileName}", text: "${fileContent}"
}

def findServer(ip, serverId) {
    def remote = [:]
    remote.name = "ssh-${ip}"
    remote.host = "${ip}"
    remote.port = 22
    remote.allowAnyHosts = true
    withCredentials([usernamePassword(credentialsId: "${serverId}", passwordVariable: 'password', usernameVariable: 'userName')]) {
        remote.user = "${userName}"
        remote.password = "${password}"
    }
    return remote
}
