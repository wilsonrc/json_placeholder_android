 # JSONPlaceholder Android APP
This application shows the post data and their details from [JSONPlaceholder](https://jsonplaceholder.typicode.com) using MAD
### Table of Contents
 - Introduction
 - Architecture
 - Getting Started
 - Libraries Used
 - Best Practices and Considerations
## Introduction
This project is a sample Android application built using Kotlin, implementing the MVVM (Model-View-ViewModel) architectural pattern. The app fetches data from a remote server and stores it locally using the Room database. The app uses Retrofit for network calls, Hilt for dependency injection, and Room for local data persistence.
### Architecture
![image](https://user-images.githubusercontent.com/11259931/230985378-b18c3140-ee91-4778-a152-a3e45336d7d2.png)


The app follows the MVVM architectural pattern with UI and Data layers. The main components of the app are:
UI layer: Responsible for rendering the UI and user interactions.
Data layer: Responsible for handling the data flow between the UI and the remote server or local database.
In addition, the app uses Repositories to handle data operations, providing a single source of truth for the application.


### Getting Started
- To run the app, follow these steps:
- Clone this repository to your local machine.
- Open the project using Android Studio.
- Connect an Android device or use an emulator.
- Build and run the app.
Language and Libraries Used

## Kotlin
Kotlin is a modern, expressive, and safe programming language fully interoperable with Java. It is used as the primary language for this project.
#### Benefits:
- Concise and expressive syntax
- Enhanced safety with nullability checks and immutability
- Extension functions for cleaner and more readable code
- Support for coroutines for efficient concurrency handling
## Retrofit
Retrofit is a type-safe HTTP client for Android and Java. It is used for making network calls in this project.
#### Benefits:
- Type-safe and easy-to-use API
- Built-in support for various serialization libraries
- Interceptors for easy handling of headers and logging
- Support for synchronous and asynchronous calls
## Hilt
The hilt is a dependency injection library for Android built on top of Dagger. It simplifies the process of providing dependencies to various components of the application.
#### Benefits:
- Simplifies the setup process for dependency injection
- Provides compile-time validation of dependencies
- Reduces boilerplate code
- Supports Android Jetpack components

## Room
Room is an SQLite object-mapping library for Android that provides an abstraction layer over SQLite and simplifies database operations.

#### Benefits:
- Compile-time checks for SQL queries
- Clean and simple API for database operations
- Support for LiveData and RxJava
- Easy integration with other Android components

## Best Practices and Considerations
While developing this project, we considered the following best practices and guidelines to ensure high-quality, maintainable, and scalable code:

- Code organization: Followed the recommended package structure for organizing code, separating components by feature and responsibility.
Performance: Used efficient data structures and algorithms to ensure optimal performance. Utilized Kotlin coroutines for efficient and non-blocking network and database operations.
- Scalability: Designed the app with future enhancements in mind, adhering to the SOLID principles to allow easy addition and modification of features.
- Error handling: Implemented proper error handling for network calls and database operations. Provided appropriate error messages to users and logged errors for debugging purposes.
- Testing: Wrote unit tests for the ViewModel and Repository layers to ensure correct behavior. Performed integration tests to verify proper interaction between components.
