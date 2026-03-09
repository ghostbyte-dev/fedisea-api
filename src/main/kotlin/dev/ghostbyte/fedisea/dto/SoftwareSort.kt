package dev.ghostbyte.fedisea.dto

enum class SoftwareSort(val apiName: String, val databaseField: String) {
    IDENTIFIER("identifier", "identifier"),
    ACTIVE_USERS_MONTH("activeUsersMonth", "activeUsersMonth"),
    ACTIVE_USERS_HALFYEAR("activeUsersHalfyear", "activeUsersHalfyear"),
    TOTAL_USERS("totalUsers", "totalUsers"),
    POSTS("localPosts", "localPosts"),
    COMMENTS("localComments", "localComments"),
    INSTANCES("instances", "instances");

    companion object {
        fun fromString(value: String): SoftwareSort {
            return values().find { it.apiName.equals(value, ignoreCase = true) } ?: ACTIVE_USERS_HALFYEAR
        }
    }
}