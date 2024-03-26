package com.github.l1an.yubattlemusic.core.config

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object Config {

    @Config("config.yml")
    lateinit var config: Configuration
        private set

    // 战斗状态的持续时间
    val timeout : Int
        get() = config.getInt("battle.timeout", 10)

    // 获取是否忽略创造模式
    val ignoreCreative : Boolean
        get() = config.getBoolean("ignore-creative", true)

    // 获取停止战斗后停止音乐的时间
    val runAwayTime : Int
        get() = config.getInt("run-away-time", 15)

    // 获取忽略播放音乐的生物类型列表
    val ignoreEntity : List<String>
        get() = config.getStringList("ignore-entity")

    // 获取是否忽略pvp音乐
    val ignorePvp : Boolean
        get() = config.getBoolean("ignore-pvp", false)

    // 获取淡出音乐
    val fadeout : String
        get() = config.getString("fadeout") ?: "fadeout"

    // 获取ConfigSection
    val musicSection : ConfigurationSection
        get() = config.getConfigurationSection("music") ?: config.createSection("music")
    val musicSectionKey
        get() = musicSection.getKeys(false)

}