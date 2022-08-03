package com.imbactbulletz.robotpattern.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()


    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        observeScreenState()
    }

    private fun setUpListeners() = with(binding) {
        usernameEditText.addTextChangedListener(AfterTextChangedListener { text ->
            viewModel.onUsernameEntered(text)
        })

        passwordEditText.addTextChangedListener(AfterTextChangedListener { text ->
            viewModel.onPasswordEntered(text)
        })

        loginButton.setOnClickListener { viewModel.onLoginClicked() }
    }

    private fun observeScreenState() {
        viewModel.loginScreenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is LoginScreenState.SuccessfulLogin -> {
                    findNavController()
                        .navigate(R.id.action_LoginFragment_to_dashboardFragment)
                }

                is LoginScreenState.FailedLogin -> {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(screenState.errorMessageResource),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}