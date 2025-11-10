# StreetWalker User Service — API README

Простая и понятная документация по REST API сервиса пользователей. Здесь описано, какие запросы отправлять, куда, с какими параметрами и что вы получите в ответ.

Базовый URL (локально): `http://localhost:8083`

Аутентификация:
- Открытые эндпоинты: всё под ` /auth/** `
- Закрытые эндпоинты: всё остальное (например, ` /users/** `) — требуют заголовок: `Authorization: Bearer <accessToken>`
- `accessToken` выдается через вход (`/auth/signin`), действует ограниченное время. Для продления используйте `refreshToken` через `/auth/refresh`.

Как читать примеры ниже:
- Любой JSON в теле запроса отправляйте с заголовком `Content-Type: application/json`.
- Примеры приведены в `curl`, но можно использовать Postman/HTTP-клиенты.

--------------------------------------------------------------------------------

Авторизация и токены (`/auth`)

1) Регистрация пользователя
- Метод и путь: `POST /auth/signup`
- Тело (JSON):
  {
    "username": "vasya",
    "email": "vasya@example.com",
    "phone": "79990000000",
    "password": "secret"
  }
- Успешный ответ: `201 Created`, тело — данные пользователя (UserDTO):
  {
    "username": "vasya",
    "email": "vasya@example.com",
    "phone": "79990000000",
    "firstName": null,
    "lastName": null,
    "description": null,
    "avatarUrl": null,
    "role": "USER",
    "status": "Active"
  }
- Ошибки: `400 Bad Request` (например, пользователь уже существует), в теле — строка с описанием.
- Пример:
  curl -X POST http://localhost:8083/auth/signup \
    -H "Content-Type: application/json" \
    -d '{"username":"vasya","email":"vasya@example.com","phone":"79990000000","password":"secret"}'

2) Вход (получение токенов)
- Метод и путь: `POST /auth/signin`
- Тело (JSON):
  {
    "phone": "79990000000",
    "password": "secret"
  }
- Успешный ответ: `200 OK`, тело (AuthResponse):
  {
    "accessToken": "<JWT>",
    "refreshToken": "<JWT>"
  }
- Ошибки: `400 Bad Request` (неверные данные/пользователь не найден), в теле — строка.
- Пример:
  curl -X POST http://localhost:8083/auth/signin \
    -H "Content-Type: application/json" \
    -d '{"phone":"79990000000","password":"secret"}'

3) Обновление токенов по refreshToken
- Метод и путь: `POST /auth/refresh?refreshToken=<строка>`
- Параметры: query-параметр `refreshToken` (в URL)
- Успешный ответ: `200 OK`, тело (AuthResponse) с новыми токенами.
- Ошибки: `400 Bad Request` (невалидный/просроченный token) — строка с описанием.
- Пример:
  curl -X POST "http://localhost:8083/auth/refresh?refreshToken=<ВАШ_REFRESH>"

4) Выход (инвалидировать refreshToken)
- Метод и путь: `POST /auth/logout?refreshToken=<строка>`
- Параметры: query-параметр `refreshToken`
- Успешный ответ: `200 OK`, тело: "Logged out successfully"
- Ошибки: `400 Bad Request` (например, ошибка БД) — строка с описанием.
- Пример:
  curl -X POST "http://localhost:8083/auth/logout?refreshToken=<ВАШ_REFRESH>"

--------------------------------------------------------------------------------

Пользователи (`/users`) — требует Authorization: Bearer <accessToken>

Важно: В конфиге безопасности `/users/**` защищены. Сначала авторизуйтесь через `/auth/signin`, возьмите `accessToken` и добавляйте заголовок:
  Authorization: Bearer <accessToken>

1) Получить список пользователей (постранично)
- Метод и путь: `GET /users`
- Параметры (query):
  - `page` — номер страницы (начиная с 0)
  - `size` — размер страницы (количество записей)
  - `sort` — сортировка, например `sort=username,asc`
- Успешный ответ: `200 OK`, тело — объект страницы Spring (Page<User>) со свойствами `content`, `totalElements`, `totalPages`, `size`, `number`, и т. п. Внутри `content` элементы — объект пользователя (модель User, содержит поля профиля, email, phone, role, status и т. д.).
- Ошибки: `400 Bad Request` (если pageable не распарсился корректно)
- Пример:
  curl -X GET "http://localhost:8083/users?page=0&size=10&sort=username,asc" \
    -H "Authorization: Bearer <ACCESS_TOKEN>"

2) Получить пользователя по id
- Метод и путь: `GET /users/{id}`
- Параметры: `id` — числовой идентификатор
- Успешный ответ: `200 OK`, тело — объект пользователя (модель User)
- Ошибки: `400 Bad Request` (если не найден)
- Пример:
  curl -X GET http://localhost:8083/users/1 \
    -H "Authorization: Bearer <ACCESS_TOKEN>"

3) Создать пользователя
- Метод и путь: `POST /users`
- Тело (JSON) — UserCreateDTO:
  {
    "username": "vasya",
    "password": "secret",
    "email": "vasya@example.com",
    "phone": "79990000000",
    "firstName": "Vasya",
    "lastName": "Pupkin",
    "description": "about me",
    "avatarUrl": "https://..."
  }
- Успешный ответ: `201 Created`, тело — UserDTO (без пароля).
- Ошибки: `400 Bad Request` (ошибка сохранения/валидации) — в ответе может прийти DTO с сообщением в `username` либо строка (в зависимости от источника ошибки).
- Пример:
  curl -X POST http://localhost:8083/users \
    -H "Authorization: Bearer <ACCESS_TOKEN>" \
    -H "Content-Type: application/json" \
    -d '{"username":"vasya","password":"secret","email":"vasya@example.com","phone":"79990000000"}'

4) Обновить пользователя по id
- ВАЖНО: Реальный путь в коде — `PUT /users/users/{id}` (двойное `users` — это текущее поведение контроллера).
- Метод и путь: `PUT /users/users/{id}`
- Параметры: `id`
- Тело (JSON) — UserUpdateDTO (только обновляемые поля, без пароля):
  {
    "username": "newName",
    "email": "new@example.com",
    "phone": "79990000001",
    "firstName": "New",
    "lastName": "Name",
    "description": "new about",
    "avatarUrl": "https://..."
  }
- Успешный ответ: `201 Created` (да, контроллер возвращает 201), тело — UserDTO
- Ошибки: `400 Bad Request` (например, пользователь не найден)
- Пример:
  curl -X PUT http://localhost:8083/users/users/1 \
    -H "Authorization: Bearer <ACCESS_TOKEN>" \
    -H "Content-Type: application/json" \
    -d '{"email":"new@example.com"}'

5) Поиск пользователей по подстроке username
- Метод и путь: `GET /users/search/username`
- Параметры (query):
  - `username` — строка для поиска (подстрока, регистр не важен)
  - `page`, `size`, `sort` — как в списке
- Успешный ответ: `200 OK`, тело — Page<User>
- Ошибки: `400 Bad Request` (если никого не нашли/ошибка сервиса) — в теле строка с описанием
- Пример:
  curl -X GET "http://localhost:8083/users/search/username?username=alice&page=0&size=5" \
    -H "Authorization: Bearer <ACCESS_TOKEN>"

--------------------------------------------------------------------------------

Структуры данных (кратко)

- UserDTO (ответ при создании/обновлении, безопасный):
  {
    "username": string,
    "email": string,
    "phone": string,
    "firstName": string|null,
    "lastName": string|null,
    "description": string|null,
    "avatarUrl": string|null,
    "role": string,
    "status": string
  }

- User (ответ в списках и при GET /users/{id}): объект доменной модели. Включает поля профиля (`id`, `username`, `description`, `avatarUrl`, `createdAt`, `updatedAt`), а также `email`, `phone`, `firstName`, `lastName`, `role`, `status`. Пароля в ответах нет.

- AuthResponse (ответ токенов):
  {
    "accessToken": string,
    "refreshToken": string
  }

- SignupRequest (тело /auth/signup): { username, email, phone, password }
- SigninRequest (тело /auth/signin): { phone, password }
- UserCreateDTO (тело POST /users): как SignupRequest + дополнительные поля профиля
- UserUpdateDTO (тело PUT /users/users/{id}): только обновляемые поля профиля (без пароля)

--------------------------------------------------------------------------------

Статусы ответов и ошибки (частые случаи)

- `200 OK` — успешные GET/POST (refresh/logout/signin)
- `201 Created` — успешные создания/обновления в `POST /users` и `PUT /users/users/{id}`
- `400 Bad Request` — неверные данные, не найдено, конфликт и т. п. (тело — строка или DTO с сообщением)
- `401 Unauthorized` — нет или неверный `Authorization: Bearer <accessToken>` для защищенных эндпоинтов

Подсказки
- Если получаете `401`, заново войдите через `/auth/signin` и используйте новый `accessToken`.
- Если `accessToken` истекает, используйте `/auth/refresh` c действующим `refreshToken`.
- Для выхода (инвалидации refresh) — `/auth/logout`.

