package com.chillieman.chilliewallet.ui.wallet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.ui.barcode.BarcodeActivity
import com.chillieman.chilliewallet.ui.base.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_wallet.*

class WalletFragment : BaseViewModelFragment<WalletViewModel>(WalletViewModel::class.java) {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wallet, container, false)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            btn_launch_barcode_activity.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_launch_barcode_activity.setOnClickListener {
            startActivity(Intent(requireActivity(), BarcodeActivity::class.java))
        }
    }
}