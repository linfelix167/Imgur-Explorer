# Android Coding Challenge

Imgur Explorer lets you to find interesting images.

Project Design
---------
* Communicating with Imgur API using <strong>Retrofit2</strong>
* <strong>MVVM Architecture</strong>: Model, Repository, ViewModel
* Custom Loading Animation ProgressBar
* RecyclerView Pagination

Design Diagram
---------
![Diagram](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

Why Architecture Components
---------
* LiveData ensures UI matches data state
* Avoid memory leaks
* No crashes due to stopped activities
* No more manual lifecycle handling
* Data is always up to date
* Proper configuration changes

Screenshots
---------
![Imgur](https://i.imgur.com/boCs1AX.png)
![Imgur](https://i.imgur.com/hwzWksu.png)
![Imgur](https://i.imgur.com/kWYOYnh.png)

Libraries
---------
* [Retrofit](https://square.github.io/retrofit/)
* [Glide](https://bumptech.github.io/glide/)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
