## Issues ✖

#### Issue 1 : Android Virtual Device

Problem : 
The Android Virtual Device screen was not clickable on my external monitor

Solution :
Move the Android Virtual Device to the laptop screen.

#### Issue 2 :  Trip.java

Problem : 
When writing the Parcelable in and out code, ran into the error that the methods writeBoolean and readBoolean is only for Android Api 30+ (Nougat)
Meanwhile our app was for Android Api 24 

Solution : 
Convert boolean to String when writing and String to boolean when reading.
