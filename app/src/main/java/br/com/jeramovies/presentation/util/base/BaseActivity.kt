package br.com.jeramovies.presentation.util.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.jeramovies.domain.entity.DialogData

abstract class BaseActivity : AppCompatActivity() {

    abstract val baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUi()
    }

    protected open fun subscribeUi() {
        baseViewModel.dialog.observe(this, Observer { showDialog(it) })
    }

    private fun showDialog(dialogData: DialogData) {
        AlertDialog.Builder(this)
            .setMessage(dialogData.message)
            .setTitle(dialogData.title)
            .setPositiveButton(dialogData.confirmText) { _, _ ->
                dialogData.onConfirm?.invoke()
            }
            .setNegativeButton(dialogData.cancelText) { _, _ ->
                dialogData.onCancel?.invoke()
            }
            .create()
            .show()
    }
}