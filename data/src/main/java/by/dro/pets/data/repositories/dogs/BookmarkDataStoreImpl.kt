package by.dro.pets.data.repositories.dogs

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class BookmarkDataStoreImpl(private val context: Context, dataStoreName: String) :
    BookmarkDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(dataStoreName)

    override suspend fun add(id: String) {
        context.dataStore.edit { pref ->
            val set: MutableSet<String> = (pref[PreferencesKeys.BOOKMARKS].orEmpty()).toMutableSet()
            set.add(id)
            pref[PreferencesKeys.BOOKMARKS] = set
        }
    }

    override suspend fun remove(id: String) {
        context.dataStore.edit { pref ->
            val set: MutableSet<String> = (pref[PreferencesKeys.BOOKMARKS].orEmpty()).toMutableSet()
            set.remove(id)
            pref[PreferencesKeys.BOOKMARKS] = set
        }
    }


    override fun getAll(): Flow<Set<String>> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.d("DataStoreRepository", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[PreferencesKeys.BOOKMARKS].orEmpty()
        }
    }

    private object PreferencesKeys {
        val BOOKMARKS = stringSetPreferencesKey("bookmarks")
    }
}
