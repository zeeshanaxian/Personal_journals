package com.bbox.personaljournal.ui.allnotes

import com.bbox.personaljournal.models.AllNotes
import com.bbox.personaljournal.models.DayDate
import com.bbox.personaljournal.models.Months
import com.bbox.personaljournal.models.NoteEntry
import com.bbox.personaljournal.utils.enums.UserMood


class AllNotesRepositoryImp : AllNotesRepository {
    override fun getAllNotes() = getAllData()

    private fun getAllData(): AllNotes {
        val timeList: ArrayList<NoteEntry> = ArrayList()
        timeList.add(
            NoteEntry(
                description = "hello how are you",
                mood = UserMood.GOOD,
                time = "18:59"
            )
        )
        timeList.add(
            NoteEntry(
                description = "I am Good today",
                mood = UserMood.GOOD,
                time = "06:55"
            )
        )
        timeList.add(
            NoteEntry(
                description = "I am not feeling well today",
                mood = UserMood.BAD,
                time = "08:00"
            )
        )
        timeList.add(
            NoteEntry(
                description = "I am Normal today",
                mood = UserMood.NORMAL,
                time = "10:36"
            )
        )
        val timeListTest: ArrayList<NoteEntry> = ArrayList()
        timeListTest.add(NoteEntry(description = "test", mood = UserMood.GOOD, time = "18:59"))
        timeListTest.add(NoteEntry(description = "test123", mood = UserMood.GOOD, time = "06:55"))
        timeListTest.add(NoteEntry(description = "65346", mood = UserMood.BAD, time = "08:00"))
        timeListTest.add(NoteEntry(description = "65346", mood = UserMood.BAD, time = "08:00"))
        timeListTest.add(NoteEntry(description = "%%%%%", mood = UserMood.NORMAL, time = "10:36"))
        timeListTest.add(NoteEntry(description = "%%%%%", mood = UserMood.NORMAL, time = "10:36"))
        val days = DayDate(timeListTest, dayName = "test, 10")
        val daysList: ArrayList<DayDate> = ArrayList()
        daysList.add(DayDate(timeList, dayName = "Fri, 10"))
        daysList.add(DayDate(timeList, dayName = "Thur, 15"))
        daysList.add(days)
        daysList.add(DayDate(timeList, dayName = "Sun, 18"))
        val monthsList: ArrayList<Months> = ArrayList()
        monthsList.add(Months(daysList, monthName = "MARCH"))
        monthsList.add(Months(daysList, monthName = "JUNE"))
        monthsList.add(Months(daysList, monthName = "AUGUST"))
        return AllNotes(monthsList)
    }

}