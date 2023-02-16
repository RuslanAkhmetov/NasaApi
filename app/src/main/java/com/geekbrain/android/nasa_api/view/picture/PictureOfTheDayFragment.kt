package com.geekbrain.android.nasa_api.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import coil.dispose
import coil.load
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentPictureOfTheDayBinding
import com.geekbrain.android.nasa_api.utils.DAYS
import com.geekbrain.android.nasa_api.view.drawer.BottomNavigationDrawerFragment
import com.geekbrain.android.nasa_api.view.settings.SettingsFragment
import com.geekbrain.android.nasa_api.viewmodel.AppState
import com.geekbrain.android.nasa_api.viewmodel.PictureOfTheDayViewModel
import com.geekbrain.android.nasa_api.utils.getSelectedDay
import java.util.*


class PictureOfTheDayFragment : Fragment() {

    private val TAG = "pictureOfTheDayFragment"

    private var _binding: FragmentPictureOfTheDayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<PictureOfTheDayViewModel>()

    private var selectedDay = ""

    companion object {

        fun newInstance() = PictureOfTheDayFragment()
        fun newInstance(day: DAYS): PictureOfTheDayFragment {
            val fragment = newInstance()
            fragment.selectedDay = getSelectedDay(day)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: ")

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { renderDateFromNasa(it) }

        if (selectedDay == "") {
            viewModel.sendRequest()
        } else {
            viewModel.sendRequest(selectedDay)
        }

        binding.chiptoday.setOnClickListener {
            viewModel.sendRequest()
        }

        binding.chipyesterday.setOnClickListener {
            val selectedDay = getSelectedDay(DAYS.YESTERDAY)
            viewModel.sendRequest(selectedDay)
        }

        binding.chipbeforeyesterday.setOnClickListener {
            val selectedDay = getSelectedDay(DAYS.BEFORE_YESTERDAY)
            viewModel.sendRequest(selectedDay)
        }

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.input.text.toString()}")
            })
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar_favorite -> {

            }

            R.id.action_bar_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .hide(this)
                    .add(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "TAG")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun renderDateFromNasa(responseAppState: AppState) {
        when (responseAppState) {
            is AppState.Error -> {
                binding.pictureOfTheDayImageView.dispose()
                Toast.makeText(activity, responseAppState.error.toString(), Toast.LENGTH_LONG)
                    .show()
            }
            AppState.Loading -> {
                binding.pictureOfTheDayImageView.visibility = View.GONE
                binding.explanationTextView.visibility = View.GONE
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE

            }
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.pictureOfTheDayImageView.visibility = View.VISIBLE
                binding.explanationTextView.visibility = View.VISIBLE
                binding.pictureOfTheDayImageView.load(responseAppState.pictureOfTheDayResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_hamburger_menu_bottom_bar)
                }
                binding.explanationTextView.text =
                    responseAppState.pictureOfTheDayResponseData.explanation

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}