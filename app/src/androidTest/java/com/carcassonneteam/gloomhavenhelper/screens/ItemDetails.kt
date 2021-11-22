package gloomhaven.gloomhavenhelper.screens

import gloomhaven.gloomhavenhelper.base.BaseGloomhavenHelperScreen
import gloomhaven.gloomhavenhelper.R
import io.github.kakaocup.kakao.text.KTextView

object ItemDetails : BaseGloomhavenHelperScreen<ItemDetails>() {

    val price = KTextView {
        withId(R.id.Iprice)
        withText("20")
    }

    val state = KTextView {
        withId(R.id.Istate)
        withText("Obracany")
    }

    val type = KTextView {
        withId(R.id.Itype)
        withText("Nogi")
    }

    val description = KTextView {
        withId(R.id.Idescription)
        withText("Podczas poruszania się dodaj +2 do Ruchu dla pojedyńczego ruchu")
    }
}