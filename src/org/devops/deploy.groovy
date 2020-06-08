package org.devops

/*
   ##################################################
   #                                                #
   #         Code deploy shared library             #
   #                                                #
   ##################################################
*/

def Deploy(ips, serverId, command) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshCommand remote: server, command: "${command}"
    }
}

def Script(ips, serverId, script) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshScript remote: server, script: "${script}"
    }
}

def CopyFile(ips, serverId, fromPath, toPath) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshPut remote: server, from: "${fromPath}", into: "${toPath}"
    }
}

def GetFile(ips, serverId, fromPath, toPath) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshGet remote: server, from: "${fromPath}", into: "${toPath}", override: true
    }
}

def RemoveFile(ips, serverId, path) {
    for (item in ips.tokenize(',')) {
        def server = findServer(item, serverId)
        sshRemove remote: server, path: "${path}"
    }
}

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
