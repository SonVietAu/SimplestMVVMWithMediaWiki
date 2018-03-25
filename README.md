# Mastering MVVM: 1.2 
## Room Addition

Version 1.2 extended the implementation of MVVM With Dependency Injection to include Room. Having been using SQLite as a source of persistence data, Room is an excellent upgrade with its minimal boilerplate code and compile time validation against the schema. 

**Implementation**
+ Instruction for adding the Room libraries can be found at [Adding Components to your Project](https://developer.android.com/topic/libraries/architecture/adding-components.html). For this particular increment to 1.2, *implementation "android.arch.persistence.room:runtime:1.0.0"* and *kapt "android.arch.persistence.room:compiler:1.0.0"* with *kapt* replacing *annotationProcessor* so that the annotation can be use in this Kotlin project. 
+ The entities, dao and *RoomDatabase* extended class can be found in the "room" package. For the instruction to building these components, see Android App Architecture: [Persisting Data](https://developer.android.com/topic/libraries/architecture/guide.html#Persisting_data). 
+ To initiate Room for runtime, *Room.databaseBuilder* function is called from this application *onCreate* function. Storing and retrieving data from the Room database can be found in the *SimplestMVVMViewModel* class. The calls to store or retrieve data are enclosed within a *Thread* object to ensure the calls are not run on the Main UI thread. Whether there are better solutions, the enclosures will suffice for this project's aim of a simple integration of Room.  


**Discussion**
+ Minimal Boilerplate Code: With Room annotation, it is great to construct a data class as norm and then have Room creating the actual database structures. On the query side, having "Room matches the :minAge bind parameter with the minAge method parameter" makes it easier to read SQl queries. 
+ Schema Compile Time Validation: Though this project is not very extensive in regards to SQL statements, validation can be tested by introducing errors into valid statements. With the test of a few introduced errors, schema compile time validation is a great tool to have on larger and more complex database implementations. 

**Future Experimentation**
+ Room use of LiveData to allow "observing changes to the database data" and the handling of "common issues such as accessing storage on the main thread" will be included in future studies. 

