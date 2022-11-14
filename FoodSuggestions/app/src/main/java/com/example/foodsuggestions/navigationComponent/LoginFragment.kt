package com.example.foodsuggestions.navigationComponent

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodsuggestions.R
import com.example.foodsuggestions.databinding.FragmentLoginBinding
import com.example.foodsuggestions.main.HomeActivity
import com.example.foodsuggestions.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginViewModel = viewModel
        }

        viewModel.navigateToRegisterEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_registerNow, null)
        }

        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner) {
            activity?.let {
                it.startActivity( Intent(it, HomeActivity::class.java) )
            }
        }

        viewModel.mailError.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.idLEmail.error = getString(R.string.mailErrorMassage)
            } else {
                binding.idLEmail.error = null
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.idLPassword.error = getString(R.string.passwordErrorMassage)
            } else {
                binding.idLPassword.error = null
            }
        }

        textChanged()

        viewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                viewModel.status.postValue(null)
                Toast.makeText(activity, getString(R.string.loginMessage), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun textChanged() {
        binding.idLPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.passwordError.postValue(false)
            }

        })

        binding.idLEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.mailError.postValue(false)
            }

        })
    }
}