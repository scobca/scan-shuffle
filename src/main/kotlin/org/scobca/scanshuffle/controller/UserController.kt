package org.scobca.scanshuffle.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.scobca.scanshuffle.dto.user.UpdateUserDto
import org.scobca.scanshuffle.dto.user.CreateUserDto
import org.scobca.scanshuffle.dto.user.UserDto
import org.scobca.scanshuffle.dto.user.UserFilterDto
import org.scobca.scanshuffle.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.careerseekers.core.api.BasicSuccessfulResponse
import ru.careerseekers.core.api.toHttpResponse
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(
        @ModelAttribute filters: UserFilterDto,
        pageable: Pageable
    ): Page<UserDto> = userService.getAll(filters, pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = userService.getById(id).toHttpResponse()

    @GetMapping("/getByEmail/{email}")
    fun getByEmail(
        @Email(message = "Некорректный email")
        @PathVariable
        email: String
    ) = userService.getByEmail(email).toHttpResponse()

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateUserDto) = userService.create(dto).toHttpResponse()

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, @Valid @RequestBody dto: UpdateUserDto) =
        userService.update(id, dto).toHttpResponse()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): BasicSuccessfulResponse<String> {
        userService.delete(id)
        return BasicSuccessfulResponse("Пользователь удален успешно.")
    }

    @DeleteMapping
    fun deleteAll() = BasicSuccessfulResponse(userService.deleteAll())
}