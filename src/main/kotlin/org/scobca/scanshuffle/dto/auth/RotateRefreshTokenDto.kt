package org.scobca.scanshuffle.dto.auth

import ru.careerseekers.core.dto.IDtoClass
import java.util.UUID

data class RotateRefreshTokenDto(
    val refreshToken: String,
    val userId: UUID
) : IDtoClass
