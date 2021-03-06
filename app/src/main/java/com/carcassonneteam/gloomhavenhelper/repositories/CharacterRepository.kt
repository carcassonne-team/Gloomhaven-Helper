package com.carcassonneteam.gloomhavenhelper.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.database.PlayerDatabase
import com.carcassonneteam.gloomhavenhelper.database.dao.CharacterDao
import com.carcassonneteam.gloomhavenhelper.models.Character
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CharacterRepository(application: Application) {
    private val tag = "CharacterRepository"
    private val characterDao: CharacterDao
    var isLoading = MutableLiveData<Boolean>()

    init {
        val database = PlayerDatabase.getInstance(application)
        characterDao = database.characterDao()
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getAllCharacters(player_id: Int): Flowable<List<Character>> {
        return characterDao.getAllCharactersByPlayer(player_id)
    }

    fun insertCharacter(character: Character) {
        isLoading.value = true
        Completable.fromAction { characterDao.insert(character) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: Called" + e.message)
                }
            })
    }


    fun updateCharacter(character: Character) {
        Completable.fromAction { characterDao.update(character) }.observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: Called" + e.message)
                }
            })
    }


    fun deleteCharacter(character: Character) {
        isLoading.value = true
        Completable.fromAction { characterDao.delete(character) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: " + e.message)
                }
            })
    }


    fun deleteAllCharacters() {
        isLoading.value = true
        Completable.fromAction { characterDao.deleteAllCharacters() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: " + e.message)
                }
            })
    }


    fun deleteAllCharactersByPlayer(player_id: Int) {
        isLoading.value = true
        Completable.fromAction { characterDao.deleteAllCharactersUnderPlayer(player_id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: " + e.message)
                }
            })
    }

}