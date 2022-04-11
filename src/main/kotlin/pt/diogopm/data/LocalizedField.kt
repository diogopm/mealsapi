package pt.diogopm.data

import kotlinx.serialization.Serializable
import java.util.*


private val availableLanguages = Locale.getISOLanguages()

fun createLanguageMap(default: String, vararg pairs: Pair<String, String>): Map<String, String> {
    val invalid = pairs.firstOrNull { it.first !in availableLanguages }
    if (invalid != null) throw IllegalArgumentException("Unknown language: [${invalid.first}] - ${invalid.second}")
    return mapOf("default" to default, *pairs)
}

@Serializable
class LocalizedField(val defaultKey: String) : LinkedHashMap<String, String>() {

    constructor(defaultKey: String, localizations: Map<String, String>) : this(defaultKey) {
        require(defaultKey in localizations.keys) { "Localizations must include the default key" }
        putAll(localizations)
    }

    override fun put(key: String, value: String): String? {
        validateKey(key)
        return super.put(key, value)
    }

    override fun putAll(from: Map<out String, String>) {
        from.forEach { validateKey(it.key) }
        if (from.keys.all { it in availableLanguages }) super.putAll(from)
    }

    override fun putIfAbsent(key: String, value: String): String? {
        validateKey(key)
        return super.putIfAbsent(key, value)
    }

    override fun getOrDefault(key: String, defaultValue: String): String {
        validateKey(key)
        return super.get(key) ?: super.get(defaultKey) ?: defaultValue
    }

    override operator fun get(key: String): String {
        validateKey(key)
        return super.get(key) ?: super.get(defaultKey)!!
    }

    private fun validateKey(key: String) {
        require(key in availableLanguages) { "$key not a valid ISO language" }
    }

}


/*
@Serializable
class LocalizedField(val default: String)  : LinkedHashMap<String, String>(mapOf("default" to default)) {

    constructor(default: String, other: Map<String, String>) : this(default) {
        putAll(other)
    }

    override fun put(key: String, value: String): String? {
        if(key in availableLanguages) {
            return super.put(key, value)
        }
        return null
    }

    override fun putAll(from: Map<out String, String>) {
        if(from.keys.all { it in availableLanguages }) super.putAll(from)
    }

    override fun putIfAbsent(key: String, value: String): String? {
        if(key in availableLanguages) return super.putIfAbsent(key, value)
        return null
    }

    override fun getOrDefault(key: String, defaultValue: String): String {
        return get(key) ?: default
    }

    override operator fun get(key: String): String {
        return super.getOrDefault(key, default)
    }

}*/
