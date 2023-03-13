package com.bbox.personaljournal.da.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bbox.personaljournal.data.MyNotesPreferencesRepository
import com.bbox.personaljournal.data.NotesPreferencesRepository
import com.bbox.personaljournal.ui.allnotes.AllNotesRepository
import com.bbox.personaljournal.ui.allnotes.AllNotesRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAllNotesRepository(): AllNotesRepository = AllNotesRepositoryImp()
}


