package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentMarsCuriosityBinding
import com.geekbrain.android.nasa_api.viewmodel.AppStateMarsCuriosity
import com.geekbrain.android.nasa_api.viewmodel.MarsCuriocityViewModel
import kotlin.random.Random
import kotlin.random.nextInt

class MarsCuriosityFragment: SpaceFragment() {
    private val TAG = "MarsCuriosityFragment"

    private var _binding: FragmentMarsCuriosityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<MarsCuriocityViewModel>()


    companion object {
        fun newInstance() = MarsCuriosityFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsCuriosityBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getLiveData().observe(viewLifecycleOwner){
            renderDataFromNasa(it)
        }

        viewModel.sendRequest()
    }

    private fun renderDataFromNasa(appStateMarsCuriosity: AppStateMarsCuriosity){
        when(appStateMarsCuriosity){
            is AppStateMarsCuriosity.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                binding.marsCuriosityImageView.visibility = View.GONE
            }
            is AppStateMarsCuriosity.Error -> {
                Toast.makeText(activity, "Something wrong", Toast.LENGTH_LONG).show()
            }
            is AppStateMarsCuriosity.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.marsCuriosityImageView.visibility = View.VISIBLE
                val number = Random(10)
                    .nextInt(0..appStateMarsCuriosity.marsCuriosityResponse.photos.size)
                Log.i(TAG, "renderDataFromNasa: randomNumber = $number")
                Log.i(TAG, "renderDataFromNasa: ${appStateMarsCuriosity.marsCuriosityResponse.photos[0].imgSrc}")
                binding.marsCuriosityImageView
                    .load(R.drawable.mars_curiosity){                                //Найти кспособ конвертировать jfif файлы
                        Log.i(TAG, "renderDataFromNasa: ")
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
            }
        }
    }
}