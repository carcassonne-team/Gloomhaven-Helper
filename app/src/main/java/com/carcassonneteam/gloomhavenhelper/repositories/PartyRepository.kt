package com.carcassonneteam.gloomhavenhelper.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.database.PlayerDatabase
import com.carcassonneteam.gloomhavenhelper.database.dao.PartyDao
import com.carcassonneteam.gloomhavenhelper.models.Party
import com.carcassonneteam.gloomhavenhelper.repositoriese.PlayerRepository
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PartyRepository(application: Application) {

    private val tag = "PartyRepository"
    private val partyDao: PartyDao
    var isLoading = MutableLiveData<Boolean>()
    private val playerRepository = PlayerRepository(application)

    init {
        val database = PlayerDatabase.getInstance(application)
        partyDao = database.partyDao()
    }


    fun getIsLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getAllParty(): Flowable<List<Party>> {
        return partyDao.getAllParties()
    }

    fun insertParty(party: Party) {
        isLoading.value = true
        Completable.fromAction { partyDao.insert(party) }.subscribeOn(Schedulers.io())
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

    fun updateParty(party: Party) {
        Completable.fromAction { partyDao.update(party) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: Called")
                }
            })
    }

    fun deleteParty(party: Party) {
        isLoading.value = true
        Completable.fromAction { partyDao.delete(party) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    playerRepository.deleteAllPlayersByParty(party.getId())
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: " + e.message)
                }
            })
    }

    fun deleteAllParty() {
        isLoading.value = true
        Completable.fromAction { partyDao.deleteAllParty() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(tag, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(tag, "onComplete: Called")
                    playerRepository.deleteAllPlayers()
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(tag, "onError: Called" + e.message)
                }
            })
    }

}