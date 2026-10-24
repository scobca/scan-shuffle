package org.scobca.scanshuffle.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import ru.careerseekers.core.dto.IDtoClass


data class CreateUserDto(
    @NotBlank
    val name: String,

    @NotBlank
    @Email(message = "Некорректный email")
    val email: String,

    @NotBlank
    val password: String,

) : IDtoClass

