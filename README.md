Описание:
---
```
Создать сервис, который обращается к сервису курсов валют, и отдает gif в ответ:  

если курс по отношению к рублю за сегодня стал выше вчерашнего,  
то отдаем рандомную отсюда https://giphy.com/search/rich  
если ниже - отсюда https://giphy.com/search/broke  
Ссылки  
REST API курсов валют - https://docs.openexchangerates.org/  
REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide  
```  
Endpoints:
---  
Получить код для валюты:  
```
GET /currency
```  
Получить гифку по слову "rich"
```
GET /gif
```  
Получить гифку по слову "broke"
```
GET /sadGif
```  
Получить гифку в зависимости от результа сравнения курса вчерашнего дня и актуального
```
GET /result
``` 
Все настройки вынесены в application.yml
---
```

alphaCase:
  feign:
    gif:
      apiKey: "6WtcHCNEkbrrN57FNDD68hJAJChSRuCE"
      url: "api.giphy.com/v1/gifs/search"
      searchPhrase:
        rich: ${GIF_PHRASE:rich}
        sad: ${GIF_SAD_PHRASE:broke}
      limit: ${GIF_LIMIT:1}

    currency:
      apiKey: "5af44adea3da47ffaa7709b5eef23eb1"
      url: "https://openexchangerates.org/api"
      symbol: ${CURRNECY_SYMBOL:USD}
```
Возможно изменить базовую валюту для сравнения, фразы поиска в случае позитивного и негативного изменения, Api Key для каждого api.
Переменные помеченные ${} могут быть заданы через окружение Docker при запуске, а также имееют базовое значение.

Запуск
---
```
$ git clone https://github.com/Rehtang/alphaCase
$ cd alphaСase
$ docker-compose up
```
