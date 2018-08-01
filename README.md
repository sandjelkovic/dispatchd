# dispatchd 
Gitlab CI : [![pipeline status](https://gitlab.com/sandjelkovic/dispatchd/badges/develop/pipeline.svg)](https://gitlab.com/sandjelkovic/dispatchd/commits/develop)

Travis CI : [![Build Status](https://travis-ci.org/sandjelkovic/dispatchd.svg?branch=develop)](https://travis-ci.org/sandjelkovic/dispatchd)

 Application to notify when episodes go live or aggregate those notifications in reports

#### Purpose
Goal of this application is to provide updates for episodes airdates in the form of customisable reports.

#### Media content provider services
* Currently, only https://trakt.tv/ is being used to retrieve information about Shows, Seasons and Episodes, including airdates.
* Importing of shows to local database is also supported only from trakt.

#### Development setup 
* Coming soon

#### Credentials to update
* trakt.appId
* trakt.appSecret

#### Importing shows
At this moment, only trakt is supported for imports. URL that needs to be provided to the importer endpoint is in form of https://trakt.tv/shows/star-trek-the-next-generation

### Default ports
| Service 	| Port
|----------	|-----:	|
| Configuration service 	| 8888 	|
| Eureka service 	| 8761 	|
| Monitor service 	| 8010 	|
| Core service 	| 8080 	|
| Content service 	| - 	|
