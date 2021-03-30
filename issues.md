## Issues

#### Issue 1 :  Trip.java

Problem : 
When writing the Parcelable in and out code, ran into the error that the methods writeBoolean and readBoolean is only for Android Api 30+ (Nougat)
Meanwhile our app was for Android Api 24 

Solution : 
Convert boolean to String when writing and String to boolean when reading.
