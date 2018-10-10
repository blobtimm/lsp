package com.blobtimm.lsp

import android.content.Context
import androidx.annotation.StringRes

fun getString(@StringRes resId: Int): String {

    // Junit has difficulty mocking contexts. This should hold still allow for us to assert uniqueness.
    fun generate() = "MockedString $resId"

    if (BuildConfig.MOCKED_BUILD) {
        return generate()
    }
    try {
        return ServiceLocator.resolve(Context::class.java).getString(resId)
    } catch (exception: NullPointerException) {
        return generate()
    }
}

fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {

    // Junit has difficulty mocking contexts. This should hold still allow for us to assert uniqueness.
    fun generate(): String {
        val s: String = formatArgs.map { it.toString() + "_" }.joinToString()
        return "MockedString $resId $s"
    }


    if (BuildConfig.MOCKED_BUILD) {
        return generate()
    }
    try {
        return ServiceLocator.resolve(Context::class.java).getString(resId, *formatArgs)
    } catch (exception: NullPointerException) {
        return generate()
    }
}