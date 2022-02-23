package yacov.sapiashvili.polykov.ui.screenthree

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ScreenThreeViewModel : ViewModel() {

    private val _button1state = MutableLiveData<Boolean>().apply {value = Random.nextBoolean()}
    private val _button2state = MutableLiveData<Boolean>().apply {value = Random.nextBoolean()}
    private val _button3state = MutableLiveData<Boolean>().apply {value = Random.nextBoolean()}
    private val _button4state = MutableLiveData<Boolean>().apply {value = Random.nextBoolean()}
    private val _button5state = MutableLiveData<Boolean>().apply {value = Random.nextBoolean()}

    val button1state: LiveData<Boolean> = _button1state
    val button2state: LiveData<Boolean> = _button2state
    val button3state: LiveData<Boolean> = _button3state

    val button4state: LiveData<Boolean> = _button4state
    val button5state: LiveData<Boolean> = _button5state

    init {
        randomizeClickableButtons()
    }
    fun randomizeClickableButtons(){
        viewModelScope.launch {
            delay(5000)
            _button1state.value = Random.nextBoolean()
            _button2state.value = Random.nextBoolean()
            _button3state.value = Random.nextBoolean()
            _button4state.value = Random.nextBoolean()
            _button5state.value = Random.nextBoolean()
            randomizeClickableButtons()
        }
    }
}