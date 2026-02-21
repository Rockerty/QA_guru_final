package components;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class DatePickerComponent {

    public void setDate(String year, String month, String day) {
        // Открыть календарь
        $x("//*[@data-testid='CalendarIcon']").scrollTo().shouldBe(visible).click();

        // Выбрать год
        $x("//*[contains(@class, 'MuiPickersCalendarHeader-switchViewButton')]").shouldBe(visible).click();
        $x("//button[text()='" + year + "']").scrollTo().shouldBe(visible).click();

        // Выбрать месяц
        $x("//button[text()='" + month + "']").scrollTo().shouldBe(visible).click();

        // Выбрать день
        $x("//button[text()='" + day + "']").scrollTo().shouldBe(visible).click();
    }
}