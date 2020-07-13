package com.code_remote.venuedemo.utilities

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

/**
 * A utility function which offers a clean alternative to using [EditText.setOnEditorActionListener]
 * @param imeActionId the id of an IME action defined in [EditorInfo]
 * @param function the KFunc to invoke when the target IME action is tapped
 */
fun EditText.onImeAction(imeActionId: Int, function: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == imeActionId) {
            function.invoke()
            true
        } else false
    }
}

fun TextInputLayout.bindImeAction(imeActionId: Int, function: () -> Unit) {
    editText?.onImeAction(imeActionId, function)
}

fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun View.hideKeyboard() = context.getInputMethodManager().hideSoftInputFromWindow(windowToken, 0)