package br.com.jeramovies.data.util

import br.com.jeramovies.domain.entity.MovieManagedStatus
import br.com.jeramovies.domain.entity.MoviePersistError
import br.com.jeramovies.domain.entity.MoviePersisted
import br.com.jeramovies.domain.entity.MovieRemoved
import io.realm.Realm
import io.realm.RealmObject
import java.util.*

fun Realm.removeFromDatabase(obj: RealmObject): Boolean {
    return try {
        executeTransaction {
            obj.deleteFromRealm()
        }
        true
    } catch (ex: Exception) {
        false
    }
}

fun Realm.addToDatabase(obj: RealmObject): Boolean {
    return try {
        executeTransaction {
            copyToRealm(obj)
        }
        true
    } catch (ex: Exception) {
        false
    }
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: String
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Byte
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: ByteArray
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Short
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Int
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Long
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Double
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Float
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Boolean
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}

fun Realm.addOrRemoveIfExists(
    obj: RealmObject,
    fieldName: String,
    value: Date
): MovieManagedStatus {
    val persistedObject = where(obj::class.java).equalTo(fieldName, value).findFirst()
    return if (persistedObject != null)
        if (removeFromDatabase(persistedObject)) MovieRemoved()
        else MoviePersistError()
    else
        if (addToDatabase(obj)) MoviePersisted()
        else MoviePersistError()
}