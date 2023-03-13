package com.bbox.personaljournal.models

import com.bbox.personaljournal.utils.enums.UserMood

data class AllNotes(
    var monthList: ArrayList<Months>
)


data class Months(
    val dayDateList: ArrayList<DayDate>,
    val monthName: String
)

data class DayDate(
    val NoteList: ArrayList<NoteEntry>,
    val dayName: String
)

data class NoteEntry(
    val description: String,
    val time: String,
    val mood: UserMood

)


