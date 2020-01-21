package pl.mrozok.myapplication.presentation.tools

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import java.util.concurrent.TimeUnit

class SimpleTextWatcher(private val onTextChangedAction: (String) -> Unit) : TextWatcher {

    companion object {
        private val DELAY_BETWEEN_CHANGES_IN_MILLIS = TimeUnit.SECONDS.toMillis(2)
    }

    private var lastValue: String? = null
    private var lastChangeMillis = 0L

    override fun afterTextChanged(text: Editable?) {
        Log.d("Test", "publishing new text")
    } // intentionally does nothing

    override fun beforeTextChanged(
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    } // intentionally does nothing

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        val currentMillis = System.currentTimeMillis()
        if (currentMillis - lastChangeMillis <= DELAY_BETWEEN_CHANGES_IN_MILLIS) {
            return
        }
        lastChangeMillis = currentMillis
        text?.apply {
            val currentValue = text.toString()
            if (currentValue != lastValue) {
                onTextChangedAction.invoke(currentValue)
                lastValue = currentValue
            }
        }
    }

}
