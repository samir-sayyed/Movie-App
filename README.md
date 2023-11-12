# Movie Catalog App

## Overview

The **Movie Catalog App** is an Android application that allows users to explore a collection of movies. It implements various features such as a paginated movie list, detailed movie information, dark and light themes, MVVM architecture, local database storage using Room, Kotlin Flow for data and state management, configuration change handling, WorkManager for periodic data fetching, and dependency injection with Dagger Hilt.

## Features

1. **Movie List Screen**
   - Paginated display of movies fetched from the local database.
   - Data is initially loaded from the local database, and additional items are fetched from the API using pagination.
   - Each movie item displays the title, release year, and a poster image.

2. **Movie Detail Screen**
   - Detailed information about a selected movie.
   - Displayed information includes movie title, release date, overview, genres, runtime, and the full-size movie poster.

3. **Dark and Light Theme**
   - Support for both dark and light themes.
   - Users can switch between themes within the app settings.

4. **MVVM Pattern**
   - Implemented the **Model-View-ViewModel (MVVM)** architecture pattern for efficient separation of concerns and data flow.

5. **Local Database with Room**
   - Utilized **Room** for local storage of movie data on the device.
   - Implemented a Room database to cache the fetched movie list.

6. **Data and State Management using Kotlin Flow**
   - Utilized **Kotlin Flow** for handling data and state management in the app.
   - Implemented data flow using Flow to fetch and store data from APIs.

7. **Config Changes**
   - The app handles configuration changes, such as orientation changes, and retains its state appropriately.

8. **Workmanager**
   - Added a **WorkManager** as a foreground service to fetch the next page of the movie list.
   - WorkManager runs every 30 minutes to keep the movie list up-to-date.

9. **Dependency Injection**
   - Integrated **Dagger Hilt** for efficient dependency injection.

## Getting Started

To run the app locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/samir-sayyed/movie-app.git

2. **Open the project in Android Studio.**

3. **Build and run the app on an Android emulator or device.**
