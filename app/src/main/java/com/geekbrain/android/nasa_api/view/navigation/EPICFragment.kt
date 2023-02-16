package com.geekbrain.android.nasa_api.view.navigation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import coil.load
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentEpicBinding
import com.geekbrain.android.nasa_api.viewmodel.AppStateEPIC
import com.geekbrain.android.nasa_api.viewmodel.EPICViewModel
import java.util.*


class EPICFragment : Fragment() {

    private val TAG = "EPICFragment"

    private var _binding: FragmentEpicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<EPICViewModel>()


    companion object {

        fun newInstance() = EPICFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: ")

        _binding = FragmentEpicBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { renderDateFromNasa(it) }

        viewModel.sendRequest()
    }





    private fun renderDateFromNasa(responseAppState: AppStateEPIC) {
        when (responseAppState) {
            is AppStateEPIC.Error -> {

                Toast.makeText(activity, responseAppState.error.toString(), Toast.LENGTH_LONG)
                    .show()
            }
            AppStateEPIC.Loading -> {
                binding.epicImageView.visibility = View.GONE
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE

            }
            is AppStateEPIC.Success -> {

                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.epicImageView.visibility = View.VISIBLE
                binding.epicImageView.load(viewModel.generateImageURL(responseAppState)) {
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_hamburger_menu_bottom_bar)
                }


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}