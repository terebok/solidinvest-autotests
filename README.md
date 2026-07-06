# SolidInvest Авто-тесты

Проект для автоматического тестирования сайта https://solidinvest.ru/.

## Технологии
- Java 11
- Maven
- Selenium WebDriver
- TestNG
- Page Object Model
- Allure Reports
- WebDriverManager (автоустановка драйверов)

## Тест-кейсы
1. **TC-01** - Проверка загрузки главной страницы (`MainPageTest`)
2. **TC-03** - Проверка перехода по ссылке "Контакты" (`ContactsTest`)
3. **TC-04** - Проверка отправки сообщения через форму обратной связи (`ContactFormTest`)

## Запуск тестов

### Локально (с видимым браузером):
```bash
mvn clean test