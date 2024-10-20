# Тестовое задание SHIFT Lab back-end

В качестве ответа возвразается application/json

В качестве тела запроса используется application/json

# Endpoints
sellers, transactions

# Endpoint sellers

## POST /sellers 

Создание нового продавца

### Body:

```
{
    "name": "string",
    "contactInfo": "string"
}
```

- name - имя продавца
- contactInfo - контактная информация продавца

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "name": "string",
    "contactInfo": "string",
    "registrationDate": "string"
}
```

В качестве ответа возвращаются данные созданного продавца

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /sellers/get/{id}

Получение информации о конкретном продавце

### Body:

Тело запроса не требуется

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "name": "string",
    "contactInfo": "string",
    "registrationDate": "string"
}
```

В качестве ответа возвращаются данные продавца

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## PATCH /sellers/patch/{id}

### Body:

```
{
    "name": "string",
    "contactInfo": "string"
}
```

- name - имя продавца
- contactInfo - контактная информация продавца

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "name": "string",
    "contactInfo": "string",
    "registrationDate": "string"
}
```

В качестве ответа возвращаются обновленные данные продавца

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## DELETE /sellers/delete/{id}

### Body:

Тело запроса отсутствует

### Response:

#### Http status code 200:

В качестве ответа возвращается только код 200

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /sellers/best

Получение самого продуктивного продавца

Также возможно указание параметра period, который отвечает за нахождение самого продуктивного
продавца за указанный период. Может принимать только значения hour, day, month, quarter, year

### Body:

Тело запроса отсутствует

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "name": "string",
    "contactInfo": "string",
    "registrationDate": "string"
}
```

В качестве ответа возвращается информация о самом продуктивном продавце

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /sellers/{cost}

Получение списка продавцов с суммой, меньше указанной

Также возможно указание параметра period, который отвечает за нахождение самого продуктивного
продавца за указанный период. Может принимать только значения hour, day, month, quarter, year

### Body:

Тело запроса отсутствует

### Response:

#### Http status code 200:

```
[
{
    "id": integer,
    "name": "string",
    "contactInfo": "string",
    "registrationDate": "string"
}
]
```

В качестве ответа возвращается список найденных продавцов

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

# Endpoint transactions

## POST /transactions/create

Создание новой транзакции

### Body:

```
{
    "seller": integer,
    "amount": integer,
    "paymentType": "string"
}
```

- seller - id продавца, к которому относится транзакция
- amount - сумма транзакции
- paymentType - тип оплаты, пожет принимать только значения: CASH, CARD, TRANSFER

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "seller": integer,
    "amount": integer,
    "paymentType": "string",
    "transactionDate": "string"
}
```

В качестве ответа возвращаются данные созданной транзакции

transactionDate - Дата в формате ISO 8601

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /transactions/getall

Получение всех транзакций

### Body:

Тело запроса не требуется

### Response:

#### Http status code 200:

```
{
[
    "id": integer,
    "seller": integer,
    "amount": integer,
    "paymentType": "string",
    "transactionDate": "string"
]
}
```

В качестве ответа возвращается массив транзакций

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /transactions/{id}

Получение конкретной транзакции

### Body:

Тело запроса не требуется

### Response:

#### Http status code 200:

```
{
    "id": integer,
    "seller": integer,
    "amount": integer,
    "paymentType": "string",
    "transactionDate": "string"
}
```

В качестве ответа возвращается информация о транзакции

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

## GET /transactions/byseller/{id}

Получение всех транзакций конкретного продавца

### Body:

Тело запроса не требуется

### Response:

#### Http status code 200:

```
{
[
    "id": integer,
    "seller": integer,
    "amount": integer,
    "paymentType": "string",
    "transactionDate": "string"
]
}
```

В качестве ответа возвращается массив транзакций

#### Http status code 400, 500

```
{
    "errorMessage": "string"
}
```

В качестве ответа возвращается сообщение об ошибке

# Инструкция по запуску

Создать базу данных shift и пользователя login: shift/ password: shift

Главным классом (точкой входа) является main.java.ru.cft.shifttest.ShiftApp
