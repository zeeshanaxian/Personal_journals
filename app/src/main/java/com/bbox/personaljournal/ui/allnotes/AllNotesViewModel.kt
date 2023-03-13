package com.bbox.personaljournal.ui.allnotes


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbox.personaljournal.data.CallBackEvent
import com.bbox.personaljournal.data.FirstOpenCallBackEvent
import com.bbox.personaljournal.data.NotesPreferencesRepository
import com.bbox.personaljournal.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class AllNotesViewModel @Inject constructor(
    private val allNotesRepository: AllNotesRepository,
    private val notesPreferencesRepository: NotesPreferencesRepository
) : ViewModel() {
    val allNotesMutable = MutableLiveData<AllNotes>()

//    init {
//        populateData()
//    }

    fun populateData() {
        allNotesMutable.value = allNotesRepository.getAllNotes()
    }

    fun saveNotesData(
        name: String
    ) = flow {
        notesPreferencesRepository.setNotesData(name)
        emit(CallBackEvent.NotesCachedSuccessObject)
    }

    fun getNotesData() = flow {
        val result = notesPreferencesRepository.getNotesData()
        val value = result.getOrNull().orEmpty() // don't care if it failed right now but you might
        emit(CallBackEvent.CachedNotesFetchSuccess(value))
    }


    fun saveFirstOpen(
        isFirstOpen: Boolean
    ) = flow {
        notesPreferencesRepository.setFirstOpen(isFirstOpen)
        emit(FirstOpenCallBackEvent.FirstOpenCachedSuccessObject)
    }

    fun getFirstOpen() = flow {
        val result = notesPreferencesRepository.getFirstOpen()
        val value =
            result.getOrNull()!!.or(false) // don't care if it failed right now but you might
        emit(FirstOpenCallBackEvent.FirstOpenFetchSuccess(value))
    }

}