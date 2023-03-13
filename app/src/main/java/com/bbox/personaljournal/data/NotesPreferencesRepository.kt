package com.bbox.personaljournal.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// domain layer
interface NotesPreferencesRepository {

    suspend fun setNotesData(
        allNotes: String
    )

    suspend fun getNotesData(): Result<String>

    suspend fun setFirstOpen(
        allNotes: Boolean
    )

    suspend fun getFirstOpen(): Result<Boolean>
}

// data layer
class MyNotesPreferencesRepository @Inject constructor(
    private val notesDataStorePreferences: DataStore<Preferences>
) : NotesPreferencesRepository {

    override suspend fun setNotesData(
        allNotes: String
    ) {
        Result.runCatching {
            notesDataStorePreferences.edit { preferences ->
                preferences[KEY_ALL_NOTES] = allNotes
            }
        }
    }

    override suspend fun setFirstOpen(
        firstOpen: Boolean
    ) {
        Result.runCatching {
            notesDataStorePreferences.edit { preferences ->
                preferences[KEY_FIRST_OPEN] = firstOpen
            }
        }
    }

    override suspend fun getNotesData(): Result<String> {
        return Result.runCatching {
            val flow = notesDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our allNotes value, defaulting to "" if not set
                    preferences[KEY_ALL_NOTES]
                }
            val value = flow.firstOrNull() ?: "" // we only care about the 1st value
            value
        }
    }

    override suspend fun getFirstOpen(): Result<Boolean> {
        return Result.runCatching {
            val flow = notesDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_FIRST_OPEN]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    private companion object {

        val KEY_ALL_NOTES = stringPreferencesKey(
            name = "allNotes"
        )

        val KEY_FIRST_OPEN = booleanPreferencesKey(
            name = "firstOpen"
        )
    }
}