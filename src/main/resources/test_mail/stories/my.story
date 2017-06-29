
Scenario: A scenario in yandex market
Given I am on yandex page
When I go to the market page
And I go to the electronic page
And I go to the <product> page
And I input low <lowPrice> I will search
And I input high <highPrice> I will search
And I choice <manufacture>
And I click on apply button
And I remember the first element
And I go to view this product
Then I see that name this product equals remember name

Examples:
|product                        |lowPrice | highPrice | manufacture |
|Мобильные телефоны             |40000    |           | Samsung     |
|Наушники и Bluetooth-гарнитуры |17000    |25000      | Beats       |