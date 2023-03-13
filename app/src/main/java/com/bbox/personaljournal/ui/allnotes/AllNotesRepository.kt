package com.bbox.personaljournal.ui.allnotes

import com.bbox.personaljournal.models.AllNotes


interface AllNotesRepository {
    fun getAllNotes(): AllNotes
}