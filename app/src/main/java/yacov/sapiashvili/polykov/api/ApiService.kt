package yacov.sapiashvili.polykov.api

import retrofit2.Response
import retrofit2.http.GET
import yacov.sapiashvili.polykov.model.ConfigurationResponse
import yacov.sapiashvili.polykov.utils.Constants

interface ApiService {
    @GET(Constants.CONFIGURATIONS_END_POINT)
    suspend fun getConfigurations(): Response<ConfigurationResponse>

}