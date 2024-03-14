package org.smdc.quickchatroom.core.tools

/**
 * 通过value寻找key
 * @param v value
 *
 * @return key（若不存在）
 */
fun <K, V> Map<K, V>.findKeyByValue(v: V): K? {
    return this.entries.find { it.value == v }?.key
}