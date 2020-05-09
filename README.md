# Tender API #
API for issuing tenders and handling offers for them.

## Requirements

For building and running the application you will need:

- Java 11
- Maven
- Lombok plugin

Tender API is created with H2 in-memory DB, so no need for DB setup.

DB details:
- jdbc url = jdbc:h2:mem:tenderdb
- username = root 
- password = root

Application is running on port 8080.


## API usage examples


###### Create tender

`POST /tenders`

Body of request:
```
{
	"name":{tender name},
	"description":{description},
	"issuerId": {id of issuer},
	"constructionSiteId": {id of construction site}
}
```

------

###### Create offer

`POST /offers`

Body of request:
```
{
	"price": {price},
	"tenderId": {id of tender},
	"bidderId":{id of bidder}
}
```

------

###### Accept an offer for a tender

`PUT /tenders/offer`

Body of request:

```
{
	"tenderId": {tenderId},
	"offerId":{offerId}
}
```

------

###### List offers for a tender

`GET /tenders/{tenderId}/offers`

------

###### List offers of a bidder

`GET /bidders/{bidderId}/offers?tenderId={tenderId}`

*tenderId is not required argument

------

###### List tenders of a issuer

`GET /issuers/{issuerId}/tenders`

------
