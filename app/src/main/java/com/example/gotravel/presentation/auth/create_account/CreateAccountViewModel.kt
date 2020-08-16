package com.example.gotravel.presentation.auth.create_account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.model.User
import com.example.gotravel.domain.UserDao
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountViewModel(private val repository: DefaultUserRepository) : ViewModel() {


    fun createUser(firstName: String, lastName: String, email: String, password: String, birthDate: Long, role: String) {
        viewModelScope.launch {
            val result = repository.createUser(firstName, lastName, email, password, birthDate, role)
            when(result) {
                is Result.Success -> {
                    println("uuuuuuuuuu" + result.data.email +  result.data.password)

                }

            }
           /* val result = CreateUserUseCase(firstName, lastName, email, password, birthDate)
            when(result) {
                is Result.Success -> {
                    println("uuuuuuuuuu" + result.data.email +  result.data.password)
                }
                is Result.Failure -> {

                }
            } */
        }
    }


}
/*
class CreateAccountViewModel(private val database: UserDao, application: Application) :
    AndroidViewModel(application) {



    fun createUser(firstName: String, lastName: String, email: String, password: String, birthDate: Long) {
        viewModelScope.launch {
            val result = createUserUseCase(firstName, lastName, email, password, birthDate)
            when(result) {
                is Result.Success -> {
                    println("uuuuuuuuuu" + result.data.email +  result.data.password)
                }
            }
        }
    }

    private suspend fun createUserUseCase(firstName: String, lastName: String, email: String, password: String, birthDate: Long) = withContext(Dispatchers.IO) {
        val newUser = User(firstName, lastName, email, password, "", 0L, "" )
        database.insert(newUser)
        val insertedUser = database.loginUser(email, password)
        return@withContext Result.Success(insertedUser)
    }

} */