package org.devops

/*
   ##################################################
   #                                                #
   #            Logger shared library               #
   #               RED == \033[1;31m                #
   #               GREEN == \E[1;32m                #
   #               BLUE == \E[1;34m                 #
   #               PINK == \E[1;35m                 #
   ##################################################
*/

// Info log
def Info(message) {
    def colorMsg = "\033[1;32m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}

// Warning log
def Warning(message) {
    def colorMsg = "\\033[1;35m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}

// Error log
def Error(message) {
    def colorMsg = "\033[1;31m >>>>>>>>>>>>>>${message}<<<<<<<<<<<<<<"
    ansiColor('xterm') {
        println(colorMsg)
    }
}
