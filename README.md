# Mastering MVVM: 1.1 
## With Dependency Injection

This is an extension to the simplest implementation of MVVM for Android. The extension is the inclusion of dependency injection(DI). The project was originally created as a goal to mastering MVVM for Android. The DI extension was included as a recommendation of the Android Guide. My personal thought is that DI is a great way to declare resources in one simple part of an Android application.

Though this project is titled "SimplestMVVMWithMediaWiki", the title is only true for the very first version, tagged as SimplestMVVM_Pure on GitHub. SimplestMVVM_Pure only have three classes: MediaWikiRepository as the model, SimplestMVVMViewModel as the view model and the MainActivity as the view. 

This project have expanded such that it is no longer "Simplest". The expansion is necessary to highlight the reason for DI, specifically to reduce the "significantly complicate and duplicate" code from dependent classes needing to know the dependencies of objects those dependent classes will construct (Android Guide [Managing Dependencies](https://developer.android.com/topic/libraries/architecture/guide.html#Managing_dependencies_between_components)). Similar to the reason of reduction of complexity and duplication is that "DI separates the responsibilities of use and construction". 

The important part of the expansion include components to implement DI and another view model class (DetailViewModel). Though the RecyclerView and its component was also added, the RecyclerView does not directly contribute to the learning of MVVM and DI but help with the transition from two displaying views, the MainActivity to the DetailActivity. Readers of this project may use a ListView instead should the RecyclerView is too new to the readers. 

The components to DI were simplified from [RayWenderlich.com](https://www.raywenderlich.com), Joe Howard and Dario Coletto's tutorial [Dependency Injection in Android with Dagger 2 and Kotlin](https://www.raywenderlich.com/171327/dependency-injection-android-dagger-2). The declaration of dependencies were implemented in the AppComponent, AppModule and SimplestDIModule components. To ensure that the dependencies are available other components, the SimplestMVVMApplication was included to build/initiate the dependencies. To use the dependencies, the two dependent/target classes in the project (DetailViewModel and SimplestMVVMViewModel) call "inject" from the *daggerAppComponent* declared in the application class. Hence, the DI implementation. 

**Discussion: Did DI reduce the complication and duplication from dependent classes?**
+ The reduction in the complication is not particularly demonstrated in this project. However, the MediaWikiRepository class was changed to have a OkHttpClient instance injected rather that instantiated within itself. This reduced the complication of the MediaWikiRepository class. Also, should the OkHttpClient was more complicated to create than a mere zero parameters construction, the complexity of the repository class would have been reduced further. 
+ The reduction to the duplication of code can be seen in one of the changes to the SimplestMVVMViewModel along with the addition of the DetailViewModel. The older version of the SimplestMVVMViewModel instantiated a MediaWikiRepository. The newer version have an instance of the repository injected. The DetailViewModel also have the instance from DI injected. Without DI, both view models would need to instantiate a MediaWikiRepository within each own class and the code would be duplicated. With DI, the instance is created once and is injected to the view models. Due to MediaWikiRepository construction being so simple with zero parameters, there are more lines of code in this project than would be without DI. Should MediaWikiRepository become more complex to initiate or that the view models need to initiate more and complicated dependencies, reduction of duplication would happen. 
+ As for the separation of use and construction, this project demonstrates it very well. The MediaWikiRepository construction is no longer a concern of the view models. The two view models mere call its require function from the repository class. 

**Personal Experience Discussion**
+ From another project, I created a "DataHolder" class to hold all the data I thought my application would need for most of its components. I would initiate an instance and load all the required data into the object. The DataHolder would exist in an Application context and components with an application reference can access the data holder. At some point in the development, the DataHolder class grew overly complex and I could no longer keep track. 
+ From this simple DI example, I can restructure the DataHolder class into modules such as set in this project and RayWenderlich.com tutorial and inject specific set of data into components that need such specific set. Hence, it is my personal view that DI is great way to declare resources in one simple part of an Android application. 

**Potential Future Experimentation**
+ DI implementation to "decouple objects to the extent that no client code has to be changed simply because an object it depends on needs to be changed to a different one" (Android Guide).

Special thanks to [RayWenderlich.com](https://www.raywenderlich.com), Joe Howard and Dario Coletto for the resources to MediaWiki and a Dagger2 example. 

Also special thanks to [MediaWiki](https://www.mediawiki.org/wiki/MediaWiki) for making their API available. The document to MediaWiki can be found [here](https://www.mediawiki.org/wiki/API:Main_page)

