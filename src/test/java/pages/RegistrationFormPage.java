package pages;

import com.codeborne.selenide.SelenideElement;
import components.DatePickerComponent;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormPage {
    //Elements
    private SelenideElement submitButton = $x("//button[@type='submit']");
    private DatePickerComponent datePicker = new DatePickerComponent();

    private static String inputFieldXpathTemplate = "//input[@data-testid='%s']";
    private static String outputFieldTemplate = "//p[text()='%s']/following::p[1]";
    private static String comboboxTemplate = "//*[contains(text(), '%s')]/following::*[@role='combobox'][1]";
    private static String optionTemplate = "//*[@role='listbox']//*[contains(text(), '%s')]";
    private static String dataValueTemplate = "//*[@data-value='%s']";
    private static String genderXpathTemplate = "//input[@data-testid='gender' and @value='%s']";
    private static String hobbyXpathTemplate = "//input[@data-testid='hobbies' and @value='%s']";
    private static String citySelect = "city-select";
    private static String textAreaXpathTemplate = "//textarea[@data-testid='%s']";
    private static String sliderXpath = "//span[contains(@class, 'MuiSlider-thumb')]";
    private static String uploadFileXpath = "//input[@type='file']";
    private static String registrationFormXpath = "//form";
    private static String advertisingMenuCloseButtonXpath = "//*[@data-testid='ClearIcon']";

    //Actions
    public void openPage(){
        open("automation-practice-form/");
    }

    public void clickSubmitButton() {
        submitButton.scrollTo().click();
    }

    public void enterField(String fieldName, String value) {
        String xpath = String.format(inputFieldXpathTemplate, fieldName);
        $x(xpath).scrollTo().setValue(value);
    }

    public void assertFieldOutput(String fieldName, String expectedValue) {
        String xpath = String.format(outputFieldTemplate, fieldName);
        $x(xpath).scrollTo().shouldBe(visible).shouldHave(text(expectedValue));
    }

    public void genderChoice(String gender) {
        String xpath = String.format(genderXpathTemplate, gender);
        $x(xpath).scrollTo().click();
    }

    public void hobbyChoice(String hobby) {
        String xpath = String.format(hobbyXpathTemplate, hobby);
        $x(xpath).scrollTo().click();
    }

    public void openCombobox(String name) {
        String xpath = String.format(comboboxTemplate, name);
        $x(xpath).scrollTo().click();
    }

    public void selectOption(String optionValue) {
        String xpath = String.format(optionTemplate, optionValue);
        $x(xpath).shouldBe(visible).click();
    }

    public void languageChoice(String language) {
        String xpath = String.format(dataValueTemplate, language);
        $x(xpath).shouldBe(visible).click();
    }

    public void stateChoice(String state) {
        String xpath = String.format(dataValueTemplate, state);
        $x(xpath).shouldBe(visible).click();
    }

    public void cityChoice(String city) {
        $(By.id(citySelect)).selectOption(city);
    }

    public void textAreaFill (String name, String text) {
        String xpath = String.format(textAreaXpathTemplate, name);
        $x(xpath).scrollTo().setValue(text);
    }

    public void sliderMove (int xOffset) {
        actions()
                .clickAndHold($(By.xpath(sliderXpath)))
                .moveByOffset(xOffset, 0)
                .release()
                .perform();
    }

    public void fileUpload(String fileName) {
        $x(uploadFileXpath).uploadFromClasspath(fileName);
    }

    public void setBirthDate(String year, String month, String day) {
        datePicker.setDate(year, month, day);
    }

    public void registrationFormTextAssert(String expectedText) {
        $x(registrationFormXpath).shouldHave(text(expectedText));
    }

    public void advertisingMenuClose() {
        $x(advertisingMenuCloseButtonXpath).shouldBe(visible).click();
    }
}
