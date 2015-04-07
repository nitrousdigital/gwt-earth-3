# Introduction #
The GWT-Earth-3 API is a GWT wrapper (overlay) for the Google Earth JavaScript API. It is therefore necessary to load the Google Earth JavaScript API at runtime. Release 3-0.7 of the GWT-Earth API introduced the ability to load the Google Earth API using Java code. This page describes two approaches that can be used to load the Google Earth JavaScript API.

# Details #

## 1. The static host page approach ##
Prior to release 3-0.7 of the GWT-Earth API, the recommended approach for loading the Google Earth JavaScript API was to add the following snippet of JavaScript to your host page:
```
    <!-- Load the google earth API -->
    <!-- Generated google map key for gwt-earth-visitors.appspot.com, To generate a key for a different host, visit http://code.google.com/apis/maps/signup.html -->
    <script src="http://www.google.com/jsapi?key=ABQIAAAAfdPr40ksX4gg7ApZBtLBdBS86FUsKsfkJFQEgvtxViJt1k74WxQ5hyj1HLwcktzdGIds40n8sfMZ4A"></script>    
    <script type="text/javascript">
        google.load("earth", "1");
    </script>
```

This approach might be preferable if you want to avoid having to recompile your GWT Java code when changing your API key, or maybe you want to use a JSP or other such dynamic host page to determine the key that is used to load the API.

## 2. The dynamic Java code approach ##
With the release of GWT-Earth-3-0.7, it is now possible to load the Google Earth JavaScript API library at runtime right from within your GWT Java code. The following code snippet demonstrates how you would achieve this:

```
    /** 
     * TODO: Replace EARTH_API_KEY with a key generated for your domain.
     * 
     * To generate a key for a real deployment, visit http://code.google.com/apis/maps/signup.html 
     */
    private static final String EARTH_API_KEY = "ABQIAAAAfdPr40ksX4gg7ApZBtLBdBTJY-9JfItWUGQPJaUDtRwwITcegRRUqEyZAfd8MUbdcg1_osKHEIdPMg";
    private GoogleEarthWidget earth;

    public void onModuleLoad() {
        // Load the Earth API
        GoogleEarth.loadApi(EARTH_API_KEY, new Runnable(){
                        @Override
                        public void run() {
                                // start the application
                                onApiLoaded();                          
                        }               
        });     
    }
    
    /**
     * The Google earth API has loaded, start the application
     */
    private void onApiLoaded() {
        // construct the UI widget
        earth = new GoogleEarthWidget();

        ...
        ...

```

One of the advantages to this approach is that you can dynamically load the API at runtime and can defer loading the API until it is actually needed. For example, if the user never selects an option to view the map, you may in-fact avoid loading the Google Earth JavaScript API altogether!