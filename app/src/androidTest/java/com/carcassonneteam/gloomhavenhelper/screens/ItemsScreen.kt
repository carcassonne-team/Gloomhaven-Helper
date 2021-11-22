package gloomhaven.gloomhavenhelper.screens

import gloomhaven.gloomhavenhelper.R
import gloomhaven.gloomhavenhelper.base.BaseGloomhavenHelperScreen
import io.github.kakaocup.kakao.text.KTextView

object ItemsScreen: BaseGloomhavenHelperScreen<ItemsScreen>()  {

    val firstElement = KTextView {
        withId(R.id.name_item)
        withText("Buty Raźnego Kroku")
    }
}