package org.scobca.scanshuffle.service

import org.scobca.scanshuffle.dto.user.DeletedUser
import org.scobca.scanshuffle.dto.user.UpdateUserDto
import org.scobca.scanshuffle.dto.user.UserFilterDto
import org.scobca.scanshuffle.dto.user.CreateUserDto
import org.scobca.scanshuffle.dto.user.UserDto
import org.scobca.scanshuffle.repository.UserRepository
import org.scobca.scanshuffle.repository.mapper.UsersMapper
import org.scobca.scanshuffle.repository.spec.UsersSpecifications
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.careerseekers.core.exceptions.DoubleRecordException
import ru.careerseekers.core.exceptions.NotFoundException
import java.util.UUID

@Service
class UserService(
    private val repository: UserRepository,
    private val usersSpecifications: UsersSpecifications,
    private val usersMapper: UsersMapper,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(
        filters: UserFilterDto?,
        pageable: Pageable,
    ): Page<UserDto> {
        val specs = listOfNotNull(
            usersSpecifications.hasName(filters?.name),
            usersSpecifications.hasEmail(filters?.email)
        )

        return repository.findAll(Specification.allOf(specs), pageable)
            .map(usersMapper::userDtoFromUser)
    }

    fun getById(userId: UUID): UserDto {
        return repository.findByIdOrNull(userId)
            ?.let(usersMapper::userDtoFromUser)
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")
    }

    fun getByEmail(email: String): UserDto {
        return repository.findByEmail(email)
            ?.let(usersMapper::userDtoFromUser)
            ?: throw NotFoundException(message = "Пользователь с таким email не найден")
    }

    @Transactional
    fun create(dto: CreateUserDto): UserDto {
        repository.findByEmail(dto.email)
            ?.let { user -> throw DoubleRecordException(message = "Пользователь с email ${user.email} уже существует.") }

        return dto.copy(password = passwordEncoder.encode(dto.password)!!)
            .let(usersMapper::userFromDto)
            .let(repository::save)
            .let(usersMapper::userDtoFromUser)
    }

    @Transactional
    fun update(userId: UUID, dto: UpdateUserDto): UserDto {
        val user = repository.findByIdOrNull(userId)
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")

        return user.apply {
            dto.name?.let { name = it }
            dto.email?.let { newEmail ->
                val oldUser = repository.findByEmail(newEmail)
                if (oldUser != null && oldUser != this)
                    throw DoubleRecordException("Пользователь с email $newEmail уже существует.")

                email = newEmail
            }
        }.let { user ->
            repository.save(user)
            return@let usersMapper.userDtoFromUser(user)
        }
    }

    @Transactional
    fun delete(userId: UUID): DeletedUser {
        val user = repository.findByIdOrNull(userId)
            ?: throw NotFoundException("Пользователь с таким ID не найден")

        val deletedUser = DeletedUser(
            id = user.id!!,
            email = user.email,
        )

        repository.delete(user)

        return deletedUser
    }

    @Transactional
    fun deleteAll(): String {
        repository.deleteAll()
        return "Все пользователи удалены."
    }
}
