import com.google.common.collect.ImmutableMap
import io.appium.java_client.MobileElement
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.IOException
import java.net.URL


class SettingsTestClass {

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

        val el0 = driver!!.findElementByAccessibilityId("General") as MobileElement
        el0.click()
        val el1 = driver!!.findElementByAccessibilityId("Keyboard") as MobileElement
        el1.click()

        assertEquals(1, 1)
    }

    @Test
    fun testB() {

        val el0 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Auto-Capitalization\"]") as MobileElement
        el0.click()
        val el1 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Auto-Correction\"]") as MobileElement
        el1.click()
        val el2 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Check Spelling\"]") as MobileElement
        el2.click()
        val el3 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Enable Caps Lock\"]") as MobileElement
        el3.click()
        val el4 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Smart Punctuation\"]") as MobileElement
        el4.click()
        val el5 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Character Preview\"]") as MobileElement
        el5.click()
        val el6 =
            driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Period shortcut\"]") as MobileElement
        el6.click()
        val el7 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Enable Dictation\"]") as MobileElement
        el7.click()
        driver!!.switchTo().alert().accept()
        val el8 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Predictive\"]") as MobileElement
        el8.click()
        val el9 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Slide to Type\"]") as MobileElement
        el9.click()



        val el10 = driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Memoji Stickers\"]") as MobileElement
        el10.click()

        fun el9a() {
            val wait = WebDriverWait(driver, 2)
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeSwitch[@name=\"Delete Slide-to-Type by Word\"]")))
            } catch (e: java.lang.Exception) {
                return
            }
            val el9a =
                driver!!.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Delete Slide-to-Type by Word\"]") as MobileElement
            el9a.click()
        }
        el9a()

        driver!!.navigate().back()

        assertEquals(1, 1)
    }

    @Test
    fun testC() {
        val el1 = driver!!.findElementByAccessibilityId("Dictionary") as MobileElement
        el1.click()
    }

    @Test
    fun testD() {
        val myList = driver!!.executeScript("mobile: source", ImmutableMap.of("format", "description"))
        System.out.println(myList)
    }

    companion object {
        private var firstTest: Boolean = true
    }

}