package gloomhaven.gloomhavenhelper.tests

import androidx.test.espresso.Espresso
import gloomhaven.gloomhavenhelper.base.BaseGloomhavenTestCase
import gloomhaven.gloomhavenhelper.screens.PartyScreen
import org.junit.Test

class PartyScreenTests : BaseGloomhavenTestCase() {

    @Test
    fun isTitleDisplayed() {
        before {
            setupPreferences()
            activityScenarioRule.scenario
        }
            .after { }
            .run {
                PartyScreen {
                    title.isVisible()
                }
            }
    }

    @Test
    fun addAndDeleteParty() {
        before {
            setupPreferences()
            activityScenarioRule.scenario
        }
            .after {
            }
            .run {
                PartyScreen {
                    addButton.click()
                    partyNameInput.typeText("Team")
                    hideKeyboard()
                    partyReputationInput.typeText("10")
                    hideKeyboard()
                    saveButton.click()
                    partyItem.isVisible()
                    partyItem.swipeRight()
                    partyItem.doesNotExist()
                }
            }
    }

    @Test
    fun isEmptyDataNotValid() {
        before {
            setupPreferences()
            activityScenarioRule.scenario
        }
            .after {
            }
            .run {
                PartyScreen {
                    addButton.click()
                    saveButton.click()
                    partyNameInput.isVisible()
                    partyReputationInput.isVisible()
                    saveButton.isVisible()
                    pressBack()
                    partyItem.doesNotExist()
                }
            }
    }

}