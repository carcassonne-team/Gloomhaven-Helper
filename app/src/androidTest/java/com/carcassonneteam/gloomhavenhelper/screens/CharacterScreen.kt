package gloomhaven.gloomhavenhelper.screens

import gloomhaven.gloomhavenhelper.R
import gloomhaven.gloomhavenhelper.base.BaseGloomhavenHelperScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object CharacterScreen : BaseGloomhavenHelperScreen<CharacterScreen>(){

    val addCharacterButton =  KButton {
        withId(R.id.fab_characters)
    }

    val characterNameInput =  KEditText {
        withId(R.id.character_name)
    }

    val saveCharacterButton =  KButton {
        withId(R.id.btn_save_character)
    }

    val characterExist = KTextView {
        withId(R.id.party_image)
        withText("Test Character")
    }
}