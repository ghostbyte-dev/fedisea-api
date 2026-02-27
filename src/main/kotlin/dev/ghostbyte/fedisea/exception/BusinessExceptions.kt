package dev.ghostbyte.fedisea.exception

sealed class BusinessException(message: String) : RuntimeException(message)

class ResourceNotFoundException(resourceName: String, id: Any) :
    BusinessException("$resourceName with id $id not found")

class DuplicateResourceException(resourceName: String, field: String) :
    BusinessException("$resourceName with this $field already exists")

class InvalidOperationException(message: String) :
    BusinessException(message)