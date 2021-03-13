# Weather Forecast Application

A simple weather forecast app using openweathermap API to get detailed weather data about local and favorite locations of the user.

The application was built mainly using the MVVM design pattern, and is designed to get the minimum API calls
as possible.

The applications make a new API call only when add a new location, change settings, at the beginning of a new day, the only exception for this is local position of the user it gets updated each time the user uses the app in case the user was traveling and changed his/her location.

The app supports English, Arabic & French languages.

The app supports the dark mode.

The app has a widget the can display the current location weather status and gets updated every hour.

The app has an alarm that could be set to a specified time of the day and check on maximum or minimum temperature of that day, the alarm repeats daily. The alarm only works for the local position of the user, so if the user doesn't give the app the permission to use GPS, the user won't be able to set any weather alarms.


