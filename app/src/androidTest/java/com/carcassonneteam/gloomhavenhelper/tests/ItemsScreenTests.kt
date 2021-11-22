package gloomhaven.gloomhavenhelper.tests

import gloomhaven.gloomhavenhelper.base.BaseGloomhavenTestCase
import gloomhaven.gloomhavenhelper.screens.ItemsScreen
import gloomhaven.gloomhavenhelper.screens.PartyScreen
import org.junit.Test

class ItemsScreenTests : BaseGloomhavenTestCase() {

    @Test
    fun doesFirstRowExist(){
        before {
            setupPreferences()
            activityScenarioRule.scenario
            PartyScreen {
                itemButton.click()
            }
        }
            .after { }
            .run {
                ItemsScreen {
                    firstElement.isVisible()
                }
            }
    }
}
