package org.scobca.scanshuffle.repository.spec

import org.scobca.scanshuffle.entity.User
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class UsersSpecifications {
    fun hasName(name: String?): Specification<User>? = name?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.let { name ->
            Specification { root, _, cb ->
                cb.like(cb.lower(root["name"]), "%${name.lowercase()}%")
            }
        }

    fun hasEmail(email: String?): Specification<User>? = email?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.let { email ->
            Specification { root, _, cb ->
                cb.like(cb.lower(root["email"]), "%${email.lowercase()}%")
            }
        }
}