package yacov.sapiashvili.polykov.ui.screenthree

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import yacov.sapiashvili.polykov.R
import yacov.sapiashvili.polykov.ui.BaseFragment

class ScreenThreeFragment : BaseFragment() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox

    private lateinit var screenThreeViewModel: ScreenThreeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenThreeViewModel.randomizeClickableButtons()
    }
    override fun getLayoutResourceID(): Int {
        return R.layout.fragment_screen_3
    }

    override fun observeViewModel() {
        screenThreeViewModel = ViewModelProvider(this).get(ScreenThreeViewModel::class.java)
        screenThreeViewModel.button1state.observe(viewLifecycleOwner, Observer {
            checkBox1.isChecked = it
        })
        screenThreeViewModel.button2state.observe(viewLifecycleOwner, Observer {
            checkBox2.isChecked = it
        })
        screenThreeViewModel.button3state.observe(viewLifecycleOwner, Observer {
            checkBox3.isChecked = it
        })
        screenThreeViewModel.button4state.observe(viewLifecycleOwner, Observer {
            checkBox4.isChecked = it
        })
        screenThreeViewModel.button5state.observe(viewLifecycleOwner, Observer {
            checkBox5.isChecked = it
        })
    }

    override fun initUiElements(root: View){
        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);
        button3 = root.findViewById(R.id.button3);
        button4 = root.findViewById(R.id.button4);
        button5 = root.findViewById(R.id.button5);
        checkBox1 = root.findViewById(R.id.checkBox1);
        checkBox2 = root.findViewById(R.id.checkBox2);
        checkBox3 = root.findViewById(R.id.checkBox3);
        checkBox4 = root.findViewById(R.id.checkBox4);
        checkBox5 = root.findViewById(R.id.checkBox5);

        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            setButtonEnabled(button1,isChecked)
        }
        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            setButtonEnabled(button2, isChecked)
        }
        checkBox3.setOnCheckedChangeListener { _, isChecked ->
            setButtonEnabled(button3,isChecked)
        }
        checkBox4.setOnCheckedChangeListener { _, isChecked ->
            setButtonEnabled(button4,isChecked)
        }
        checkBox5.setOnCheckedChangeListener { _, isChecked ->
            setButtonEnabled(button5,isChecked)
        }

    }
   private fun setButtonEnabled(button: Button, isChecked:Boolean){
       button.isEnabled = isChecked
    }
}