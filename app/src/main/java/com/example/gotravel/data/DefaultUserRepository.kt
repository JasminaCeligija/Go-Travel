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

    override suspend fun createOrUpdateUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        gender: String,
        birthDate: String,
        role: String
    ): Result<User> = withContext(Dispatchers.IO) {
        val newUser = User(firstName, lastName, email, password, gender, birthDate, role)
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

    override suspend fun logoutUser() = withContext(Dispatchers.IO) {
        user = null
        return@withContext Result.Success(Unit)
    }

    fun saveUserDetails(user: User): Boolean {
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

    fun getUserFromSharedPreferences(): User {
        return User(
            appPreferences.getUserFirstName() ?: "",
            appPreferences.getUserLastName() ?: "",
            appPreferences.getUserEmail() ?: "",
            appPreferences.getUserPassword() ?: "",
            appPreferences.getUserGender() ?: "",
            appPreferences.getUserBirthDate() ?: "",
            appPreferences.getUserRole() ?: ""
        )
    }
}