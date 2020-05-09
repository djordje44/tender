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

Pre-inserted data:
    
    Construction sites:
    - ID: 1, PROJECT_NAME: Warehouse for company X, DESCRIPTION: Building warehouse for company X, LOCATION: North Boulevard 123
    - ID: 2, PROJECT_NAME: Old Trafford renovation, DESCRIPTION: Renovation of football stadium, LOCATION: Major Street 123
    
    Issuers:
    - ID: 1, FIRST_NAME: John, LAST_NAME: Doe
    - ID: 2, FIRST_NAME: Brian, LAST_NAME: Mills
    
    Bidders:
    - ID: 1, COMPANY_NAME: Hyper Building
    - ID: 2, COMPANY_NAME: Lego Constructions


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
