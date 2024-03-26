package com.github.l1an.yubattlemusic.core.listener

import com.github.l1an.yubattlemusic.api.event.PlayerBattleEvent
import com.github.l1an.yubattlemusic.core.config.Config.fadeout
import com.github.l1an.yubattlemusic.core.config.Config.ignoreCreative
import com.github.l1an.yubattlemusic.core.config.Config.ignoreEntity
import com.github.l1an.yubattlemusic.core.config.Config.ignorePvp
import com.github.l1an.yubattlemusic.core.config.Config.musicSectionKey
import com.github.l1an.yubattlemusic.core.config.Config.runAwayTime
import com.github.l1an.yubattlemusic.core.config.Config.timeout
import com.github.l1an.yubattlemusic.core.manager.BattleManager.addBattle
import com.github.l1an.yubattlemusic.core.manager.BattleManager.getCurrentMusic
import com.github.l1an.yubattlemusic.core.manager.BattleManager.isBattle
import com.github.l1an.yubattlemusic.core.manager.BattleManager.removeBattle
import com.github.l1an.yubattlemusic.core.manager.BattleManager.setCurrentMusic
import com.github.l1an.yubattlemusic.util.getDelayTime
import com.github.l1an.yubattlemusic.util.getMusicName
import com.github.l1an.yubattlemusic.util.getPriority
import com.github.l1an.yubattlemusic.util.getSectionValue
import org.bukkit.GameMode
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.common.platform.service.PlatformExecutor
import taboolib.module.chat.uncolored
import taboolib.platform.util.attacker

object BattleListener {

    private val taskMap: MutableMap<Player, PlatformExecutor.PlatformTask?> = HashMap()

    /**
     * 玩家离开战斗事件
     */
    @SubscribeEvent
    fun onPlayerLeaveBattle(e: PlayerBattleEvent.Out) {
        val player = e.player
        // 将玩家移出战斗列表
        removeBattle(player)
        // 延迟执行淡出音乐
        submit(delay = runAwayTime * 20L) {
            setCurrentMusic(player, null)
            player.stopSound(SoundCategory.MUSIC)
            player.playSound(player.location, fadeout, SoundCategory.MUSIC, 1f, 1f)
        }
    }

    /**
     * 检测玩家进入战斗
     */
    @SubscribeEvent
    fun onPlayerAttack(e: EntityDamageByEntityEvent) {
        val player = e.attacker as? Player ?: return
        // 将玩家加入战斗列表
        addBattle(player)
        // 唤起进入战斗事件
        PlayerBattleEvent.In(player, true).call()
        // 取消之前的延迟退出战斗状态任务（如果有）
        taskMap[player]?.cancel()
        // 创建并存储新的延迟退出战斗状态任务
        val task = submit(delay = timeout * 20L) {
            // 唤起离开战斗事件
            PlayerBattleEvent.Out(player, false).call()
            // 从映射中移除该任务，表示玩家不再战斗状态
            taskMap.remove(player)
        }
        taskMap[player] = task

        // 获取生物信息
        val entity = e.entity
        val entityName = entity.name.uncolored()

        // 检测玩家是否在创造模式
        if (player.gameMode == GameMode.CREATIVE && ignoreCreative) {
            return
        }
        // 检测是否忽略pvp
        if (entity is Player && ignorePvp) {
            return
        }
        // 检测是否为忽略的生物类型
        if (ignoreEntity.contains(entity.type.name)) {
            return
        }

        // 获取当前生物的音乐键，也就是要播放的新音乐键
        val musicKey = if (musicSectionKey.contains(entityName)) entityName else "default"
        // 获取当前播放的音乐
        val currentMusic = getCurrentMusic(player)

        // 检测是否正在播放音乐，如果没有播放音乐或者播放的音乐不是针对当前生物的音乐，则开始播放新音乐
        if (currentMusic == null || currentMusic != musicKey) {

            val newMusic = getMusicWithPriority(musicKey, currentMusic)

            // 如果当前没有播放音乐，或者播放的音乐不是针对当前生物的音乐，则开始播放新音乐
            playMusic(player, newMusic)

            // 更新播放状态和当前播放的音乐
            setCurrentMusic(player, newMusic)

            // 延迟音乐持续时间后重置播放状态
            submit(delay = getDelayTime(getSectionValue(newMusic))) {
                // 如果玩家仍在战斗状态，则重新播放音乐
                if (isBattle(player)) {
                    player.stopSound(SoundCategory.MUSIC)
                    playMusic(player, newMusic)
                } else { // 否则停止播放音乐
                    player.stopSound(SoundCategory.MUSIC)
                    setCurrentMusic(player, null)
                }
            }
        }
    }

    /**
     * 传入两个音乐键，返回优先级较高的音乐键
     * @param musicKey 新传入的音乐键
     * @param currentMusic 当前播放的音乐键
     */
    private fun getMusicWithPriority(musicKey: String, currentMusic: String?): String {
        val newPriority = getPriority(getSectionValue(musicKey))
        val currentPriority = getPriority(getSectionValue(currentMusic ?: "default"))

        // 如果新音乐的优先级低于当前音乐的优先级，则播放新音乐
        return if (newPriority < currentPriority) {
            musicKey
        } else (currentMusic ?: "default")
    }

    /**
     * 播放对应生物的音乐
     * @param player 玩家
     * @param entityName 生物名称
     */
    private fun playMusic(player: Player, entityName: String) {
        // 获取音乐的配置
        val music = getMusicName(getSectionValue(entityName))
        player.stopSound(SoundCategory.MUSIC)
        player.playSound(player.location, music, SoundCategory.MUSIC, 1f, 1f)
    }

}