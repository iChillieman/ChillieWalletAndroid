package com.chillieman.chilliewallet.ui.auth.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentPinBinding
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment

class PinFragment : BaseViewModelFragment<PinViewModel>(PinViewModel::class.java) {
    private var isNewPin: Boolean? = null
    private var messageTitle: String? = null

    private var _binding: FragmentPinBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isNewPin = it.getBoolean(ARG_IS_NEW_PIN)
            messageTitle = it.getString(ARG_TITLE_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Use ViewModel!

        return root
    }

    interface Listener {
        fun onPinResponse(response: AuthResponse)
    }

    companion object {
        private const val ARG_IS_NEW_PIN = "is_new_pin"
        private const val ARG_TITLE_MESSAGE = "title"

        @JvmStatic
        fun newInstance(isNewPin: Boolean, title: String) =
            PinFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_NEW_PIN, isNewPin)
                    putString(ARG_TITLE_MESSAGE, title)
                }
            }
    }
}