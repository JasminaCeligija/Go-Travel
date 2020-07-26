package com.example.gotravel.domain.usecases

import com.example.gotravel.data.DefaultUserRepository

class GetAuthStateUseCase : UseCase<Unit, Boolean>() {

    private val repository = DefaultUserRepository()

    override fun doWork(params: Unit): Boolean {
        return repository.isUserAuthenticated()
    }
}