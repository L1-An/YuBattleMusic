package com.github.l1an.yubattlemusic

import taboolib.common.platform.Platform
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.module.chat.colored
import taboolib.module.metrics.Metrics

object YuBattleMusic : Plugin() {

    val messagePrefix = "&f[ &3YuBattleMusic &f]"

    override fun onEnable() {
        Metrics(21427, pluginVersion, Platform.BUKKIT)
        console().sendMessage("$messagePrefix &aYuBattleMusic has been loaded! - $pluginVersion".colored())
        console().sendMessage("$messagePrefix &bAuthor by L1An".colored())
    }
}