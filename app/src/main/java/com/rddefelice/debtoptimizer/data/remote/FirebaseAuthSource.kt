package com.rddefelice.debtoptimizer.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.rddefelice.debtoptimizer.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthSource @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun signUp(email: String, password: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.let {
                User(uid = it.uid, email = it.email ?: "")
            }
            if (user != null) Result.success(user)
            else Result.failure(Exception("Sign up failed: User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.let {
                User(uid = it.uid, email = it.email ?: "")
            }
            if (user != null) Result.success(user)
            else Result.failure(Exception("Login failed: User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser(): User? {
        return auth.currentUser?.let {
            User(uid = it.uid, email = it.email ?: "")
        }
    }

    fun logout() {
        auth.signOut()
    }
}