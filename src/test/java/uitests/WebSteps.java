package uitests;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {

    @Step("Открыть страницу {url}")
    public void openPage(String url) {
        open(url);
    }

    @Step("Найти репозиторий {repository}")
    public void findRepository(String repository) {
        $("[name = 'q']").click();
        $("[name = 'q']").setValue(repository).submit();
    }

    @Step("Кликнуть по ссылке репозитория {repository}")
    public void clickLinkRepository(String repository) {
        $(linkText(repository)).click();
    }

    @Step("Перейти на вкладку Issue")
    public void clickIssueTab() {
        $("#issues-tab").click();
    }

    @Step("Проверить наличие Issue c названием {issueName}")
    public void shouldBeIssueWithName(String issueName) {
        $("#issue_1736_link").shouldHave(text(issueName));
    }
}
