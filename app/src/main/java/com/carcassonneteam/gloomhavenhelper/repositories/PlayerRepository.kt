package com.carcassonneteam.gloomhavenhelper.repositoriese

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.database.PlayerDatabase
import com.carcassonneteam.gloomhavenhelper.database.dao.PlayerDao
import com.carcassonneteam.gloomhavenhelper.models.Player
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlayerRepository(application: Application) {
    private val tag = "PlayerRepository"
    private val playerDao: PlayerDao
    var isLoading = MutableLiveData<Boolean>()

    init {
        val database = PlayerDatabase.getInstance(application)
        playerDao = database.playerDao()
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getAllPlayers(party_id: Int): Flowable<List<Player>> {
        return playerDao.getAllPlayersByParty(party_id)
    }

    fun insertPlayer(player: Player) {
        isLoading.value = true
        Completable.fromAction { playerDao.insert(player) }.subscribeOn(Schedulers.io())
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

    fun updatePlayer(player: Player) {
        Completable.fromAction { playerDao.update(player) }.observeOn(Schedulers.io())
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

    fun deletePlayer(player: Player?) {
        isLoading.value = true
        Completable.fromAction { playerDao.delete(player!!) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    isLoading.setValue(false)
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: " + e.message)
                }
            })
    }

    fun deleteAllPlayers() {
        isLoading.value = true
        Completable.fromAction { playerDao.deleteAllPlayers() }.subscribeOn(Schedulers.io())
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

    fun deleteAllPlayersByParty(party_id: Int) {
        isLoading.value = true
        Completable.fromAction { playerDao.deleteAllPlayersUnderParty(party_id) }
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