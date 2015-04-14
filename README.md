# gwt-earth-3
### Introduction
The goal of this API is to provide a comprehensive [GWT](https://developers.google.com/web-toolkit/) wrapper to expose all of the Google Earth functionality that is provided by the official Google Javascript API version 3.0 found [here](https://developers.google.com/earth/documentation/reference/).

This API provides a [GWT](https://developers.google.com/web-toolkit/) widget and overlay types to expose all documented classes and methods of the [Google Earth API version 3.0](https://developers.google.com/earth/documentation/reference/)

**Important Note:** The Google Earth API has been [deprecated](https://developers.google.com/earth/documentation/reference/) and will be shut down on December 12th, 2015.

### Demos
* [Demo Browser](http://gwt-earth-demos.appspot.com/#HelloWorldDemo) ([Source](https://github.com/nitrousdigital/gwt-earth-3/tree/master/src/com/nitrous/gwt/earth/client/demo))
* [Hello World demo](http://gwt-earth.appspot.com/) ([Source](https://github.com/nitrousdigital/gwt-earth-3-test/blob/master/src/com/nitrous/gwt/earthtest/client/GwtEarthTest.java))
* [SmartGWT Shim demo](http://smart-gwt-earth-shim-demo.appspot.com/) ([Source](https://github.com/nitrousdigital/smart-gwt-earth-shim-demo))

### Updates
|Date|Update|
|----|------|
|09/05/2012	| Released [version 3-1.0](https://github.com/nitrousdigital/gwt-earth-3/releases/download/1.0/GwtEarth3-1.0.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|05/11/2012	| Released [version 3-0.9](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.9/GwtEarth3-0.9.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). | 
|01/30/2012	| Released [version 3-0.8](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.8/GwtEarth3-0.8.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|11/15/2011	| Released [version 3-0.7](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.7/GwtEarth3-0.7.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|09/13/2011	| Released [version 3-0.6](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.6/GwtEarth3-0.6.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|09/05/2011	| Released [version 3-0.5](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.5/GwtEarth3-0.5.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|08/30/2011	| Added [Demo Browser](http://gwt-earth-demos.appspot.com/#HelloWorldDemo). Released [version 3-0.4](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.4/GwtEarth3-0.4.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|08/25/2011	| Implemented event listener support. Created additional demos which can be found [here](https://github.com/nitrousdigital/gwt-earth-3/tree/master/src/com/nitrous/gwt/earth/client/demo). Released [version 3-0.3](http://code.google.com/p/gwt-earth-3/downloads/detail?name=GwtEarth3-0.3.jar). Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|08/21/2011	| Fixed [issue 3](https://github.com/nitrousdigital/gwt-earth-3/issues/3). Added [JavaDoc download](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.2/GwtEarth3-0.2-JavaDoc.zip). [Version 3-0.2](https://github.com/nitrousdigital/gwt-earth-3/releases/download/0.2/GwtEarth3-0.2.jar) released. Release notes [here](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/ReleaseNotes.md). |
|08/17/2011	| Added more sample code. |
|07/30/2011	| Added Google Analytics demo. |
|06/11/2011	| Completed initial wrapping of all classes. |

### Getting Started
See [GettingStarted](https://github.com/nitrousdigital/gwt-earth-3/blob/master/doc/GettingStarted.md) to get started now!

### Discussion Group
[http://groups.google.com/group/gwt-earth](http://groups.google.com/group/gwt-earth)

### Known Issues
Currently there appears to be an issue with the GWT plugin for Chrome that prevents gwt-earth projects from being debugged in DevMode in the Google Chrome browser. If you are experiencing this problem, please vote for the issues reported here:

* [https://code.google.com/p/chromium/issues/detail?id=137664](https://code.google.com/p/chromium/issues/detail?id=137664)
* [https://code.google.com/p/google-web-toolkit/issues/detail?id=7373](https://code.google.com/p/google-web-toolkit/issues/detail?id=7373)

The workaround is to use another browser such as FireFox for your DevMode debugging sessions of gwt-earth applications.
