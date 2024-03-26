package com.github.l1an.yubattlemusic.api.event

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class PlayerBattleEvent {

    // 进入战斗事件
    class In(val player: Player, val status: Boolean) : BukkitProxyEvent()

    // 离开战斗事件
    class Out(val player: Player, val status: Boolean) : BukkitProxyEvent()

}