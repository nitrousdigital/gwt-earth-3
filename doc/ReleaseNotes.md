### 3.1.0
##### Released: September 05, 2012
* Fix for [Issue 12](https://github.com/nitrousdigital/gwt-earth-3/issues/12) - Can not display 2 GoogleEarthWidgets

This release contains a fix for the critical issue where it was not possible to display 2 or more GoogleEarthWidget instances without chaining their onInitSuccess() methods.

* The ID of the DIV that contains the GE plugin is now exposed via the new method: GoogleEarthWidget::getContainerId();
* GoogleEarthWidget::onInitSuccess() and GoogleEarthWidget::onInitFailure() are now exposed as protected methods.
* GoogleEarthWidget::pluginReadyListeners is now exposed as a protected member variable.

### 3.0.9
##### Released: May 11, 2012
* Minor updates eliminating the need to provide an API key when loading the Google Earth API:
  * Updated com.nitrousgwt.earth.client.api.GoogleEarth
    * deprecated loadApi(key, Runnable)
    * added loadApi(Runnable)
  * Updated all sample code to use new GoogleEarth::loadApi(Runnable) method and removed API keys.
  * Minor JavaDoc fixes.

### 3.0.8
##### Released: Jan 30, 2012
* Adding support for the new Google Earth JavaScript API 1.009 functions:
  * Added GETourPlayer.getLoop and GETourPlayer.setLoop
  * Added GETourPlayer.getCurrentSpeed and GETourPlayer.setCurrentSpeed.
  * Added GETourPlayer.getControl.
  * Added GETourPlayerControl.getVisiblity and GETourPlayerControl.setVisiblity.
* Updated JavaDoc to exclude demo classes.
* Added KML tour demos

### 3.0.7
##### Released: Nov 15, 2011
* Added Google Ajax API Loader ( [Issue 8](https://github.com/nitrousdigital/gwt-earth-3/issues/8) )
* Updated all demos to use Google Ajax API Loader
* Fixed JSNI event callbacks for MouseClickListener in GEEventEmitter
* Removed GEHtmlDivBalloon::setContent(Widget) as this method was experimental and not very useful.
* Added demos: Placemark Click Demo, Drawing Demo, Balloon widget demo & Shim demo.

### 3.0.6
##### Released: Sep 13, 2011
* Fixes for [Issue 5](https://github.com/nitrousdigital/gwt-earth-3/issues/5) : Return type of KmlMouseEvent.getDidHitGlobe() incorrect (GWT Developer Plugin + Internet Explorer).
* Modified GoogleEarthWidget to be a Composite Widget.

### 3.0.5
##### Released: Sep 5, 2011
* Fixed access modifier on GoogleEarth::addEventListener methods.
* Fixed return type of KmlEvent::getTarget()
* Added getType() to GEAbstractBalloon
* Updated JavaDoc for each demo to include a URL to the original JavaScript demo.
* Moved getType() from KmlObject to to GESchemaObject
* Added getTimeStamp() to KmlEvent
* Added many demos

### 3.0.4
##### Released: Aug 30, 2011
**IMPORTANT NOTE:** ge.getWindow().setVisibility(true) must now be called manually after the GEPlugin is loaded. Previously, the GoogleEarthWidget was calling this automatically but that code has now been removed.

* Added GoogleEarth class that provides the google.earth namespace functionality.
* Added batch execution support.
* Added demos
* Fixed method access modifiers in KmlExtrudableGeometry
* Fixed some JavaDoc links
* Modified GoogleEarthWidget to use the new GoogleEarth class for google.earth namespace function calls such as createInstance()

### 3.0.3
##### Released: Aug 25, 2011
* This version adds support for all Event Handling offered by Google Earth 3.


### 3.0.2
##### Released: Aug 21, 2011
* [Issue 3](https://github.com/nitrousdigital/gwt-earth-3/issues/3) :GESchemaObjectContainer::replaceChild(oldChild, newChild) is broken
* Added more sample code.

### 3.0.1
##### Released: Jun 11, 2011
* This is the very first build that you can use to get started with the code. The majority of the code is currently untested.
