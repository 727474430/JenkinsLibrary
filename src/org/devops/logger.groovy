package org.devops

/**
 * RED_COLOR='\E[1;31m'   #红
 * GREEN_COLOR='\E[1;32m' #绿
 * YELOW_COLOR='\E[1;33m' #黄
 * BLUE_COLOR='\E[1;34m'  #蓝
 * PINK='\E[1;35m'        #粉红
 * RES='\E[0m'
 */

def Info(message) {
    def colorMsg = "\033[1;32m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}

def Warning(message) {
    def colorMsg = "\\033[1;33m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}

def Error(message) {
    def colorMsg = "\033[1;31m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}

