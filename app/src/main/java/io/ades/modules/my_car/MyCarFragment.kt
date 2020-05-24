package io.ades.modules.my_car

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.ades.R

class MyCarFragment : Fragment() {

    companion object {
        fun newInstance() = MyCarFragment()
    }

    private lateinit var viewModel: MyCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_car_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyCarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
