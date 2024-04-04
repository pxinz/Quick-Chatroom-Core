package org.smdc.quickchatroom.core


val asciiTitle = "\u001b[36m" + """
 ________     ___  ___    ___    ________    ___  __       
|\   __  \   |\  \|\  \  |\  \  |\   ____\  |\  \|\  \     
\ \  \|\  \  \ \  \\\  \ \ \  \ \ \  \___|  \ \  \/  /|_   
 \ \  \\\  \  \ \  \\\  \ \ \  \ \ \  \      \ \   ___  \  
  \ \  \\\  \  \ \  \\\  \ \ \  \ \ \  \____  \ \  \\ \  \ 
   \ \_____  \  \ \_______\ \ \__\ \ \_______\ \ \__\\ \__\
    \|___| \__\  \|_______|  \|__|  \|_______|  \|__| \|__|
          \|__|""".trimIndent() + "\u001B[34m" + """
 ________    ___  ___    ________    _________    ________    ________    ________    _____ ______      
|\   ____\  |\  \|\  \  |\   __  \  |\___   ___\ |\   __  \  |\   __  \  |\   __  \  |\   _ \  _   \    
\ \  \___|  \ \  \\\  \ \ \  \|\  \ \|___ \  \_| \ \  \|\  \ \ \  \|\  \ \ \  \|\  \ \ \  \\\__\ \  \   
 \ \  \      \ \   __  \ \ \   __  \     \ \  \   \ \   _  _\ \ \  \\\  \ \ \  \\\  \ \ \  \\|__| \  \  
  \ \  \____  \ \  \ \  \ \ \  \ \  \     \ \  \   \ \  \\  \| \ \  \\\  \ \ \  \\\  \ \ \  \    \ \  \ 
   \ \_______\ \ \__\ \__\ \ \__\ \__\     \ \__\   \ \__\\ _\  \ \_______\ \ \_______\ \ \__\    \ \__\
    \|_______|  \|__|\|__|  \|__|\|__|      \|__|    \|__|\|__|  \|_______|  \|_______|  \|__|     \|__|
""" + "\u001b[33m" + """
________    ________    ________    _______      
|\   ____\  |\   __  \  |\   __  \  |\  ___ \     
\ \  \___|  \ \  \|\  \ \ \  \|\  \ \ \   __/|    
 \ \  \      \ \  \\\  \ \ \   _  _\ \ \  \_|/__  
  \ \  \____  \ \  \\\  \ \ \  \\  \| \ \  \_|\ \ 
   \ \_______\ \ \_______\ \ \__\\ _\  \ \_______\
    \|_______|  \|_______|  \|__|\|__|  \|_______|
""" + "\u001b[0m"

val dependencies = arrayOf("org.tinylog.Logger", "com.alibaba.fastjson2.JSONObject")

fun testForDependency(className: String): Boolean {
    try {
        ClassLoader.getSystemClassLoader().loadClass(className)
    } catch (e: ClassNotFoundException) {
        println("\u001B[31m × Cannot find class %s\u001B[0m".format(className))
        return false
    }
    println("\u001B[32m · Found class %s\u001B[0m".format(className))
    return true
}

fun main() {
    println(asciiTitle)
    println("\u001B[0m ! Notice: This is just a testing program !")
    println("\u001B[36m · Core Github: https://github.com/pxinz/Quick-Chatroom-Core")
    println("\u001B[36m · Client Github: https://github.com/pxinz/Quick-Chatroom-Client")
    println("\u001B[36m · Server Github: https://github.com/pxinz/Quick-Chatroom-Server")
    println("\u001B[0m - Test: dependencies")
    var flag = true
    dependencies.forEach {
        flag = flag && testForDependency(it)
    }
    println(if (flag) "\u001b[34m > Dependencies are complete" else "\u001B[33m22 > Dependencies are not complete")
}