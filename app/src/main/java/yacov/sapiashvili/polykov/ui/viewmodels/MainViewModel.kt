package yacov.sapiashvili.polykov.ui.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import yacov.sapiashvili.polykov.repository.ConfigurationRepository
import yacov.sapiashvili.polykov.utils.Constants.DEFAULT_DEGREES
import yacov.sapiashvili.polykov.utils.Constants.DEFAULT_PERIOD
import yacov.sapiashvili.polykov.utils.Constants.DEFAULT_RECTANGLE_ANIMATION
import yacov.sapiashvili.polykov.utils.Constants.DEFAULT_VERTEXES
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor (private val repository: ConfigurationRepository)
:ViewModel(){
    private val TAG = "MainViewModel"
    private val _error = MutableLiveData<String>()
    private val _numberOfSides = MutableLiveData<Int>().apply {
        value = DEFAULT_VERTEXES
    }
    private val _rotationDegree = MutableLiveData<Int>().apply {
        value = DEFAULT_DEGREES
    }
    private val _animationInterval = MutableLiveData<Int>().apply {
        value = DEFAULT_RECTANGLE_ANIMATION
    }

    val numberOfSides: LiveData<Int> = _numberOfSides
    val rotationDegree: LiveData<Int> = _rotationDegree
    val animationInterval: LiveData<Int> = _animationInterval
    val errorMessage : LiveData<String> = _error

    init {
        getConfigurations()
    }
    private fun resetToDefaultsValues(){
        _rotationDegree.postValue(DEFAULT_DEGREES)
        _numberOfSides.postValue(DEFAULT_VERTEXES)
        _animationInterval.postValue(DEFAULT_RECTANGLE_ANIMATION)
    }
    private fun getConfigurations(){
        viewModelScope.launch (Dispatchers.IO){
            var periodDelay = DEFAULT_PERIOD
            try {
                repository.getConfigurations().let { response ->
                    if (response.isSuccessful) {
                        _rotationDegree.postValue(response.body()!!.polygonDegreesToRotate)
                        _numberOfSides.postValue(response.body()!!.polygonNumberOfVertices)
                        _animationInterval.postValue(response.body()!!.rectangleAnimationDuration)
                        periodDelay = response.body()!!.period * 100
                    } else {
                        _error.postValue("error ${response.code()} received while getting Configurations from server resting to default configurations")
                        Log.d(TAG, "error: ${response.code()} message : ${response.message()}")
                        resetToDefaultsValues()
                    }

                }
            } catch (e: HttpException) {
                Log.d(TAG, "error: HttpException : ${e.message()}")
                _error.postValue("${e.message()} error received while getting Configurations from server resting to default configurations")
                resetToDefaultsValues()
            } catch (e: IOException) {
                //handles no internet exception
                Log.d(TAG, "error:IOException : ${e.message}")
                _error.postValue("${e.message} error received while getting Configurations from server resting to default configurations")
                resetToDefaultsValues()
            }
            delay(periodDelay.toLong())
            getConfigurations()
        }

    }
}