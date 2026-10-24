package org.scobca.scanshuffle.dto.user

import ru.careerseekers.core.dto.IDtoClass
import java.util.UUID

data class DeletedUser(
    val id: UUID,
    val email: String,
) : IDtoClass
