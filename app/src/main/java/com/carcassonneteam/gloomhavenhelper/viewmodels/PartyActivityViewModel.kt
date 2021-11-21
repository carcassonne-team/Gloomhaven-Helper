package com.carcassonneteam.gloomhavenhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.models.Party
import com.carcassonneteam.gloomhavenhelper.repositories.PartyRepository
import io.reactivex.Flowable

class PartyActivityViewModel(application: Application): AndroidViewModel(application) {

    var partyRepository: PartyRepository = PartyRepository(application)

    fun getAllParty(): Flowable<List<Party>> {
        return partyRepository.getAllParty()
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return partyRepository.getIsLoading()
    }

    fun insert(party: Party) {
        partyRepository.insertParty(party)
    }

    fun updateParty(party: Party) {
        partyRepository.updateParty(party)
    }

    fun deleteParty(party: Party) {
        partyRepository.deleteParty(party)
    }

    fun deleteAllParty() {
        partyRepository.deleteAllParty()
    }

}
