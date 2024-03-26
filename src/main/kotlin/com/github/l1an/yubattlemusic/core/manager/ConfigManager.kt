package com.github.l1an.yubattlemusic.core.manager

import com.github.l1an.yubattlemusic.core.config.Config

object ConfigManager {

    fun reload() {
        Config.config.reload()
    }

}