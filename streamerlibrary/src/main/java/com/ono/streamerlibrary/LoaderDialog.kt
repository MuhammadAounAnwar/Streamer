package com.ono.streamerlibrary

import android.app.ProgressDialog
import android.content.Context

class LoaderDialog(val context: Context) {

//    val dialog = ProgressDialog(context)


    private val dialog by lazy {
        ProgressDialog(context)
    }

    fun createProgressDialog() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Loading")
        dialog.setMessage("Please wait...")
        dialog.isIndeterminate = true
        dialog.setCanceledOnTouchOutside(false)
    }

    fun showLoadingDialog() {
        dialog.show()
    }

    fun hideLoadingDialog() {
        dialog.dismiss()
    }


}