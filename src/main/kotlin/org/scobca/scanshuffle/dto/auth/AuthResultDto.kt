package org.scobca.scanshuffle.dto.auth

import ru.careerseekers.core.dto.IDtoClass

data class AuthResultDto(
    val accessToken: String,
    val refreshToken: String,
) : IDtoClass
