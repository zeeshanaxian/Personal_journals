package com.bbox.personaljournal.data

// just some events to interact with the view
sealed class CallBackEvent {
    object NotesCachedSuccessObject : CallBackEvent()
    class CachedNotesFetchSuccess(
        val allNotesData: String
    ) : CallBackEvent()
}

sealed class FirstOpenCallBackEvent {
    object FirstOpenCachedSuccessObject : FirstOpenCallBackEvent()
    class FirstOpenFetchSuccess(
        val isFirstOpen: Boolean
    ) : FirstOpenCallBackEvent()
}