package uitests;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {

    @Step("Открыть страницу {url}")
    public WebSteps openPage(String url) {
        open(url);
        return this;
    }

    @Step("Найти репозиторий {repository}")
    public WebSteps findRepository(String repository) {
        $("[name = 'q']").click();
        $("[name = 'q']").setValue(repository).submit();
        return this;
    }

    @Step("Кликнуть по ссылке репозитория {repository}")
    public WebSteps clickLinkRepository(String repository) {
        $(linkText(repository)).click();
        return this;
    }

    @Step("Перейти на вкладку Issue")
    public WebSteps clickIssueTab() {
        $("#issues-tab").click();
        return this;
    }

    @Step("Проверить наличие Issue c названием {issueName}")
    public WebSteps shouldBeIssueWithName(String issueName) {
        $("#issue_1736_link").shouldHave(text(issueName));
        return this;
    }
}
