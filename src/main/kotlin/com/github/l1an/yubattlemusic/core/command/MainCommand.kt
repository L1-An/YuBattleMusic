package com.github.l1an.yubattlemusic.core.command

import com.github.l1an.yubattlemusic.api.event.PluginReloadEvent
import com.github.l1an.yubattlemusic.core.manager.ConfigManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.platform.util.sendLang

@CommandHeader("yubattlemusic", aliases = ["ybm", "battlemusic", "bm", "yubm"])
object MainCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            sender.sendLang("reload")
            ConfigManager.reload()
            PluginReloadEvent().call()
        }
    }

}