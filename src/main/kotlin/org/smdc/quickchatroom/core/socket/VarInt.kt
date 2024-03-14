package org.smdc.quickchatroom.core.socket

import java.io.InputStream
import java.io.OutputStream


object VarInt {
    /**
     * 0b01111111，作按位与后可取出VarInt单字节的内容
     */
    private const val CONTENT_BITS = 0b01111111

    /**
     * 0b10000000，VarInt继续标识符
     * 当VarInt没有结束时，首位应该是0
     */
    private const val CONTINUE_BIT = 0b10000000

    /**
     * 从输入流中读取VarInt
     *
     * @param stream 输入流
     * @return 读得的值
     */
    fun readVarInt(stream: InputStream): Int {
        var value = 0
        var offset = 0
        var now: Int
        do {
            now = stream.read()
            value += now and CONTENT_BITS shl offset
            offset += 7
        } while ((now and CONTINUE_BIT) != 0)
        return value
    }

    /**
     * 向输出流中写入VarInt
     *
     * @param value 值
     * @param stream 输出流
     */
    fun writeVarInt(value: Int, stream: OutputStream) {
        var varValue = value
        do {
            stream.write(varValue and CONTENT_BITS or CONTINUE_BIT)
            varValue = varValue shr 7
        } while (varValue > CONTENT_BITS)
        stream.write(varValue)
    }
}
