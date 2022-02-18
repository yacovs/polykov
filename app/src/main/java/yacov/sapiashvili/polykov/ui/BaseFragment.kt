package yacov.sapiashvili.polykov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutResourceID(), container, false)
        initUiElements(rootView)
        observeViewModel()
        return rootView
    }
    @LayoutRes
    abstract fun getLayoutResourceID(): Int

    abstract fun initUiElements(root: View)
    abstract fun observeViewModel()
}