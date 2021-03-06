# Reddit App

## Setup

 - Build the project

* gets top post from reddit and displays different media(GIF.VIDEO,IMAGE).
* Enable user to add/remove a post to favourites and retrieve

<table>
<tr>
<td>
<img  width="200" height="400" src="./screenshort/5.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/1.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/3.png"/>
</td>
</tr>

<tr>
<td>
<img  width="200" height="400" src="./screenshort/3.png"/>
</td>
<td>
<img  width="200" height="400" src="./screenshort/4.png"/>
</td>
</table>



## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Rxjava](https://factoryhr.medium.com/understanding-java-rxjava-for-beginners-5eacb8de12ca) - RxJava is a Java VM implementation of ReactiveX a library for composing asynchronous and event-based programs by using observable sequences. The building blocks of RxJava are Observables and Subscribers.
    * [livedata](https://kotlinlang.org/docs/reference/coroutines/flow.html) - is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
        * [Jetpack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) -  Implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.

* Architecture
    * MVVM - Model View View Model

* Plugins
    * [Detekt](https://github.com/detekt/detekt) - a static code analysis tool for the Kotlin programming language.

* CI/CD
    * Github Actions
    
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/)) - a simple framework to write repeatable tests.
    * [MockK](https://github.com/mockk) - mocking library for Kotlin
    * [Truth](https://github.com/google/truth) - Truth makes your test assertions and failure messages more readable.

 
    




