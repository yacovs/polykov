package yacov.sapiashvili.polykov.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yacov.sapiashvili.polykov.R
import yacov.sapiashvili.polykov.ui.customviews.rectangle.RectangleView
import yacov.sapiashvili.polykov.ui.viewmodels.MainViewModel

class ScreenTwoFragment : BaseFragment() {
    private val rootSet = AnimatorSet()
    private lateinit var viewModel: MainViewModel
    lateinit var rectangleView : RectangleView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startViewAnimation()
    }
    override fun getLayoutResourceID(): Int {
        return R.layout.fragment_screen_2
    }

    override fun initUiElements(root: View) {
        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        rectangleView = root.findViewById(R.id.rectangleView)
    }
    override fun observeViewModel() {
        viewModel.animationInterval.observe(viewLifecycleOwner, Observer {
            startViewAnimation()
        })
    }
    private fun startViewAnimation() {
        viewModel.viewModelScope.launch {
            viewModel.animationInterval.value?.let { delay( it.toLong()) }
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