import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;


public class CheckFormTests extends TestBase {

    //проверяем все поля
    @Test
    void positiveAllRequiredFieldsTest() {
        String name = "Evgeny";
        String lastName = "Usatenko";
        String mobile = "1234567890";
        String gender = "Male";
        String email = "Usatenko@sdasd.ru";

        step("Открываем форму регистрации студентов", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Заполняем данные студента", () -> {
            $("#firstName").val(name);
            $("#lastName").val(lastName);
            $("#userEmail").val(email);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").val(mobile);
        });

        step("Выбор даты рождения", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("April");
            $(".react-datepicker__year-select").selectOption("1984");
            $(".react-datepicker__day--005:not(.react-datepicker__day--outside-month)").click();
        });

        step("Выбор предметов", () -> {
            $("#subjectsInput").setValue("Maths").pressEnter(); // вместо sendKeys(Keys.ENTER);
            $(byText("Sports")).click();
            $(byText("Reading")).click();
            $(byText("Music")).click();
        });

        step("Загрузить картинку", () -> {
            $("#uploadPicture").uploadFromClasspath("pic.jpg");//uploadFile(new File("src/test/resources/pic.jpg"));
        });

        step("Заполнить адрес", () -> {
            $("#currentAddress").setValue("street Test");
        });

        step("Выбрать штат и город", () -> {
            $("#state").scrollTo().click(); //вместо scrollIntoView(true)
            $(byText("NCR")).click();
            $("#city").click();
            $(byText("Delhi")).click();
        });

        step("Отправить заполненную форму", () -> {
            $("#submit").click();
        });

        //проверки
        step("Проверка введённых данных", () -> {
        $("tbody").$(byText("Student Name")).parent().shouldHave(text(name + " " + lastName));
        $("tbody").$(byText("Student Email")).parent().shouldHave(text(email));
        $("tbody").$(byText("Gender")).parent().shouldHave(text(gender));
        $("tbody").$(byText("Mobile")).parent().shouldHave(text(mobile));
        $("tbody").$(byText("Date of Birth")).parent().shouldHave(text("05 April,1984"));
        $("tbody").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $("tbody").$(byText("Hobbies")).parent().shouldHave(text("Sports, Reading, Music"));
        $("tbody").$(byText("Picture")).parent().shouldHave(text("pic.jpg"));
        $("tbody").$(byText("Address")).parent().shouldHave(text("street Test"));
        $("tbody").$(byText("State and City")).parent().shouldHave(text("NCR Delhi"));
        });
    }
}