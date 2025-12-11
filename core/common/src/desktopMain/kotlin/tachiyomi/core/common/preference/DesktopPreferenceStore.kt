package tachiyomi.core.common.preference

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.prefs.Preferences

/**
 * Desktop implementation of PreferenceStore using Java Preferences API.
 */
class DesktopPreferenceStore(
    private val preferences: Preferences = Preferences.userNodeForPackage(DesktopPreferenceStore::class.java)
) : PreferenceStore {

    override fun getString(key: String, defaultValue: String): Preference<String> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Preference<Long> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun getInt(key: String, defaultValue: Int): Preference<Int> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Preference<Float> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Preference<Boolean> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun getStringSet(key: String, defaultValue: Set<String>): Preference<Set<String>> {
        return DesktopPreference(preferences, key, defaultValue)
    }

    override fun <T> getObject(
        key: String,
        defaultValue: T,
        serializer: (T) -> String,
        deserializer: (String) -> T
    ): Preference<T> {
        return DesktopObjectPreference(preferences, key, defaultValue, serializer, deserializer)
    }

    override fun getAll(): Map<String, *> {
        return preferences.keys().associateWith { key ->
            preferences.get(key, null)
        }
    }
}

/**
 * Desktop implementation of Preference using Java Preferences.
 */
private class DesktopPreference<T>(
    private val preferences: Preferences,
    private val key: String,
    private val defaultValue: T
) : Preference<T> {

    private val flow = MutableStateFlow(get())

    override fun key(): String = key

    override fun get(): T {
        @Suppress("UNCHECKED_CAST")
        return when (defaultValue) {
            is String -> preferences.get(key, defaultValue as String) as T
            is Int -> preferences.getInt(key, defaultValue as Int) as T
            is Long -> preferences.getLong(key, defaultValue as Long) as T
            is Float -> preferences.getFloat(key, defaultValue as Float) as T
            is Boolean -> preferences.getBoolean(key, defaultValue as Boolean) as T
            is Set<*> -> {
                val stored = preferences.get(key, null)
                if (stored != null) {
                    stored.split(",").toSet() as T
                } else {
                    defaultValue
                }
            }
            else -> defaultValue
        }
    }

    override fun set(value: T) {
        when (value) {
            is String -> preferences.put(key, value)
            is Int -> preferences.putInt(key, value)
            is Long -> preferences.putLong(key, value)
            is Float -> preferences.putFloat(key, value)
            is Boolean -> preferences.putBoolean(key, value)
            is Set<*> -> preferences.put(key, (value as Set<String>).joinToString(","))
        }
        preferences.flush()
        flow.value = value
    }

    override fun isSet(): Boolean {
        return preferences.get(key, null) != null
    }

    override fun delete() {
        preferences.remove(key)
        preferences.flush()
        flow.value = defaultValue
    }

    override fun defaultValue(): T = defaultValue

    override fun changes(): Flow<T> = flow

    override fun stateIn(scope: CoroutineScope): kotlinx.coroutines.flow.StateFlow<T> {
        return flow
    }
}

/**
 * Desktop implementation for object preferences with custom serialization.
 */
private class DesktopObjectPreference<T>(
    private val preferences: Preferences,
    private val key: String,
    private val defaultValue: T,
    private val serializer: (T) -> String,
    private val deserializer: (String) -> T
) : Preference<T> {

    private val flow = MutableStateFlow(get())

    override fun key(): String = key

    override fun get(): T {
        val stored = preferences.get(key, null)
        return if (stored != null) {
            try {
                deserializer(stored)
            } catch (e: Exception) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }

    override fun set(value: T) {
        preferences.put(key, serializer(value))
        preferences.flush()
        flow.value = value
    }

    override fun isSet(): Boolean {
        return preferences.get(key, null) != null
    }

    override fun delete() {
        preferences.remove(key)
        preferences.flush()
        flow.value = defaultValue
    }

    override fun defaultValue(): T = defaultValue

    override fun changes(): Flow<T> = flow

    override fun stateIn(scope: CoroutineScope): kotlinx.coroutines.flow.StateFlow<T> {
        return flow
    }
}
