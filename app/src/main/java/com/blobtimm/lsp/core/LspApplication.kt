package com.blobtimm.lsp.core

import android.app.Application
import android.content.Context
import com.google.gson.Gson


class LspApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.put(Context::class.java, applicationContext)
        ServiceLocator.put(Gson::class.java, Gson())
    }
}