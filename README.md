# AntiTheftBoomerang

Android Application (made as a minor project in 3rd year at Jaypee Insitite of Information Technology)

Software required: Android Studio

<b>Steps to setup:</b> 
1. Clone the repository
2. Place the folder in your Projects folder
3. Open it in android studio 
4. Might have to change the package names according to the new setup


Authentication: <b>Google Firebase</b>

<b>Features: </b>

1.) <b>Lockscreen Guard</b>: Captures the image of the user using the front camera, if he/she attempts to unlock the phone 'x' number of times. There must be consecutive x failed attempts at the pattern or passcode security to trigger this. This 'x' can be set in the application settings itself. This lags sometimes and is showing issues in newer android versions. 

2.) <b>Message Control</b>: Option to control few basic settings of the device via messages: Set device from SILENT to RINGER mode, Raise a loud alarm, or even get the location of the phone back via sms on the preconfigured number.

3.) <b>Signal Flare</b>: Send the battery levels of the phone when the battery is at 'x'%. This crtical percentage of the battery 'x' can be between 5% to 15% and can be configured from the application settings.
