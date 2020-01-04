package br.com.keep_informed.services.authentication.results

import br.com.keep_informed.services.ServiceResult
import br.com.keep_informed.services.ServiceStatus
import br.com.keep_informed.services.authentication.model.AuthResponse

class AuthResult(result: AuthResponse?, status: ServiceStatus, throwable: Throwable?) :
    ServiceResult<AuthResponse?>(result, status, throwable) {

    companion object{
        fun resultSuccess(value:AuthResponse) : AuthResult {
            return AuthResult(
                value,
                ServiceStatus.SUCCESS,
                null
            )
        }

        fun resultError(throwable: Throwable?) : AuthResult {
            return AuthResult(
                null,
                ServiceStatus.ERROR,
                throwable
            )
        }

        fun resultLoading() : AuthResult {
            return AuthResult(
                null,
                ServiceStatus.LOADING,
                null
            )
        }
    }
}