# favorite-series-service

This is simple REST API responsible for providing data to [favorite-series-frontend](https://github.com/bartekszerlag/favorite-series-frontend).

## Methods
- `GET /series` - returns all TV series added to list with additional fields: 
    - *id* - automatically generated
    - *imdbRating* - TV series rating fetched from external API [OMDb API](http://www.omdbapi.com/)
- `POST /series` - adds new TV series
    - request example: *{ title: "Ozark", platform: "Netflix" }* , content-type *application/json*
    - *platform* field accepts any string but only "netflix" or "hbo" values returns in GET method platform set to NETFLIX / HBO. 
      Any other values returns platform OTHER.
- `DELETE /series/{id}` - removes TV series with specific id

## How to run

To start this service you need to go to the project directory and run:

### `./mvnw spring-boot:run`

Open [http://localhost:8080](http://localhost:3000) to view it in the browser.