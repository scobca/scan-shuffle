package org.scobca.scanshuffle.dto.user

import kotlinx.serialization.Serializable
import ru.careerseekers.core.dto.IDtoClass
import ru.careerseekers.core.serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class UserDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val email: String,
) : IDtoClass