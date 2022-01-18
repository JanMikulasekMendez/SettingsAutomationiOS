import io.appium.java_client.MobileElement
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.IOException
import java.net.URL


class SettingsTestClassSecond {

    private var driver: IOSDriver<*>? = null

    @Before
    @Throws(IOException::class)
    fun setUp() {
        val caps = DesiredCapabilities()
        caps.setCapability("platformName", "iOS")
        caps.setCapability("platformVersion", "15.2")
        caps.setCapability("deviceName", "iPhone 11")
        caps.setCapability("automationName", "XCUITest")

        //caps.setCapability("app", APP)

        caps.setCapability("noReset", true)
        caps.setCapability("udid", "F8EE7098-8D89-46FB-B649-54129E57A109")
        caps.setCapability("bundleId", "com.apple.Preferences")

        if ((!firstTest)) {
            caps.setCapability("webDriverAgentUrl", "http://localhost:8100")
        }

        driver = IOSDriver<MobileElement>(URL("http://localhost:4723/wd/hub"), caps)
    }

    @After
    fun tearDown() {
        firstTest = false
        try {
            driver!!.quit()
        } catch (ign: Exception) {
        }

    }

    @Test
    fun testA() {

        val el0 = driver!!.findElementByAccessibilityId("General")
        el0.click()
        val el1 = driver!!.findElementByAccessibilityId("Keyboard")
        el1.click()

        val checkElement = driver!!.findElementByIosClassChain("**/XCUIElementTypeStaticText").getAttribute("name")
        assertEquals("Keyboards", checkElement)
    }

    @Test
    fun testB() {

        val possibleExtraClicks = 1

        var clickedListNames = mutableListOf<String>()
        for (i in 0..possibleExtraClicks) {
            var namesToClick = mutableListOf<String>()
            val elementList = driver!!.findElementsByClassName("XCUIElementTypeSwitch")
            for (element in elementList) {
                namesToClick.add(element.getAttribute("name"))
            }

            for (name in namesToClick) {
                if (clickedListNames.contains(name)) {
                    System.out.println("Name already found $name")
                }
                else {
                    val wait = WebDriverWait(driver, 2)
                    var toClick: WebElement? = null
                    try {
                        toClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeSwitch[@name=\"$name\"]")))
                    }
                    catch(e: java.lang.Exception) {
                        System.out.println("ERROR while trying to click $name")
                        System.out.println("Clicked ${clickedListNames.count()} names")
                    }

                    if (toClick != null) {
                        clickedListNames.add(toClick.getAttribute("name"))
                        toClick.click()

                        toClick = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"$name\"]")
                        if (toClick.getAttribute("name") == "Enable Dictation") {
                            driver!!.switchTo().alert().accept()
                        }
                    }

                }
            }
        }
        System.out.println("Clicked int ${clickedListNames.count()} Names, List of names : $clickedListNames")
    }

    companion object {
        private var firstTest: Boolean = true
    }

}