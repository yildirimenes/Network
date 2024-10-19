package group.beymen.network.common

data class UiConfigurationState(
    val isDarkMode: Boolean = false,
    val isLargeText: Boolean = false,
    val language: String = "en",
    val someOtherSetting: Any? = null
)
