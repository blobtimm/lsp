package com.blobtimm.lsp

import java.io.Serializable

/*
 * AvmData - a kotlin data class that contains the bare minimum required to recreate an AVM
 *  from a previous state.
 *
 * From the docs: To bridge the gap between user expectation and system behavior, use a
 *  combination of ViewModel objects, the onSaveInstanceState() method, and/or local storage to
 *  persist the UI state across such application and activity instance transitions.
 *
 * Unlike saved instance state, ViewModels are destroyed during a system-initiated process death.
 *  This is why you should use ViewModel objects in combination with onSaveInstanceState()
 *  (or some other disk persistence), stashing identifiers in savedInstanceState to help view
 *  models reload the data after system death.
 *
 * Saving UI State: https://developer.android.com/topic/libraries/architecture/saving-states
 */
interface AvmLifecycle<AvmData : Serializable> {
    /**
     * Provide a bundle for saving state, or not. The system will not consume one if it is not
     *  provided. This should be a simple, flat, data class.
     */
    fun onSaveInstanceState(): AvmData?

    /**
     * avmData - only invoked if the activity receives a non-null bundle. Use this to
     *  rebuild your AVM.
     */
    fun onRestore(avmData: AvmData)
}