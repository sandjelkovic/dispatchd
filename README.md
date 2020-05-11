# dispatchd 
Gitlab CI : [![pipeline status](https://gitlab.com/sandjelkovic/dispatchd/badges/master/pipeline.svg)](https://gitlab.com/sandjelkovic/dispatchd/commits/master)

 Application to notify when episodes go live or aggregate those notifications in reports

#### Purpose
Functional goal of this application is to provide updates for episodes air dates in the form of user customisable reports.

#### State
The whole application is being migrated to a distributed architecture. While the core-service application might work for some use cases, the goal is to split the core-service.

#### Media content provider services
* Currently, only https://trakt.tv/ is being used to retrieve information about Shows, Seasons and Episodes, including air dates.
* Importing of shows to local database is also supported only from trakt.

#### Importing shows
At this moment, only trakt is supported for imports. URL that needs to be provided to the importer endpoint is in form of `https://trakt.tv/shows/star-trek-the-next-generation`

### Default ports
| Service 	| Port
|----------	|-----:	|
| Configuration service 	| 8888 	|
| Eureka service 	| 8761 	|
| Monitor (SBA) service 	| 8010 	|
| Content service 	| - 	|
| Report service 	| - 	|
Port `-` is dynamics port and can be discovered from Eureka instance. Those services also support horizontal scaling.
