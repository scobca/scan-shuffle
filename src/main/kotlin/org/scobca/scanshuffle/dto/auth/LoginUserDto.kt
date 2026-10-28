package org.scobca.scanshuffle.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import ru.careerseekers.core.dto.IDtoClass

data class LoginUserDto(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,
) : IDtoClass
