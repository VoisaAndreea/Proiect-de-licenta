package com.example.foodsuggestions.navigationComponent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodsuggestions.R
import com.example.foodsuggestions.databinding.FragmentRegisterBinding
import com.example.foodsuggestions.viewmodels.RegisterViewModel

class RegisterFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerViewModel = viewModel
        }

        viewModel.navigateToLoginEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loginNow)
        }

        viewModel.fullNameError.observe(viewLifecycleOwner) { error ->
            if (error)
                binding.idRName.error = getString(R.string.fullNameErrorMessage)
            else
                binding.idRName.error = null
        }

        viewModel.mailError.observe(viewLifecycleOwner) { error ->
            if (error)
                binding.idREmail.error = getString(R.string.mailErrorMassage)
            else
                binding.idREmail.error = null
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            if (error)
                binding.idRPassword.error = getString(R.string.passwordErrorMassage)
            else
                binding.idRPassword.error = null
        }

        viewModel.confirmPasswordError.observe(viewLifecycleOwner) { error ->
            if (error)
                binding.idRConfPassword.error = getString(R.string.confirmPasswordErrorMessage)
            else
                binding.idRConfPassword.error = null
        }

        textChanged()


        viewModel.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                viewModel.status.postValue(null)
                Toast.makeText(activity, getString(R.string.registerMessage), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun textChanged() {
        binding.idRName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.fullNameError.postValue(false)
            }
        })

        binding.idREmail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.mailError.postValue(false)
            }
        })

        binding.idRPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.passwordError.postValue(false)
            }
        })

        binding.idRConfPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.confirmPasswordError.postValue(false)
            }
        })
    }
}