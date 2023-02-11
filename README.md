# Java BACKEND Shop Store

Разработка по API FIRST командой "GOT"
Проект поделили по зонам ответственности:
- [https://t.me/Samael_IT](https://t.me/Samael_IT)  Spring Security, UsersApi, AuthApi, Docker, Docker Compose, архитектура
- [https://t.me/se_fil](https://t.me/se_fil)  AdsAPI, AOP, ExceptionHandler
- [https://t.me/irish_irish_f](https://t.me/irish_irish_f)  AdsCommentAPI, Ads Criteria Search 

Каждый занимался миграциями, мапперами, тестированием своей зоны ответственности
___
### В проекте реализованы следующие функции:
- регистрация;
- вход/выход;
- получение и обновление профиля;
- смена пароля;
- добавление, редактирование, удаление объявлений и коментариев к ним
- загрузка изображений объявлений и аватарок пользователей
- поиск по объвлениям
- AOP Logging 
- Spring Security
- Docker Compose конфигурация

--- 

### Дефолтные данные для входа. В миграции
- user@gmail.com userpass
- quser@gmail.com 12345678
- shapka@gmail.com 12345678
- admin@gmail.com adminpass

Коллекция для Postman src/test/resources/ShopCollectionForPostman.postman_collection.json

### Запуск через Docker 
- docker load -i "..\fix\adsclientv16.tar"
- docker run --rm -p 3000:3000 adsclient:v16
- docker run --rm -p 3000:3000 skypro-frontend-diploma-image
- docker run --name psql -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=shop -p 5432:5432 --rm postgres:12
------------------------------------------------------------
### Запуск через Docker Compose
- docker load -i "..\fix\adsclientv16.tar"
- docker load -i "..\skypro-frontend-diploma-image.tar.gz"
- docker build -t itsrv23/shop:1.0 .\ShopApp\
- docker build -t itsrv23/shop-admin:1.0 .\ShopAdmin\.
- docker compose up -d или docker-compose up -d (Linux)

### Для корректного отображения картинок в приложении запущенном в docker
- скопируйте картинки из папки [data/images/]() в раздел [data/images]() docker compose