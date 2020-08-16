package com.example.gotravel.data

import com.example.gotravel.common.Result
import com.example.gotravel.common.Error
import com.example.gotravel.common.model.User
import com.example.gotravel.domain.UserDao
import com.example.gotravel.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val userDao: UserDao,
    private val appPreferences: AppPreferences
) : UserRepository {

    override var user: User? = null

    override suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthDate: Long,
        role: String
    ): Result<User> = withContext(Dispatchers.IO) {
        val newUser = User(firstName, lastName, email, password, "", 0L, "")
        userDao.insertOrUpdateUser(newUser)
        return@withContext Result.Success(newUser)
    }

    override suspend fun loginUser(email: String, password: String) = withContext(Dispatchers.IO) {
        userDao.getUser(email, password)?.let { user ->
            saveUserDetails(user)
            return@withContext Result.Success(user)
        }
        return@withContext Result.Failure(Error.UnauthorizedError("User with with this email doesn't exists."))
    }

    private fun saveUserDetails(user: User): Boolean {
        this.user = user
        appPreferences.setUserId(user.id)
        appPreferences.setUserFirstName(user.firstName)
        appPreferences.setUserLastName(user.lastName)
        appPreferences.setUserEmail(user.email)
        appPreferences.setUserPassword(user.password)
        appPreferences.setUserGender(user.gender)
        appPreferences.setUserBirthDate(user.birthDate)
        appPreferences.setUserRole(user.role)
        return true
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