package com.imbactbulletz.robotpattern.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null

    private val binding: FragmentDashboardBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpListeners()
        observeScreenState()
        observeScreenEvent()
    }

    private fun setUpListeners() = with(binding) {
        decrementCounterButton.setOnClickListener { viewModel.onDecrementCounter() }
        incrementCounterButton.setOnClickListener { viewModel.onIncrementCounter() }
    }

    private fun observeScreenState() {
        viewModel.dashboardScreenState.observe(viewLifecycleOwner) { screenState ->
            when(screenState) {
                is DashboardScreenState.Counter -> {
                    binding.counterTextView.text = String.format(
                        getString(R.string.counter_value_template),
                        screenState.value
                    )
                }
            }
        }
    }

    private fun observeScreenEvent() {
        viewModel.dashboardScreenEvent.observe(viewLifecycleOwner) { screenEvent ->
            when(screenEvent) {
                is DashboardScreenEvent.Error -> {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(screenEvent.errorMessageResource),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}