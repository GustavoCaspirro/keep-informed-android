package br.com.keep_informed.services

enum class ServiceStatus {SUCCESS,LOADING,ERROR}

abstract class ServiceResult<T>(val result: T,
                                val status: ServiceStatus,
                                val throwable: Throwable? = null)