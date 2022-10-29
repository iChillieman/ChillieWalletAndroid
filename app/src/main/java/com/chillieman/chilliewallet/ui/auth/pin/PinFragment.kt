package com.chillieman.chilliewallet.ui.auth.pin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.databinding.FragmentPinBinding
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinFragment : BaseViewModelFragment<PinViewModel>(PinViewModel::class.java) {
    private var isNewPin: Boolean? = null
    private var messageHint: Int? = null
    private lateinit var listener: Listener

    private var _binding: FragmentPinBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isNewPin = it.getBoolean(ARG_IS_NEW_PIN)
            messageHint = it.getInt(ARG_HINT_MESSAGE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setHint() {
        binding.etPincode.setHint(messageHint ?: R.string.pin)
    }

    private fun showError(textId: Int) {
        binding.tvError.setText(textId)
        binding.tvError.visibility = View.VISIBLE
        setHint()
        reset()
    }

    private fun reset() {
        binding.etPincode.setText("")
    }

    fun onBackPressed(): Boolean {
        val etPin = binding.etPincode
        val length = etPin.length()
        if (length > 0) {
            etPin.text.delete(length - 1, length)
            return false
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHint()

        binding.etPincode.addTextChangedListener {
            it?.let { editable ->
                if (editable.length == 4) {
                    Log.d(TAG, "Okay!")
                    viewModel.processesPin(editable.toString(), isNewPin)
                }
            }

        }

        viewModel.errorState.observe(viewLifecycleOwner) {
            when (it) {
                PinErrorState.WRONG_PIN -> {
                    showError(R.string.pin_error_incorrect)
                }
                PinErrorState.PINS_DO_NOT_MATCH -> {
                    showError(R.string.pin_error_do_not_match)
                }
                PinErrorState.NONE -> binding.tvError.visibility = View.GONE
                else -> Unit
            }
        }

        viewModel.isPinStored.observe(viewLifecycleOwner) {
            if (it) {
                binding.etPincode.setHint(R.string.pin_new_confirm)
                reset()
            }
        }

        viewModel.authResponse.observe(viewLifecycleOwner) {
            listener.onPinResponse(it)
        }

        return root
    }


    override fun onResume() {
        super.onResume()
        binding.etPincode.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etPincode, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setListener(newListener: Listener) {
        listener = newListener
    }

    interface Listener {
        fun onPinResponse(response: AuthResponse)
    }

    companion object {
        private const val ARG_IS_NEW_PIN = "is_new_pin"
        private const val ARG_HINT_MESSAGE = "hint"

        private const val TAG = "ChilliePinFragment"

        @JvmStatic
        fun newInstance(isNewPin: Boolean, listener: Listener) =
            PinFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_NEW_PIN, isNewPin)
                    if (isNewPin) {
                        putInt(ARG_HINT_MESSAGE, R.string.pin_new)
                    } else {
                        putInt(ARG_HINT_MESSAGE, R.string.pin_enter)
                    }
                }

                setListener(listener)
            }
    }
}
