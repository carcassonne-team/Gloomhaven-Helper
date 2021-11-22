package gloomhaven.gloomhavenhelper.screens

import gloomhaven.gloomhavenhelper.R
import gloomhaven.gloomhavenhelper.base.BaseGloomhavenHelperScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object PlayerScreen: BaseGloomhavenHelperScreen<PlayerScreen>() {

    val addButton = KButton {
        withId(R.id.fab_players)
    }

    val inputNamePlayer = KEditText {
        withId(R.id.et_name)
    }

    val savePlayerButton = KButton {
        withId(R.id.btn_save_player)
    }

    val player = KTextView {
        withId(R.id.player_name)
        withText("Test Player")
    }
}