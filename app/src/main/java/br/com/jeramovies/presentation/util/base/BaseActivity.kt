package br.com.jeramovies.presentation.util.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.jeramovies.domain.entity.DialogData
import br.com.jeramovies.presentation.util.livedata.observe
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
        baseViewModel.dialog.observe(this) { showDialog(it) }
        baseViewModel.goTo.observe(this) { navigateTo(it) }
        baseViewModel.toast.observe(this) { (msg, duration) -> showToast(msg, duration) }
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

    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).apply { show() }
    }
}