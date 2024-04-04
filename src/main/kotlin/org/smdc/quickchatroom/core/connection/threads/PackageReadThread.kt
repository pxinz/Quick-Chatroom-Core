package org.smdc.quickchatroom.core.connection.threads

import com.alibaba.fastjson2.JSON
import org.smdc.quickchatroom.core.connection.Connection
import org.smdc.quickchatroom.core.connection.socket.DataPackage
import org.smdc.quickchatroom.core.connection.socket.VarInt
import org.tinylog.Logger
import java.io.IOException
import java.io.InputStream
import java.net.Socket

abstract class PackageReadThread(protected open val connection: Connection) : Thread() {
    companion object {
        fun getInputStream(socket: Socket, maxRetry: Int): InputStream? {
            var times = maxRetry
            for (i in 1..maxRetry) {
                try {
                    return socket.getInputStream()
                } catch (e: IOException) {
                    Logger.error(e)
                    Logger.error("获取输入流失败(%d / %d)".format(i, maxRetry) as Any)
                    try {
                        sleep(3000)
                    } catch (ex: InterruptedException) {
                        throw RuntimeException(ex)
                    }
                }
            }
            return null
        }
    }

    override fun run() {
        var retry = 0 // 重试次数

        val stream = getInputStream(connection.socket, 10) // 输入流

        if (stream == null) {
            Logger.error("无法获取输入流，包读取进程结束" as Any)
            return
        }

        while (retry < 10 && !connection.socket.isClosed) { // 最多重试10次
            try {
                val packLen = VarInt.readVarInt(stream)
                val p = ByteArray(packLen)
                val realLen = stream.read(p) // 读取包
                val pack = String(p)
                if (packLen != realLen) { // 比较二者长度
                    Logger.warn("声明的包长度(%d)与实际读取(%d)不相符".format(packLen, realLen) as Any)
                }
                handlePackage(DataPackage(JSON.parseObject(pack)))
            } catch (e: IOException) {
                retry++
            }
        }

        Logger.error("连接断开，包读取进程结束" as Any)
    }

    abstract fun handlePackage(pkg: DataPackage)
}