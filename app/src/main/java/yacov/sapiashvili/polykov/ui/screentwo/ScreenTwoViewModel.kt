package yacov.sapiashvili.polykov.ui.screentwo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenTwoViewModel : ViewModel() {
    private val DEFAULT_INTERVAL = 10000
    private val _animationInterval = MutableLiveData<Int>().apply {
        value = DEFAULT_INTERVAL
    }
    val animationInterval: LiveData<Int> = _animationInterval
}