package com.carcassonneteam.gloomhavenhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.carcassonneteam.gloomhavenhelper.repositories.CharacterRepository
import com.carcassonneteam.gloomhavenhelper.models.Character
import io.reactivex.Flowable

class CharacterViewModel(application: Application) : AndroidViewModel(application) {
    private val characterRepository: CharacterRepository = CharacterRepository(application)


    fun getAllCharacters(character_id: Int): Flowable<List<Character>> {
        return characterRepository.getAllCharacters(character_id)
    }

    val isLoading: MutableLiveData<Boolean>
        get() = characterRepository.getIsLoading()

    fun insert(character: Character) {
        characterRepository.insertCharacter(character)
    }

    fun update(character: Character) {
        characterRepository.updateCharacter(character)
    }

    fun delete(character: Character) {
        characterRepository.deleteCharacter(character)
    }

    fun deleteAllCharactersByPlayer(player_id: Int) {
        characterRepository.deleteAllCharactersByPlayer(player_id)
    }

}
