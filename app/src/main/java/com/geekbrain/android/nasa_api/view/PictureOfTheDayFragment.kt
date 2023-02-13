package com.geekbrain.android.nasa_api.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import coil.dispose
import coil.load
import com.geekbrain.android.nasa_api.MainActivity
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentPictureOfTheDayBinding
import com.geekbrain.android.nasa_api.viewmodel.AppState
import com.geekbrain.android.nasa_api.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayFragment : Fragment() {

    private val TAG = "pictureOfTheDayFragment"

    private var _binding: FragmentPictureOfTheDayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel by viewModels<PictureOfTheDayViewModel>()

    companion object {
        fun newInstance() = PictureOfTheDayFragment()

    }

    enum class DAYS(val offset: Int) {
        BEFORE_YESTERDAY(-2),
        YESTERDAY(-1),
        TODAY(0),
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        setHasOptionsMenu(true)*/

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { renderDateFromNasa(it) }

        viewModel.sendRequest()

        binding.chiptoday.setOnClickListener {
            viewModel.sendRequest()
        }

        binding.chipyesterday.setOnClickListener {
            val selectedDay = getSelectedDay(DAYS.YESTERDAY)
            viewModel.sendRequest(selectedDay)
        }

        binding.chipbeforeyesterday.setOnClickListener{
            val selectedDay = getSelectedDay(DAYS.BEFORE_YESTERDAY)
            viewModel.sendRequest(selectedDay)
        }

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.input.text.toString()}")
            })
        }

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_bar_favorite->{

            }

            R.id.action_bar_settings ->{
                //TODO открыть фрагмент настроек

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedDay(day: DAYS): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, day.offset)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return dateFormat.format(calendar.time)
    }

    private fun renderDateFromNasa(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.imageView.dispose()
                Toast.makeText(activity, appState.error.toString(), Toast.LENGTH_LONG).show()
            }
            AppState.Loading -> {
                binding.imageView.visibility = View.GONE
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                
            }
            is AppState.Success -> {
                binding.imageView.visibility = View.VISIBLE
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.imageView.load(appState.pictureOfTheDayResponseData.url){
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_hamburger_menu_bottom_bar)
                }

                //TODO настроить загрузку изображения error() placeholder()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}