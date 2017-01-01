# dispatchd
Application to notify when episodes go live or aggregate those notifications in reports

Currently in heavy development mode.

##Purpose
Goal of this application is to provide updates for episodes airdates in the form of customisable reports.

##Media content provider services
* Currently, only https://trakt.tv/ is being used to retrieve information about Shows, Seasons and Episodes, including airdates.
* Importing of shows to local db is also supported only from trakt.

##Setup
* Checkout.
* Import maven project.
* Create missing property files by copying the example ones such as application-mailcredentials.properties-example and filling them out with information.
* Set the database URL for profile in use (default for dev is Linux based in relation to user home).
* Set Spring profile to use in run configuration (recomended: dev)
* Run.

##Importing shows
At this moment, only trakt is supported for imports. URL that needs to be provided to the importer endpoint is in form of https://trakt.tv/shows/star-trek-the-next-generation
