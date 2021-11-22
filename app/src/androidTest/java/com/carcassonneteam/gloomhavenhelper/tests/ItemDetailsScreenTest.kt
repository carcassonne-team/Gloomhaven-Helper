package gloomhaven.gloomhavenhelper.tests

import gloomhaven.gloomhavenhelper.base.BaseGloomhavenTestCase
import gloomhaven.gloomhavenhelper.screens.ItemDetails
import gloomhaven.gloomhavenhelper.screens.ItemsScreen
import gloomhaven.gloomhavenhelper.screens.PartyScreen
import org.junit.Test

class ItemDetailsScreenTest : BaseGloomhavenTestCase() {

    @Test
    fun isItemDetailExist(){
        before {
            setupPreferences()
            activityScenarioRule.scenario
            PartyScreen {
                itemButton.click()
                ItemsScreen {
                    firstElement.click()
                }
            }
        }
            .after { }
            .run {
                ItemDetails {
                    price.isVisible()
                    state.isVisible()
                    type.isVisible()
                    description.isVisible()
                }
            }
    }
}