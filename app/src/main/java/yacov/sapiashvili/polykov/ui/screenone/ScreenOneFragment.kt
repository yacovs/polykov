package yacov.sapiashvili.polykov.ui.screenone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yacov.sapiashvili.polykov.R
import yacov.sapiashvili.polykov.ui.BaseFragment
import yacov.sapiashvili.polykov.ui.customviews.polygon.PolygonAnimationProgress
import yacov.sapiashvili.polykov.ui.customviews.polygon.PolygonView


class ScreenOneFragment : BaseFragment() , PolygonAnimationProgress {

    private lateinit var screenOneViewModel: ScreenOneViewModel
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
        screenOneViewModel = ViewModelProvider(this).get(ScreenOneViewModel::class.java)
        textView = root.findViewById(R.id.text_home)
        polygonView = root.findViewById(R.id.polykov)
        polygonView.setAnimationCallback(this)
    }
    override fun observeViewModel() {
        screenOneViewModel.numberOfSides.observe(viewLifecycleOwner, Observer {
            polygonView.numberOfPoint = it
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
        super.onDestroyView()
        polygonView.mAnimateOnDisplay = false
    }
}