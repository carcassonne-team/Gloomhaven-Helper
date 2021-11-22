package gloomhaven.gloomhavenhelper.base

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.carcassonneteam.gloomhavenhelper.views.PartyActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import gloomhaven.gloomhavenhelper.views.PartyActivity
import org.junit.Rule

abstract class BaseGloomhavenTestCase: TestCase() {
    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule(PartyActivity::class.java)

    fun setupPreferences() {
        activityScenarioRule.scenario.onActivity {
            it.getPreferences(Context.MODE_PRIVATE).edit().clear().apply()
        }
    }
}