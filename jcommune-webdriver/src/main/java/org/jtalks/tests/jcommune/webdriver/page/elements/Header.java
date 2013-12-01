package org.jtalks.tests.jcommune.webdriver.page.elements;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class Header {
    @FindBy(id = "user-dropdown-administration-link")
    protected WebElement administrationDropdownMenu;
    @FindBy(id = "Administration")
    protected WebElement toggleAdmineModeLink;
    @FindBy(id = "user-dropdown-menu-link")
    protected WebElement userMenuLink;
    @FindBy(className = "btn-navbar")
    protected WebElement smallScreenMenuButton;
    @FindBy(id = "signin")
    protected WebElement loginLink;
    @FindBy(xpath = "//a[@href=\'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/logout\']")
    protected WebElement logOutButton;
    @FindBy(xpath = "//a[@href=\'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/inbox\']")
    protected WebElement privateMessagesLink;
    @FindAll({@FindBy(id = "links_editor"), @FindBy(id = "links_editor_top")})
    protected WebElement editExternalLinksControl;
    @FindBy(id = "searchText")
    protected WebElement searchInput;

    public Header(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public abstract void clickLogin();

    public abstract void clickLogout();

    public abstract void openPrivateMessages();

    public abstract boolean userIsLoggedIn();

    public abstract boolean isAdminModeOn();

    public abstract void pressOpenExternalLinksDialog();
}
