package group.beymen.network.ui.main

data class UiConfigurationState(
    val isDarkMode: Boolean = false,
    val isLargeText: Boolean = false,
    val language: String = "en",
    val someOtherSetting: Any? = null
)
