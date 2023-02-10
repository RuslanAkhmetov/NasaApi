package com.geekbrain.android.nasa_api.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.geekbrain.android.nasa_api.databinding.FragmentPictureOfTheDayBinding
import com.geekbrain.android.nasa_api.viewmodel.AppState
import com.geekbrain.android.nasa_api.viewmodel.PictureOfTheDayViewModel


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel by viewModels<PictureOfTheDayViewModel>()

companion object{
    fun newInstance() = PictureOfTheDayFragment()

}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner
        ) { renderDateFromNasa(it) }

        viewModel.sendRequest()

        binding.chipyesterday.setOnClickListener{
            Toast.makeText(requireContext(), "chipYesterday", Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderDateFromNasa(appState: AppState) {
        when (appState) {
            is AppState.Error -> {}
            AppState.Loading -> {}
            is AppState.Success -> {
                binding.imageView.load(appState.pictureOfTheDayResponseData.url)
                    //TODO настроить загрузку изображения error() placeholder()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}