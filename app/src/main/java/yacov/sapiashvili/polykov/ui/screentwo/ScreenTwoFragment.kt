package yacov.sapiashvili.polykov.ui.screentwo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yacov.sapiashvili.polykov.R
import yacov.sapiashvili.polykov.ui.BaseFragment
import yacov.sapiashvili.polykov.ui.customviews.rectangle.RectangleView

class ScreenTwoFragment : BaseFragment() {
    private val rootSet = AnimatorSet()
    private var animationInterval : Long = 1000L
    private lateinit var screenTwoViewModel: ScreenTwoViewModel
    lateinit var rectangleView : RectangleView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startViewAnimation()
    }
    override fun getLayoutResourceID(): Int {
        return R.layout.fragment_screen_2
    }

    override fun initUiElements(root: View) {
        screenTwoViewModel = ViewModelProvider(this).get(ScreenTwoViewModel::class.java)
        rectangleView = root.findViewById(R.id.rectangleView)
    }
    override fun observeViewModel() {
        screenTwoViewModel.animationInterval.observe(viewLifecycleOwner, Observer {
            animationInterval = it.toLong()
        })
    }
    private fun startViewAnimation() {
        screenTwoViewModel.viewModelScope.launch {
            delay(animationInterval)
            val scaleX = ObjectAnimator.ofFloat(rectangleView, "scaleX", 0.5f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(rectangleView, "scaleY", 0.5f, 1.0f)
            val scaleRX = ObjectAnimator.ofFloat(rectangleView, "scaleX", 1.0f, 0.5f)
            val scaleRY = ObjectAnimator.ofFloat(rectangleView, "scaleY", 1.0f, 0.5f)
            val growSet = AnimatorSet()
            growSet.playTogether(scaleX, scaleY)
            val shrinkSet = AnimatorSet()
            shrinkSet.playTogether(scaleRX, scaleRY)
            rootSet.play(growSet).after(shrinkSet)
            rootSet.duration = 1000
            rootSet.start()
            rootSet.doOnEnd { startViewAnimation() }
        }
    }
}