package com.chillieman.chilliewallet.ui.auth.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillieman.chilliewallet.databinding.FragmentPinBinding
import com.chillieman.chilliewallet.model.AuthResponse
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseViewModelFragment<PasswordViewModel>(PasswordViewModel::class.java) {
    private var isNewPassword: Boolean? = null
    private var messageTitle: String? = null

    private var _binding: FragmentPinBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener {
        fun onPasswordResponse(response: AuthResponse)
    }

    companion object {
        private const val ARG_IS_NEW_PASSWORD = "is_new_password"
        private const val ARG_TITLE_MESSAGE = "title"

        @JvmStatic
        fun newInstance(isNewPassword: Boolean, title: String) =
            PasswordFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_NEW_PASSWORD, isNewPassword)
                    putString(ARG_TITLE_MESSAGE, title)
                }
            }
    }
}
