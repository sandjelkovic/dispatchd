# dispatchd 
Gitlab CI : [![pipeline status](https://gitlab.com/sandjelkovic/dispatchd/badges/develop/pipeline.svg)](https://gitlab.com/sandjelkovic/dispatchd/commits/develop)

Travis CI : [![Build Status](https://travis-ci.org/sandjelkovic/dispatchd.svg?branch=develop)](https://travis-ci.org/sandjelkovic/dispatchd)

> Application to notify when episodes go live or aggregate those notifications in reports

> Currently in heavy development mode.

#### Purpose
Goal of this application is to provide updates for episodes airdates in the form of customisable reports.

#### Media content provider services
* Currently, only https://trakt.tv/ is being used to retrieve information about Shows, Seasons and Episodes, including airdates.
* Importing of shows to local db is also supported only from trakt.

#### Development setup 
* Checkout.
* Import maven project.
* Update credentials in property files or set up run configuration to override those properties (recommended).
* Set the database URL for profile in use (default for dev is Linux based in relation to user home).
* Set up run configuration to use "dev" profile
* Run.

#### Credentials to update
* spring.mail.host (if not gmail)
* spring.mail.username
* spring.mail.password
* trakt.appId
* trakt.appSecret

#### Importing shows
At this moment, only trakt is supported for imports. URL that needs to be provided to the importer endpoint is in form of https://trakt.tv/shows/star-trek-the-next-generation

### Default ports
| Service 	| Port
|----------	|-----:	|
| Configuration service 	| 8888 	|
| Core service 	| 8080 	|
| Content service 	| 8090 	|
| Monitor service 	| 8010 	|
