package com.carcassonneteam.gloomhavenhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.models.Player
import com.carcassonneteam.gloomhavenhelper.repositoriese.PlayerRepository
import io.reactivex.Flowable

class PlayerActivityViewModel(application: Application): AndroidViewModel(application) {
    private var playerRepository: PlayerRepository = PlayerRepository(application)


    fun getAllPlayers(player_id: Int): Flowable<List<Player>> {
        return playerRepository.getAllPlayers(player_id)
    }

    fun listGetCount(player_id: Int): Int {
        return playerRepository.getAllPlayers(player_id).count().toString().toInt()
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return playerRepository.getIsLoading()
    }

    fun insert(player: Player) {
        playerRepository.insertPlayer(player)
    }

    fun update(player: Player) {
        playerRepository.updatePlayer(player)
    }

    fun delete(player: Player) {
        playerRepository.deletePlayer(player)
    }

    fun deleteAllPlayersByParty(player_id: Int) {
        playerRepository.deleteAllPlayersByParty(player_id)
    }
}