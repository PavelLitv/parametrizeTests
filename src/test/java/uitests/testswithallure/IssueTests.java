package uitests.testswithallure;

import org.junit.jupiter.api.Test;
import uitests.BaseTest;
import uitests.WebSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class IssueTests extends BaseTest {

    private final String
            URL = "https://github.com",
            REPOSITORY = "SeleniumHQ/docker-selenium",
            ISSUE_NAME = "Connection refusal in router and hence getting timeout exception straight from Chrome node";

    @Test
    void verifyIssueTextFreeSelenide() {
        open(URL);        
        $("[name = 'q']").setValue(REPOSITORY).submit();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("#issue_1736_link").shouldHave(text(ISSUE_NAME));
    }

    @Test
    void verifyIssueTextLambdaSteps() {
        step("Открыть страницу " + URL, () -> {
            open(URL);
        });

        step("Найти репозиторий " + REPOSITORY, () -> {            
            $("[name = 'q']").setValue(REPOSITORY).submit();
        });

        step("Кликнуть по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });

        step("Перейти на вкладку Issue", () -> {
            $("#issues-tab").click();
        });

        step("Проверить наличие Issue c названием " + ISSUE_NAME, () -> {
            $("#issue_1736_link").shouldHave(text(ISSUE_NAME));
        });
    }

    @Test
    void verifyIssueTextByAnnotatedSteps() {
        WebSteps webSteps = new WebSteps();

        webSteps
                .openPage(URL)
                .findRepository(REPOSITORY)
                .clickLinkRepository(REPOSITORY)
                .clickIssueTab()
                .shouldBeIssueWithName(ISSUE_NAME);
    }
}
