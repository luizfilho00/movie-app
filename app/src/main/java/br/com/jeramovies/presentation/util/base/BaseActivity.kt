package br.com.jeramovies.presentation.util.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.jeramovies.domain.entity.DialogData
import br.com.jeramovies.presentation.util.navigation.NavData

abstract class BaseActivity : AppCompatActivity() {

    abstract val baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected open fun subscribeUi() {
        baseViewModel.dialog.observe(this, Observer { showDialog(it) })
        baseViewModel.goTo.observe(this, Observer { navigateTo(it) })
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

    private fun navigateTo(navData: NavData) {
        navData.navigate(this)
    }
}