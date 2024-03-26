package com.github.l1an.yubattlemusic.util

import com.github.l1an.yubattlemusic.core.config.Config
import taboolib.common5.Demand
import taboolib.common5.cint
import taboolib.common5.clong

/**
 * 获取音乐的名称
 * @param s demand
 */
// "music_name -delayTime 60(以秒为单位)"
fun getMusicName(s : String?) : String {
    val de = Demand(s ?: "")
    return de.namespace
}

/**
 * 获取音乐的延迟时间
 * @param s demand
 */
fun getDelayTime(s : String?) : Long {
    val de = Demand(s ?: "")
    return de.get("delayTime").clong * 20
}

/**
 * 获取音乐的优先级
 * @param s demand
 */
fun getPriority(s : String?) : Int {
    val de = Demand(s ?: "")
    return de.get("priority").cint
}

/**
 * 获取Section的值
 * @param s Section的名称
 */
fun getSectionValue(s : String = "default") : String? {
    return Config.musicSection.getString(s)
}