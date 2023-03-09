package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentMarsCuriosityBinding
import com.geekbrain.android.nasa_api.viewmodel.AppStateMarsCuriosity
import com.geekbrain.android.nasa_api.viewmodel.MarsCuriocityViewModel
import kotlin.random.Random
import kotlin.random.nextInt

class MarsCuriosityFragment : ViewBindingFragment<FragmentMarsCuriosityBinding>
    (FragmentMarsCuriosityBinding::inflate) {
    private val TAG = "MarsCuriosityFragment"


    // This property is only valid between onCreateView and
    // onDestroyView.

    private val viewModel by viewModels<MarsCuriocityViewModel>()


    companion object {
        val rnd = Random(12)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderDataFromNasa(it)
        }

        viewModel.sendRequest()
    }

    private fun renderDataFromNasa(appStateMarsCuriosity: AppStateMarsCuriosity) {
        when (appStateMarsCuriosity) {
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

                val number = rnd
                    .nextInt(0..appStateMarsCuriosity.marsCuriosityResponse.photos.size)
                Log.i(TAG, "renderDataFromNasa: imageRandomNumber = $number")


                var imageSource = appStateMarsCuriosity.marsCuriosityResponse.photos[number].imgSrc
                Log.i(TAG, "renderDataFromNasa before: $imageSource")
                if (imageSource.contains("http://")) {
                    imageSource = imageSource.replace("http", "https")
                }
                Log.i(TAG, "renderDataFromNasa: $imageSource")

                binding.marsCuriosityImageView
                    .load(imageSource) {
                        error(R.drawable.error)
                        placeholder(R.drawable.bg_mars)
                    }
            }
        }
    }


}