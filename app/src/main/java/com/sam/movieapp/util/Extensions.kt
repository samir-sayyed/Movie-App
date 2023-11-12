package com.sam.movieapp.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sam.movieapp.R


var loadingDialog: Dialog? = null

fun Fragment.showLoadingDialog(
    cancelable: Boolean = true,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return MaterialAlertDialogBuilder(context ?: return null).apply {
        setView(R.layout.layout_loading_dialog)
    }.create().let { dialog ->
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                dialog.dismiss()
                if (loadingDialog === dialog) {
                    loadingDialog = null
                }
            }
        })
        loadingDialog = dialog
        dialog.show()
        dialog
    }
}

fun dismissLoadingDialog() {
    if (loadingDialog?.isShowing == true) {
        loadingDialog?.dismiss()
    }
}
