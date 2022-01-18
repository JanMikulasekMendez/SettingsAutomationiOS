import io.appium.java_client.MobileElement
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.IOException
import java.net.URL


class SettingsTestClassDictionary {

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


    //Navigate to Dictionary Screen
    @Test
    fun test01() {

        val el0 = driver!!.findElementByAccessibilityId("General")
        el0.click()

        val el1 = driver!!.findElementByAccessibilityId("Dictionary") as MobileElement
        el1.click()

        val checkElement = driver!!.findElementByIosClassChain("**/XCUIElementTypeStaticText").getAttribute("name")
        assertEquals("Dictionary", checkElement)
    }

    //Create SearchTree from elements
    @Test
    fun test02() {

        class TreeNode<T>(value:T){
            var value:T = value
            var parent:TreeNode<T>? = null

            var children:MutableList<TreeNode<T>> = mutableListOf()

            fun addChild(node:TreeNode<T>){
                children.add(node)
                node.parent = this
            }
            override fun toString(): String {
                var s = "${value}"
                if (!children.isEmpty()) {
                    s += " {" + children.map { it.toString() } + " }"
                }
                return s
            }
        }

        val ElementTree = TreeNode<WebElement>(driver!!.findElement(By.xpath("//XCUIElementTypeApplication")))
        val screenElement = driver!!.findElement(By.xpath("//XCUIElementTypeApplication"))

        fun getChildren(parent : WebElement, parentNode : TreeNode<WebElement>) {
            var children = parent.findElements<WebElement>(By.xpath("./*"))
            for (child in children) {
                val node = TreeNode(child)
                parentNode.addChild(node)
                getChildren(child,node)
            }
        }
        getChildren(screenElement,ElementTree)

        //check if result is table to make sure structure is okay
        val checkElement = ElementTree.children[0].children[0].children[0].children[0].children[0].children[1].children[0].children[0].children[0].children[0].children[0].children[0].value.getAttribute("type")
        System.out.println(checkElement)
        assertEquals("XCUIElementTypeTable", checkElement)

        //print out whole tree if required
        //System.out.println(ElementTree)
    }

    @Test
    fun test03() {
        driver!!.navigate().back()
        val checkElement = driver!!.findElementByIosClassChain("**/XCUIElementTypeStaticText").getAttribute("name")
        assertEquals("General", checkElement)
    }

    companion object {
        private var firstTest: Boolean = true
    }

}