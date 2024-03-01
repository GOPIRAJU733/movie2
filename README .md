Given five files,

- `MovieController.java`
- `MovieRepository.java`
- `MovieJpaService.java`
- `MovieJpaRepository.java`
- `Movie.java`

And also given a database file `movies` which contains the `MOVIELIST` table.

#### MOVIELIST Table

   |  Columns  |  Type   |
   | :-------: | :-----: |
   |  movieId  | INTEGER |
   | movieName |  TEXT   |
   | leadActor |  TEXT   |



<MultiLineNote>

Use only `movielist` as the table name in both query writing and in the model class within your code.

</MultiLineNote>

### Completion Instructions

- `Movie.java`: `Movie` class should contain the following attributes.

    | Attribute |  Type  |
    | :-------: | :----: |
    |  movieId  |  int   |
    | movieName | String |
    | leadActor | String |


- `MovieRepository.java`: Create an `interface` containing the required methods.
- `MovieJpaService.java`: Update the service class with logic for managing movie data.
- `MovieController.java`: Create the controller class to  handle HTTP requests. 
- `MovieJpaRepository.java`: Create an interface that extends the `JpaRpository` Interface.

Implement the following APIs.

### API 1

#### Path: `/movies`

#### Method: `GET`

#### Description:

Returns a list of all `movies` from the `movieList`.

#### Response

```
[
    {
        "movieId": 1,
        "movieName": "Avengers: Endgame",
        "leadActor": "Robert Downey Jr."
    },
   ...
]
```

### API 2

#### Path: `/movies`

#### Method: `POST`

#### Description:

Creates a new movie in the `movieList`. The `movieId` is auto-incremented.

#### Request

```
{
    "movieName": "Dangal",
    "leadActor": "Aamir Khan"
}
```

#### Response

```
{
    "movieId": 6,
    "movieName": "Dangal",
    "leadActor": "Aamir Khan"
}
```

### API 3

#### Path: `/movies/{movieId}`

#### Method: `GET`

#### Description:

Returns a movie based on the `movieId`. If the given `movieId` is not found in the `movieList`, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```
{
    "movieId": 2,
    "movieName": "Avatar",
    "leadActor": "Sam Worthington"
}
```

### API 4

#### Path: `/movies/{movieId}`

#### Method: `PUT`

#### Description:

Updates the details of a movie in the `movieList` based on the `movieId`. If the given `movieId` is not found in the `movieList`, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```
{
    "movieName": "Avatar 2",
    "leadActor": "S.H.J Worthington"
}
```

#### Success Response

```
{
    "movieId": 2,
    "movieName": "Avatar 2",
    "leadActor": "S.H.J Worthington"
}
```

### API 5

#### Path: `/movies/{movieId}`

#### Method: `DELETE`

#### Description:

Deletes a movie from the `movieList`  based on the `movieId`. If the given `movieId` is not found in the `movieList`, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


**Do not modify the code in `MovieApplication.java`**

**Do not  modify anything in the `application.properties` file**

**Do not add any SQL files**