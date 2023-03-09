package com.geekbrain.android.nasa_api.view.picture


import android.content.Intent
import android.net.Uri
import android.os.*
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.dispose
import coil.load
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentPictureOfTheDayBinding
import com.geekbrain.android.nasa_api.view.picture.utils.DAYS
import com.geekbrain.android.nasa_api.view.picture.utils.getSelectedDay
import com.geekbrain.android.nasa_api.viewmodel.AppState
import com.geekbrain.android.nasa_api.viewmodel.PictureOfTheDayViewModel


class PictureOfTheDayFragment : Fragment() {

    private val TAG = "pictureOfTheDayFragment"

    private var isExpanded = false
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
        setHasOptionsMenu(true)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if(isAdded)    //проверяем не умер ли фрагмент
                tutorial()
        }, 500)

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { renderDateFromNasa(it) }

        if (selectedDay == "") {
            viewModel.sendRequest()
        } else {
            viewModel.sendRequest(selectedDay)
        }



        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.input.text.toString()}")
            })
        }


        binding.pictureOfTheDayImageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(binding.root)
            val params = it.layoutParams as ConstraintLayout.LayoutParams

            if (isExpanded) {
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }


    }

    private fun tutorial() {


    }


    @RequiresApi(Build.VERSION_CODES.Q)
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
                    error(R.drawable.error)
                    placeholder(R.drawable.bg_system)
                }

                val textExplanation = responseAppState.pictureOfTheDayResponseData.explanation

                var spannableString = SpannableString(textExplanation)

                binding.explanationTextView.setText(
                    spannableString,
                    TextView.BufferType.SPANNABLE
                )

                spannableString = binding.explanationTextView.text as SpannableString


            }
        }
    }

    private fun rainbow(
        firstColor: Int,
        textExplanation: String,
        spannableStringBuilder: SpannableString
    ) {
        val ColorList = listOf(
            ContextCompat.getColor(requireContext(), R.color.red),
            ContextCompat.getColor(requireContext(), R.color.orange),
            ContextCompat.getColor(requireContext(), R.color.yellow),
            ContextCompat.getColor(requireContext(), R.color.green),
            ContextCompat.getColor(requireContext(), R.color.light_blue),
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.purple),
        )

        var currentColor = firstColor
        val timer = object : CountDownTimer(20000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                paintText(currentColor, textExplanation, spannableStringBuilder, ColorList)
                currentColor = ++currentColor % ColorList.size
            }

            override fun onFinish() {
                rainbow(currentColor, textExplanation, spannableStringBuilder)
            }
        }
        timer.start()

    }

    private fun paintText(
        firstColor: Int,
        textExplanation: String,
        spannableStringBuilder: SpannableString,
        ColorList: List<Int>
    ) {
        val spanArray = spannableStringBuilder.getSpans(
            0, spannableStringBuilder.length,
            ForegroundColorSpan::class.java
        )

        for (span in spanArray) {
            spannableStringBuilder.removeSpan(span)
        }

        for (i in textExplanation.indices) {
            val color: Int = ColorList[(i + firstColor) % ColorList.size]
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(color),
                i, i + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



