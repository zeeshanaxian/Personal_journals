package com.bbox.personaljournal.da.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bbox.personaljournal.BuildConfig
import com.bbox.personaljournal.data.MyNotesPreferencesRepository
import com.bbox.personaljournal.data.NotesPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.notesDataStore: DataStore<Preferences> by preferencesDataStore(
    // just my preference of naming including the package name
    name = BuildConfig.APPLICATION_ID
)

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindUNotesPreferencesRepository(
        myNotesPreferencesRepository: MyNotesPreferencesRepository
    ): NotesPreferencesRepository

    companion object {

        // provides instance of DataStore
        @Provides
        @Singleton
        fun provideNotesDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.notesDataStore
        }
    }
}
