package gloomhaven.gloomhavenhelper.tests

import gloomhaven.gloomhavenhelper.base.BaseGloomhavenTestCase
import gloomhaven.gloomhavenhelper.screens.PlayerScreen
import gloomhaven.gloomhavenhelper.screens.PartyScreen
import org.junit.Test

class AddPlayerScreenTest : BaseGloomhavenTestCase(){

    @Test
    fun addPlayer(){
        before {
            setupPreferences()
            activityScenarioRule.scenario
            PartyScreen {
                addButton.click()
                partyNameInput.typeText("Team")
                hideKeyboard()
                partyReputationInput.typeText("10")
                hideKeyboard()
                saveButton.click()

                partyItem.isVisible()
                partyItem.click()
            }
        }
            .after { }
            .run {
                PlayerScreen {
                    addButton.click()
                    inputNamePlayer.typeText("Test Player")
                    PartyScreen.hideKeyboard()
                    savePlayerButton.click()

                    player.isVisible()
                    pressBack()
                }
                PartyScreen{
                    partyItem.swipeRight()
                }
            }
    }
}