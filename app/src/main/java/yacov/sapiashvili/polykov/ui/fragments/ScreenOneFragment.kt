package yacov.sapiashvili.polykov.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yacov.sapiashvili.polykov.R
import yacov.sapiashvili.polykov.ui.customviews.polygon.PolygonAnimationProgress
import yacov.sapiashvili.polykov.ui.customviews.polygon.PolygonView
import yacov.sapiashvili.polykov.ui.viewmodels.MainViewModel


class ScreenOneFragment : BaseFragment() , PolygonAnimationProgress {

    private lateinit var screenOneViewModel: MainViewModel
    lateinit var polygonView: PolygonView
    lateinit var textView: TextView
    val TAG = "Shape"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startViewAnimation()
    }

    override fun getLayoutResourceID(): Int {
        return R.layout.fragment_screen_1
    }

    override fun initUiElements(root: View) {
        screenOneViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        textView = root.findViewById(R.id.text_home)
        polygonView = root.findViewById(R.id.polykov)
        polygonView.setAnimationCallback(this)
    }
    override fun observeViewModel() {
        screenOneViewModel.numberOfSides.observe(viewLifecycleOwner, Observer {
            polygonView.generatePoly(numberOfPoint = it)
        })
        screenOneViewModel.rotationDegree.observe(viewLifecycleOwner, Observer {
            polygonView.targetDegreeOfRotation = it.toFloat()
        })
    }

    private fun startViewAnimation(){
        screenOneViewModel.viewModelScope.launch {
            delay(1000)
            screenOneViewModel.rotationDegree.value?.let { polygonView.startRotation(it.toFloat()) };
        }
    }

    override fun animationComplete() {
        startViewAnimation();
    }

    override fun onDestroyView() {
        polygonView.mAnimateOnDisplay = false
        super.onDestroyView()
    }
}