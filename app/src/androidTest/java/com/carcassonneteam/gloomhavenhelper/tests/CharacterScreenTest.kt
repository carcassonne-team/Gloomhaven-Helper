package gloomhaven.gloomhavenhelper.tests

import gloomhaven.gloomhavenhelper.base.BaseGloomhavenTestCase
import gloomhaven.gloomhavenhelper.screens.CharacterScreen
import gloomhaven.gloomhavenhelper.screens.PartyScreen
import gloomhaven.gloomhavenhelper.screens.PlayerScreen
import org.junit.Test

class CharacterScreenTest : BaseGloomhavenTestCase(){

    @Test
    fun addCharacter() {
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
            PlayerScreen {
                addButton.click()
                inputNamePlayer.typeText("Test Player")
                PartyScreen.hideKeyboard()
                savePlayerButton.click()
                player.click()
            }
        }
            .after { }
            .run {
                CharacterScreen {
                    addCharacterButton.click()
                    characterNameInput.typeText("Test Character")
                    PartyScreen.hideKeyboard()
                    saveCharacterButton.click()
                    characterExist.isVisible()
                }

                PartyScreen{
                    pressBack()
                    pressBack()
                    partyItem.swipeRight()
                }
            }
    }
}