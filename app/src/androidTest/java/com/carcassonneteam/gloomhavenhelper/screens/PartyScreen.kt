package gloomhaven.gloomhavenhelper.screens

import gloomhaven.gloomhavenhelper.R
import gloomhaven.gloomhavenhelper.base.BaseGloomhavenHelperScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object PartyScreen : BaseGloomhavenHelperScreen<PartyScreen>() {

    val title = KTextView {
        withId(R.id.toolbar_title)
        withText(R.string.parties)
    }

    val partyItem = KScrollView {
        withId(R.id.text_view)
        withSibling {
            withId(R.id.repuration)
        }
    }

    val addButton = KButton {
        withId(R.id.fab_parties)
    }

    val partyNameInput = KEditText {
        withId(R.id.party_name_input_label)
    }

    val partyReputationInput = KEditText {
        withId(R.id.party_reputation_input)
    }

    val saveButton = KButton {
        withId(R.id.button_save_party)
    }

    val itemButton = KButton {
        withId(R.id.items_button)
    }
}