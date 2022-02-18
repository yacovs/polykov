package yacov.sapiashvili.polykov.ui.screenone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenOneViewModel : ViewModel() {
    private val _numberOfSides = MutableLiveData<Int>().apply {
        value = 3
    }
    private val _rotationDegree = MutableLiveData<Int>().apply {
        value = 10
    }
    val numberOfSides: LiveData<Int> = _numberOfSides
    val rotationDegree: LiveData<Int> = _rotationDegree
}