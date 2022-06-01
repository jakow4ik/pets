package by.dro.pets.data.repositories.dogs

import android.content.Context
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
        context.dataStore.editBookmarksSet { it.add(id) }
    }

    override suspend fun remove(id: String) {
        context.dataStore.editBookmarksSet { it.remove(id) }
    }

    override fun getAll(): Flow<Set<String>> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[PreferencesKeys.BOOKMARKS].orEmpty()
        }
    }

    private suspend fun DataStore<Preferences>.editBookmarksSet(block: (MutableSet<String>) -> Unit) {
        edit { preference ->
            val set: MutableSet<String> =
                (preference[PreferencesKeys.BOOKMARKS].orEmpty()).toMutableSet()
            block.invoke(set)
            preference[PreferencesKeys.BOOKMARKS] = set
        }
    }

    private object PreferencesKeys {
        val BOOKMARKS = stringSetPreferencesKey("bookmarks")
    }
}
