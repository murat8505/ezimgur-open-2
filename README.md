ezimgur-open-2
==============

Version 2 of the ezimgur android app.

App layer is being rewritten to use some newer features and libraries available (such as Dagger, Robospice, Android Studio, Gradle, etc...), and to have more maintainable code (first version was used to learn android). Also using a newer version of the sdk, will not have to deal with compatibility issues as much.

Goal is to add complete functionality that is available from the API (unlike the first version of the app). 

Contributions are welcome. Please reach out for API keys (though you should be able to create new keys for testing through imgur). 

Notes:
- Logging can be written anywhere. Will have proguard strip out logging for release builds. If you have to do special operation that might be expensive to gather logging info, use BuildConfig.DEBUG to determine if that work should be done. 

- Android Library Projects are being used for api and datacontract modules, even though a standard java library would be preferred. These projects depend on the android framework, and AS does not recognize the provided scope properly for dependencies in pure java libraries (https://code.google.com/p/android/issues/detail?id=69481&thanks=69481&ts=1399651923) - so using this for now. 
