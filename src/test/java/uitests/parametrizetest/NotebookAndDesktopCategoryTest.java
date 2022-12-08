package uitests.parametrizetest;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import uitests.BaseTest;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class NotebookAndDesktopCategoryTest extends BaseTest {
    private static final String BASE_URL = "https://www.wildberries.ru";

    @BeforeAll
    @Step("Задать базовый URL " + BASE_URL)
    static void beforeSuit(){
        Configuration.baseUrl = BASE_URL;
    }

    static Stream<Arguments> verifyAvailableFilter() {
        return Stream.of(
                Arguments.of("Ноутбуки", List.of("Срок дост1авки", "Бренд", "Продавец", "Цена, ₽", "Скидка", "Цвет",
                        "Назначение ноутбука", "Диагональ экрана", "Тип матрицы", "Разрешение экрана", "Поверхность экрана",
                        "Операционная система", "Линейка процессоров", "Количество ядер процессора", "Объем оперативной памяти (Гб)",
                        "Производитель видеопроцессора", "Видеоадаптер", "Объем памяти видеоадаптера", "Оптический привод", "Тип накопителя",
                        "Объем накопителя HDD", "Объем накопителя SSD", "Процессор")),

                Arguments.of("Компьютеры и моноблоки", List.of("Категория", "Срок доставки", "Бренд", "Продавец", "Цена, ₽",
                        "Скидка", "Объем оперативной памяти (Гб)", "Объем памяти видеоадаптера", "Операционная система", "Тип видеокарты")),

                Arguments.of("Мониторы", List.of("Срок доставки", "Бренд", "Продавец", "Цена, ₽", "Скидка", "Цвет",
                        "Диагональ", "Разрешение экрана", "Тип матрицы", "Формат экрана", "Частота обновления", "Вид разъема",
                        "Поверхность экрана", "Размер VESA", "Тип блока питания")),

                Arguments.of("Периферийные аксессуары", List.of("Категория", "Срок доставки", "Бренд", "Продавец", "Скидка", "Цена, ₽"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверяем в подкатегории {0} доступные фильтры {1}")
    @Tag("High")
    void verifyAvailableFilter(String category, List<String> filters) {
        step("Открываем страницу Ноутбуки", () -> {
            open("/catalog/elektronika/noutbuki-periferiya");
        });

        step("Выбираем категорию " + category, () -> {
            $(".sidemenu").$(byText(category)).click();
        });

        step("Проверяем доступные фильтры", () -> {
            $$(".filter__name").filter(visible).shouldHave(CollectionCondition.texts(filters));
        });
    }

    @CsvSource({
            "Acer", "lenovo", "Asus", "HP", "Apple", "Infinix", "Xiaomi"
    })

    @ParameterizedTest(name = "Проверка фильтра ноутбуков по производителю {0}")
    @Tag("High")
    void verifyFilterByBrand(String filter) {

        open("/catalog/elektronika/noutbuki-pereferiya/noutbuki-ultrabuki");
        $("[data-filter-name=fbrand]").$(withText(filter)).click();
        $$(".product-card__brand").shouldHave(CollectionCondition.allMatch(
                "Проверяем, что в карточках товара содержится наименование бренда",
                (selenideElement -> selenideElement.getText().contains(filter))));
    }

    @CsvFileSource(resources = "currencies.csv")
    @ParameterizedTest(name = "Выбираем доступную валюту {0}, проверяем наименование в header {1} и в цене товара {2}")
    void verifyPriceByCurrencies(String currency, String latinName, String mark){
        open("/catalog/elektronika/noutbuki-periferiya");
        $("[data-link=currentCurrency]").hover();
        $(by("action", "/webapi/setcurrency")).$(byText(currency)).click();
        $("[data-link=currentCurrency]").shouldHave(text(latinName));
        $(".price__wrap").shouldHave(text(mark));
    }

}
