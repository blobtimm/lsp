package com.blobtimm.lsp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson


class LspApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.put(Context::class.java, applicationContext)
        ServiceLocator.put(Gson::class.java, Gson())
    }
}