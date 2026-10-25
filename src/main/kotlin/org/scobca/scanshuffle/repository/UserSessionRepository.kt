package org.scobca.scanshuffle.repository

import org.scobca.scanshuffle.entity.UserSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserSessionRepository : JpaRepository<UserSession, UUID>