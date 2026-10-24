package org.scobca.scanshuffle.dto.user

import ru.careerseekers.core.dto.IFilterDtoClass

data class UserFilterDto(
    val name: String?,
    val email: String?,
) : IFilterDtoClass
