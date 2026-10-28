package org.scobca.scanshuffle.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.scobca.scanshuffle.dto.user.CreateUserDto
import ru.careerseekers.core.dto.IDtoClass

data class RegisterUserDto(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Email(message = "Некорректный email")
    val email: String,

    @field:NotBlank
    val password: String,
) : IDtoClass {
    fun toCreateUserDto(): CreateUserDto {
        return CreateUserDto(
            name = this.name,
            email = this.email,
            password = this.password
        )
    }
}
