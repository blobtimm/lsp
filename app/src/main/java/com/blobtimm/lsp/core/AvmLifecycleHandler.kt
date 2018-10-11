package com.blobtimm.lsp.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.Serializable
import java.lang.reflect.Type

abstract class AvmLifecycleHandler<AvmData : Serializable, Avm : AvmLifecycle<AvmData>>: AppCompatActivity() {

    private val KEY = "AvmLifecycleHandlerKey"

    abstract fun avm(): Avm

    abstract fun avmDataClassType(): Type

    abstract fun onCreate()

    inline fun <reified AvmData : Any> getType() = AvmData::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
        onRestore(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        with (collectAvmData()) {
            if (this != null && outState != null) {
                outState.putString(KEY, this)
            }
        }
        super.onSaveInstanceState(outState)
    }

    fun collectAvmData(): String? {
        val data: AvmData? = avm().onSaveInstanceState()
        return if (data != null) {
            ServiceLocator.resolve(Gson::class.java).toJson(data)
        } else {
            null
        }
    }

    fun onRestore(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                this.getString(KEY, null)?.let {
                    val data: AvmData = ServiceLocator.resolve(Gson::class.java).fromJson<AvmData>(it, avmDataClassType())
                    avm().onRestore(data)
                }
            }
        }
    }
}