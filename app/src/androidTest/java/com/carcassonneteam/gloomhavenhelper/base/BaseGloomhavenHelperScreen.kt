package gloomhaven.gloomhavenhelper.base

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.components.kautomator.screen.UiScreen

abstract class BaseGloomhavenHelperScreen<T : UiScreen<T>> : UiScreen<T>() {
    override val packageName = Constants.packageName

    companion object Constants {
        const val packageName = "gloomhaven.gloomhavenhelper"
    }

    fun getString(@StringRes id: Int): String =
        with(InstrumentationRegistry.getInstrumentation().targetContext) {
            return this.getString(id)
        }

    fun hideKeyboard(){
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
    }
}