package yacov.sapiashvili.polykov.repository

import yacov.sapiashvili.polykov.api.ApiService
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(private val apiService: ApiService)  {
    suspend fun getConfigurations() = apiService.getConfigurations()
}