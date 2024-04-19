package com.do55anto5.taskapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.do55anto5.taskapp.R
import com.do55anto5.taskapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: String,
    onClick: () -> Unit = {}
) {

    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.bottomSheetDialog)
    val bind: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    bind.txtTitle.text = getText(titleDialog?: R.string.title_warning)
    bind.txtMessage.text = message
    bind.btnOk.text = getText(titleButton?: R.string.btn_warning)
    bind.btnOk.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }
    bottomSheetDialog.setContentView(bind.root)
    bottomSheetDialog.show()
}