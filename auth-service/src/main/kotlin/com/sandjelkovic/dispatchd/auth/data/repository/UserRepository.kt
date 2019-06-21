package com.sandjelkovic.dispatchd.auth.data.repository

import com.sandjelkovic.dispatchd.auth.data.entity.UserModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : MongoRepository<UserModel, String> {
    fun findByUsername(username: String): Optional<UserModel>
}
