package org.smdc.quickchatroom.core.socket

import com.alibaba.fastjson.JSONObject
import org.smdc.quickchatroom.core.tools.JSONPrinter

/**
 * 通讯时所需的数据包
 */
data class Package(val route: String, val rId: Int?, val data: JSONObject) {
    companion object {
        var globalRId = 0
    }

    /**
     * 自动生成rId来创建数据包
     * @param route 访问路径
     * @param data 内容
     */
    constructor(route: String, data: JSONObject) : this(route, globalRId++, data)

    /**
     * 从收得的JSONObject中获取数据包
     * @param data 收得的JSONObject
     */
    constructor(data: JSONObject) : this(data.getString("route"), data.getInteger("rId"), data.getJSONObject("data"))

    /**
     * 打印数据包
     */
    fun printPackage() {
        println("PACK: ROUTE %s%n".format(route))
        JSONPrinter.printJSON(data)
    }

    /**
     * 以JSON格式转化数据包为字符串
     * @return 字符串形式的数据包
     */
    override fun toString(): String {
        val obj = JSONObject()
        obj["route"] = route
        if (rId != null) {
            obj["rId"] = rId
        }
        obj["data"] = data
        return obj.toJSONString()
    }
}
