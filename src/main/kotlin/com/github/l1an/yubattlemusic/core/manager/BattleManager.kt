package com.github.l1an.yubattlemusic.core.manager

import org.bukkit.entity.Player
import kotlin.collections.HashMap

object BattleManager {
    private val battleMap: MutableMap<Player, Boolean> = HashMap()
    private val currentMusicMap: MutableMap<Player, String?> = HashMap()

    /**
     * 获取玩家当前播放的音乐
     */
    fun getCurrentMusic(player: Player) : String? {
        return currentMusicMap[player]
    }

    /**
     * 设置玩家当前播放的音乐
     */
    fun setCurrentMusic(player: Player, music: String?) {
        currentMusicMap[player] = music
    }

    /**
     * 将玩家加入战斗列表
     */
    fun addBattle(player: Player) {
        battleMap[player] = true
    }

    /**
     * 将玩家移出战斗列表
     */
    fun removeBattle(player: Player) {
        battleMap.remove(player)
    }

    /**
     * 判断玩家是否在战斗列表中
     */
    fun isBattle(player: Player) : Boolean {
        return battleMap[player] ?: false
    }

}