Classlocus
==========

The project requires the following additional library projects to compile correctly:

 - Google Play Services SDK
 - Android Maps Util library

---------------------------------------------
To install the Google Play services SDK for development:

1) Launch the SDK Manager in one of the following ways:
	- In Android Studio, click SDK Manager  in the toolbar.
	- In Eclipse (with ADT), select Window > Android SDK Manager.
	- On Windows, double-click the SDK Manager.exe file at the root of the Android SDK directory.
	- On Mac or Linux, open a terminal and navigate to the tools/ directory in the Android SDK, then execute 		android sdk.

2) Install the Google Play services SDK.

Scroll to the bottom of the package list, expand Extras, select Google Play services, and install it. If you're using Android Studio, also install Google Repository (it provides the Maven repository used for Gradle builds).

The Google Play services SDK is saved in your Android SDK environment at <android-sdk>/extras/google/google_play_services/.

Note: Google Play services 4.0.30 (released November 2013) and newer versions require Android 2.3 or higher. If your app supports Android 2.2, you can continue development with the Google Play services SDK, but must instead install Google Play services for Froyo from the SDK Manager.

3) Install a compatible version of the Google APIs platform.

If you want to test your app on the emulator, expand the directory for Android 4.2.2 (API 17) or a higher version, select Google APIs, and install it. Then create a new AVD with Google APIs as the platform target.

4) Make a copy of the Google Play services library project.

Note: If you are using Android Studio, skip this step.

Copy the library project at <android-sdk>/extras/google/google_play_services/libproject/google-play-services_lib/ to the location where you maintain your Android app projects.
If you are using Eclipse, import the library project into your workspace. Click File > Import, select Android > Existing Android Code into Workspace, and browse to the copy of the library project to import it.

---------------------------------------------
To install the Android Maps Util library

1) Get the Google Maps Android API utility library

Download the android-maps-utils repository. You can do it by cloning the repo (recommended, to receive automatic updates) or downloading a zip file. If you want to customize the library, you should fork the repo.

The repository includes:
	- A demo application, in the demo directory.
	- The library of utilities, in the library directory.
	- Various files containing license, contributors, and readme information.
	- Gradle build configuration, for use with Android Studio.
	- Import the utility library into Eclipse

2) Follow these steps to import the demo and library directories from the utility library into Eclipse:

	- Choose File > Import > Android > Existing Android Code Into Workspace, and click Next.
	- Browse to the location where you saved the Google Maps Android API Utility Library, select the option to 		Copy projects into workspace, and import the demo and library directories.

3) Add the Android Support Library

Now you need to add the Android Support library to the Google Play Services SDK library project:

Right-click the Google Play Services SDK library project and choose Android Tools > Add support library.
Accept the license and install the library.
