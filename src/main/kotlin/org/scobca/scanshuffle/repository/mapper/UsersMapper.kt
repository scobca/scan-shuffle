package org.scobca.scanshuffle.repository.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.scobca.scanshuffle.dto.user.CreateUserDto
import org.scobca.scanshuffle.dto.user.UserDto
import org.scobca.scanshuffle.entity.User

@Mapper(componentModel = "spring")
interface UsersMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun userFromDto(dto: CreateUserDto): User

    fun userDtoFromUser(user: User): UserDto =
        UserDto(
            id = requireNotNull(user.id) { "Cannot map a user without an ID" },
            name = user.name,
            email = user.email,
        )
}
