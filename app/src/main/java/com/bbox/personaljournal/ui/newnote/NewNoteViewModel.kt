package com.bbox.personaljournal.ui.newnote


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbox.personaljournal.data.CallBackEvent
import com.bbox.personaljournal.data.NotesPreferencesRepository
import com.bbox.personaljournal.utils.AppUtils
import com.bbox.personaljournal.models.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val notesPreferencesRepository: NotesPreferencesRepository
) : ViewModel() {
    private var allNotes: AllNotes? = null

    // Create a MutableLiveData object
    private val _updateData = MutableLiveData<String>()

    // Create a LiveData object to expose the data to the UI
    val updateData: LiveData<String>
        get() = _updateData

    init {
        getNotesData()
    }

    private fun saveData(noteEntry: NoteEntry) {
        val timeListMain: ArrayList<NoteEntry> = ArrayList()
        timeListMain.add(noteEntry)
        var isMonthAvailable = false
        var isDayAvailable = false
        for (i in 0 until allNotes?.monthList!!.size) {
            if (AppUtils.getMonthName() == allNotes?.monthList!![i].monthName) {
                isMonthAvailable = true
                for (j in 0 until allNotes?.monthList!![i].dayDateList.size) {
                    if (AppUtils.getDayAndDate() == allNotes?.monthList!![i].dayDateList[j].dayName) {
                        isDayAvailable = true
                        allNotes?.monthList!![i].dayDateList[j].NoteList.add(noteEntry)
                        break
                    }
                }
            }
        }
        if (!isMonthAvailable) {
            val timeList: ArrayList<NoteEntry> = ArrayList()
            timeList.add(noteEntry)
            val daysList: ArrayList<DayDate> = ArrayList()
            daysList.add(DayDate(timeList, AppUtils.getDayAndDate()))
            allNotes?.monthList?.add(Months(daysList, AppUtils.getMonthName()))
        } else if (!isDayAvailable) {
            val timeList: ArrayList<NoteEntry> = ArrayList()
            timeList.add(noteEntry)
            for (i in 0 until allNotes?.monthList!!.size) {
                if (AppUtils.getMonthName() == allNotes?.monthList!![i].monthName) {
                    allNotes?.monthList!![i].dayDateList.add(
                        DayDate(
                            timeList,
                            AppUtils.getDayAndDate()
                        )
                    )
                }
            }
        }
        val gson = Gson()
        val jsonString = gson.toJson(allNotes)
        _updateData.value = jsonString
//        notesPreferencesRepository.setNotesData(jsonString)
    }

    fun addNote(
        noteEntry: NoteEntry
    ) = flow {
        saveData(noteEntry)
        emit(CallBackEvent.NotesCachedSuccessObject)
    }

    fun saveNotesData(
        name: String
    ) = flow {
        notesPreferencesRepository.setNotesData(name)
        emit(CallBackEvent.NotesCachedSuccessObject)
    }

    fun getNotesData() = flow {
        val result = notesPreferencesRepository.getNotesData()
        val allNotesString = result.getOrNull().orEmpty()
        val gson = Gson()
        val myModel = gson.fromJson(allNotesString, AllNotes::class.java)
        allNotes = myModel
        emit(CallBackEvent.CachedNotesFetchSuccess(allNotesString))
    }

}