package com.example.gotravel.data

import androidx.lifecycle.LiveData
import com.example.gotravel.common.Result
import com.example.gotravel.common.Error
import com.example.gotravel.common.model.User
import com.example.gotravel.domain.UserDao
import com.example.gotravel.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultUserRepository(private val userDao: UserDao): UserRepository {

    override var user: User? = null



    override suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthDate: Long
    ) = withContext(Dispatchers.IO) {
        val newUser = User(firstName, lastName, email, password, "", 0L, "" )
        userDao.insert(newUser)
        return@withContext Result.Success(newUser)
    }

    override suspend fun loginUser(email: String, password: String) = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    /*override fun isUserAuthenticated(): Boolean {
        return user != null
    }

    override suspend fun loginUser(email: String, password: String) = withContext(Dispatchers.IO) {

        //TODO: implement API request, User should be UserResource...
        when {
            email == "orion@gmail.com" && password == "000000" -> {
                user = User(uuid = "1", email = email, password = password)
                return@withContext Result.Success(user!!)
            }
            else -> {
                return@withContext Result.Failure(Error.InvalidDataError("User with this email and password does not exits."))
            }
        }
    }

    override suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthDate: Long
    ) = withContext(Dispatchers.IO) {
        return@withContext Result.Success(getMockedUser())
    }

    private fun getMockedUser(): User {
        return User("1", "Orion", "Team", "orion@gmail.com", "0000000", "Male", 1111111)
    }

    override suspend fun logoutUser() = withContext(Dispatchers.IO) {
        user = null
        return@withContext Result.Success(Unit)
    } */

}