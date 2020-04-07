package br.com.devroid.domain.entity

data class DialogData(
    val message: String,
    val title: String? = null,
    val confirmText: String? = null,
    val cancelText: String? = null,
    val onCancel: (() -> Unit)? = null,
    val onConfirm: (() -> Unit)? = null
)