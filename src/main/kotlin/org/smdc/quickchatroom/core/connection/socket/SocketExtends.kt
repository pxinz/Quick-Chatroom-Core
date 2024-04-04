package org.smdc.quickchatroom.core.connection.socket

import com.alibaba.fastjson2.JSON
import java.net.Socket

/**
 * 发送ByteArray类型数据(带有VarInt头)
 *
 * @see VarInt.writeVarInt
 */
fun Socket.sendData(data: ByteArray) {
    outputStream.write(data)
}

/**
 * 发送String类型数据(带有VarInt头)
 *
 * @see VarInt.writeVarInt
 */
fun Socket.sendData(data: String) {
    sendData(data.toByteArray())
}

/**
 * 发送任意类型数据(带有VarInt头)
 *
 * @see VarInt.writeVarInt
 */
fun Socket.sendData(data: Any) {
    sendData(data.toString())
}

/**
 * 接收带有VarInt头的ByteArray类型数据
 */
fun Socket.receiveData(): ByteArray {
    val size = VarInt.readVarInt(inputStream)
    return inputStream.readNBytes(size)
}

/**
 * 接收带有VarInt头的数据包类型数据
 *
 * @see DataPackage
 */
fun Socket.receivePackage(): DataPackage {
    return DataPackage(JSON.parseObject(receiveData()))
}
