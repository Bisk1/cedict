# CC-CEDICT as a complete microservice

Demo:
https://cedict.herokuapp.com/

CC-CEDICT:
http://cc-cedict.org/wiki/start

Technologies used:
- Spring Boot
- Maven
- JPA
- H2
- Heroku

Info:
- This repository contains database file (> 60 MB) for H2 so the application is already populated with CC-CEDICT entries
- The entries were filtered for performance to contain no more than 255 characters; some lengthy English explanations from original CEDICT were skipped for this reason
- The application is integrated with Heroku PaaS - can be deployed with a single command (`git push heroku master`)
- Because of the embedded database configuration used and the fact that it is a part of repository, the data size is not restricted by platform database limits - can even run on Heroku free dynos despite total rows count over 430.000 (currently free plan allows up to 10.000 rows).
- The application code contains controller used for populating the database (`LoaderController`), but it is commented out (annotation) for safety
- data is exposed through REST API
- only simplified Chinese is supported

## API:

### 1) Translate Chinese word specified as Chinese characters only

`[base URL]/api/zh/[Chinese characters]`

Returns list of all possible Chinese words (pairs of characters + pinyin) matching this characters.

#### Example

Request:

https://cedict.herokuapp.com/api/zh/好/hao4 GET

Response:

```json
[
    {
        "pinyin": "hao3",
        "text": "好",
        "translations": [
            "good",
            "well",
            "proper",
            "good to",
            "easy to",
            "very",
            "so",
            "(suffix indicating completion or readiness)",
            "(of an unmarried couple) to be close",
            "to be keen on each other"
        ]
    },
    {
        "pinyin": "hao4",
        "text": "好",
        "translations": [
            "to be fond of",
            "to have a tendency to",
            "to be prone to"
        ]
    }
]
```

This method return list of pairs (characters + pinyin) with respective English translations, as in general single written form of Chinese word can be related to multiple pronouncations and each of them has different translations associated.


### 2) Translate Chinese word specified as Chinese characters and pinyin

`[base URL]/api/zh/[Chinese characters]/[pinyin]`

Returns Chinese word and associated translations (characters + pinyin) matching this characters and pinyin.

#### Example

Request:

https://cedict.herokuapp.com/api/zh/好/hao4 GET

Response:

```json
{
    "pinyin": "hao4",
    "text": "好",
    "translations": [
        "to be fond of",
        "to have a tendency to",
        "to be prone to"
    ]
}
```

This method matches at most one Chinese word with translations as a pair (characters + pinyin)  uniquely identifies a word


### 3) Translate English word

`[base URL]/api/en/[English word]`

Return English word and Chinese translations associated with it.

#### Example

Request:

https://cedict.herokuapp.com/api/en/Europe GET

Response:
```json
{
    "text": "Europe",
    "translations": [
        [
            "欧",
            "Ou1"
        ],
        [
            "欧洲",
            "Ou1 zhou1"
        ],
        [
            "欧罗巴",
            "Ou1 luo2 ba1"
        ],
        [
            "欧罗巴洲",
            "Ou1 luo2 ba1 Zhou1"
        ]
    ]
}
```







