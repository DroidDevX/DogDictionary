Preview

<a href="https://imgflip.com/gif/3aw61c"><img src="https://i.imgflip.com/3aw61c.gif" title="Preview"/></a>

Overview:
The main objective of this short experimental project is to use a Retrofit to perform REST API requests from a public web server.
Dog profiles are requested from this server and are displayed on the UI. The threshold amounts to two-hundred queries for this demo 
application

The simple UI design comprises of a search bar to perform search queries, and a view pager to display dog profile results.
Further more, a master-view layout approach was taken into consideration when designing the UI to accommodate the dog profile data.
Images on the other hand were loaded using the Picaso library

The MVVM architectural pattern together with room was followed closely in terms of implementation. Background operations such as
caching of REST API request data in SQLite were implemented using Android SDK classes such as Executor. 
