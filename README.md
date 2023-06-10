# Forum
### Описание:
Система предоставляет возможность ведения данных из базы данных форума. А именно: добавление, удаление и редактирование статей, комментариев, данных о пользователях и их ролях в системе. Также в системе присутствует такой функционал, как добавление статей в список избранных, просмотр своих статей, пометка статьи лайком или дизлайком, удаление аккаунта и его восстановление с помощью электронной почты. 
Вся работа с системой производится через web-интерфейс, который реализован в виде одностраничного приложения, написанного с использованием фреймворка Angular.

### Содержание
 + Логическая модель базы данных
 + Физическая модель базы данных
 + Диаграмма вариантов использования
 + Интерфейс пользователя
 + Развертывание


## Структура базы данных
- Логическая модель базы данных
<p align="center">
  <img src="/images/db_logic.png">
  <br>
  <em>Рисунок 1 – Логическая модель базы данных</em>
</p>

- Физическая модель базы данных
<p align="center">
  <img src="/images/db_physic.png">
  <br>
  <em>Рисунок 2 – Физическая модель базы данных</em>
</p>


## Моделирование
<p align="center">
  <img src="/images/diagram_variant.png">
  <br>
  <em>Рисунок 3 – Диаграмма вариантов использования</em>
</p>

## Интерфейс пользователя
<p align="center">
  <img src="/images/1.png">
  <br>
  <em>Рисунок 4 – Начальная страница системы</em>
</p>

<p align="center">
  <img src="/images/2.png">
  <br>
  <em>Рисунок 5 – Форма регистрации</em>
</p>

<p align="center">
  <img src="/images/3.png">
  <br>
  <em>Рисунок 6 – Форма аутентификации</em>
</p>

<p align="center">
  <img src="/images/4.png">
  <br>
  <em>Рисунок 7 – Страница со списком статей</em>
</p>

<p align="center">
  <img src="/images/5.png">
  <br>
  <em>Рисунок 5 – Просмотр статьи</em>
</p>

<p align="center">
  <img src="/images/6.png">
  <br>
  <em>Рисунок 6 – Продолжение статьи</em>
</p>

<p align="center">
  <img src="/images/7.png">
  <br>
  <em>Рисунок 7 – Форма создания статьи</em>
</p>

<p align="center">
  <img src="/images/8.png">
  <br>
  <em>Рисунок 8 – Выпадающее меню, при нажатии на иконку профиля</em>
</p>

<p align="center">
  <img src="/images/9.png">
  <br>
  <em>Рисунок 9 – Профиль пользователя</em>
</p>

<p align="center">
  <img src="/images/10.png">
  <br>
  <em>Рисунок 10 – Вкладка «Settings»</em>
</p>

В системе также присутствует режим администратора, который дает возможность удаления любой статьи, любого комментария, а также просмотр информации о всех пользователях и присвоение им новой роли.
<p align="center">
  <img src="/images/11.png">
  <br>
  <em>Рисунок 11 – Список статей</em>
</p>

<p align="center">
  <img src="/images/12.png">
  <br>
  <em>Рисунок 12 – Информация о пользователях</em>
</p>

<p align="center">
  <img src="/images/13.png">
  <br>
  <em>Рисунок 13 – Присвоение роли «Администратор» пользователю</em>
</p>

# DEPLOY
### Manual build to jar:
- FRONT
```
    cd ForumFrontend
    ng build --configuration production
```
- BACKEND
```
    cp -R ../ForumFrontend/dist/forum/* src/main/resources/static/
    mvn clean package
    java -jar ForumBackend\target\ForumBackend-0.0.1-SNAPSHOT.jar
```

### Build with maven: 
    cd Forum
    mvn install
    java -jar ForumBackend\target\ForumBackend-0.0.1-SNAPSHOT.jar
