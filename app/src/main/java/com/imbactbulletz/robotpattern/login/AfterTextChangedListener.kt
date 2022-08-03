package com.imbactbulletz.robotpattern.login

import android.text.Editable
import android.text.TextWatcher

class AfterTextChangedListener(
    private val action: (text: String) -> Unit
): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) = action(p0.toString())
}