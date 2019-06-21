package com.sandjelkovic.dispatchd.auth.data.repository

import com.sandjelkovic.dispatchd.auth.data.entity.AuthClientDetails
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface AuthClientRepository : MongoRepository<AuthClientDetails, String> {

    fun findByClientId(clientId: String): Optional<AuthClientDetails>

}
