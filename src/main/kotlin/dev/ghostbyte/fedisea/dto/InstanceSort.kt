package dev.ghostbyte.fedisea.dto

enum class InstanceSort(val apiName: String, val databaseField: String) {
    USERS("users", "activeUsersMonth"),
    ACTIVE_USERS_MONTH("activeUsersMonth", "activeUsersMonth"),
    ACTIVE_USERS_HALFYEAR("activeUsersHalfyear", "activeUsersHalfyear"),
    softwareVersion("softwareVersion", "softwareVersion"),
    POSTS("posts", "localPosts"),
    NAME("name", "domain");

    companion object {
        fun fromString(value: String): InstanceSort {
            return values().find { it.apiName.equals(value, ignoreCase = true) } ?: USERS
        }
    }
}