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


#### Issue 3 : 

Problem : 
In Edit Mode, the checkbox was always unchecked because the boolean state is saved as an integer (0 or 1) in the database and converted to a String when showed (EditText).

Solution :

Check if the string is equal to "1"
