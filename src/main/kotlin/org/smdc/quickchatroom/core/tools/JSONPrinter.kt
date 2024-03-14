package org.smdc.quickchatroom.core.tools

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.util.*

/**
 * JSON打印缩进风格，默认使用Tab(\t)缩进
 */
var indentStyle: String = "\t"

/**
 * 按格式打印JSON对象
 * @param obj JSON对象
 * @param indent 缩进
 *
 * @see printJSON
 */
fun printJSONArray(obj: JSONObject, indent: Int) {
    val indentedString = java.lang.String.join("", Collections.nCopies(indent, indentStyle))
    obj.forEach { key: String?, value: Any ->
        System.out.printf("%s%s: ", indentedString, key)
        printIndentedJSON(value, indent)
    }
}

/**
 * 按格式打印JSON数组
 * @param objs JSON数组
 * @param indent 缩进
 *
 * @see printJSON
 */
fun printJSONArray(objs: JSONArray, indent: Int) {
    val indentedString = java.lang.String.join("", Collections.nCopies(indent, indentStyle))
    objs.forEach { value: Any ->
        print(indentedString)
        printIndentedJSON(value, indent)
    }
}

/**
 * 按格式打印JSON(带有缩进)
 * @param obj JSON对象或JSON内容
 * @param indent 缩进
 *
 * @see printJSON
 */
fun printIndentedJSON(obj: Any, indent: Int) {
    if (obj is JSONObject) {
        println()
        printJSONArray(obj, indent + 1)
        return
    } else if (obj is JSONArray) {
        println()
        printJSONArray(obj, indent + 1)
        return
    }
    System.out.printf("%s (%s)%n", obj, obj.javaClass.typeName)
}

/**
 * 按格式打印JSON
 *
 * @param obj JSON对象或JSON内容
 */
fun printJSON(obj: Any) {
    printIndentedJSON(obj, -1)
}
