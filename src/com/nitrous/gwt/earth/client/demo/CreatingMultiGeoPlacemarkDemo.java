/*
 * Copyright 2012 Nick Kerr
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.nitrous.gwt.earth.client.demo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.nitrous.gwt.earth.client.api.GEGeometryContainer;
import com.nitrous.gwt.earth.client.api.GELayerId;
import com.nitrous.gwt.earth.client.api.GEPlugin;
import com.nitrous.gwt.earth.client.api.GEPluginReadyListener;
import com.nitrous.gwt.earth.client.api.GEVisibility;
import com.nitrous.gwt.earth.client.api.GoogleEarth;
import com.nitrous.gwt.earth.client.api.GoogleEarthWidget;
import com.nitrous.gwt.earth.client.api.KmlAltitudeMode;
import com.nitrous.gwt.earth.client.api.KmlLinearRing;
import com.nitrous.gwt.earth.client.api.KmlLookAt;
import com.nitrous.gwt.earth.client.api.KmlMultiGeometry;
import com.nitrous.gwt.earth.client.api.KmlPlacemark;

/**
 * A GWT implementation of the demo found <a href="http://code.google.com/apis/ajax/playground/#creating_multi-geometry_placemarks">here</a>.
 */
public class CreatingMultiGeoPlacemarkDemo implements EntryPoint {
	
    private GoogleEarthWidget earth;
	
    public void onModuleLoad() {
    	// Load the Earth API
    	GoogleEarth.loadApi(new Runnable(){
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

        // register a listener to be notified when the earth plug-in has loaded
        earth.addPluginReadyListener(new GEPluginReadyListener() {
            public void pluginReady(GEPlugin ge) {
                // show map content once the plugin has loaded
                loadMapContent();
            }

            public void pluginInitFailure() {
                // failure!
                Window.alert("Failed to initialize Google Earth Plug-in");
            }
        });

        HorizontalPanel header = new HorizontalPanel();
        Button createButton = new Button("Create a Multi-Geometry Placemark!");
        createButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                createMultiGeometry();
            }
        });
        header.add(createButton);
        
        DockLayoutPanel layout = new DockLayoutPanel(Unit.PX);
        layout.addNorth(header, 30);
        layout.add(earth);
        RootLayoutPanel.get().add(layout);

        // begin loading the Google Earth Plug-in
        earth.init();
    }

    /**
     * Display content on the map
     */
    private void loadMapContent() {
        // The GEPlugin is the core class and is a great place to start browsing the API
        GEPlugin ge = earth.getGEPlugin();
        ge.getWindow().setVisibility(true);
        
        // add a navigation control
        ge.getNavigationControl().setVisibility(GEVisibility.VISIBILITY_AUTO);
  
        // add some layers
        ge.enableLayer(GELayerId.LAYER_BORDERS, true);
        ge.enableLayer(GELayerId.LAYER_ROADS, true);
        
        // Square outer boundary.
        KmlLookAt lookAt = ge.getView().copyAsLookAt(KmlAltitudeMode.ALTITUDE_RELATIVE_TO_GROUND);
        lookAt.setRange(400000);
        ge.getView().setAbstractView(lookAt);
        
        createMultiGeometry();
    }
    
	private void createMultiGeometry() {
	    GEPlugin ge = earth.getGEPlugin();

	    KmlPlacemark multGeoPlacemark = ge.createPlacemark("");
	    KmlMultiGeometry multiGeo = ge.createMultiGeometry("");
	    multGeoPlacemark.setGeometry(multiGeo);
	    
	    GEGeometryContainer geoms = multiGeo.getGeometries();
	    geoms.appendChild(makeCircle(.25, 0, 0));
	    geoms.appendChild(makeCircle(.55, 0, 0));
	    geoms.appendChild(makeCircle(.85, 0, 0));
	    geoms.appendChild(makeCircle(1.15, 0, 0));
	    ge.getFeatures().appendChild(multGeoPlacemark);
	}	
	
	private KmlLinearRing makeCircle(double radius, double x, double y) {
        GEPlugin ge = earth.getGEPlugin();
        
	    KmlLookAt center = ge.getView().copyAsLookAt(KmlAltitudeMode.ALTITUDE_RELATIVE_TO_GROUND);
	    KmlLinearRing ring = ge.createLinearRing("");
	    double steps = 25;
	    double pi2 = Math.PI * 2;
	    for (double i = 0; i < steps; i++) {
	        double lat = center.getLatitude() + x + radius * Math.cos(i / steps * pi2);
	        double lng = center.getLongitude() + y + radius * Math.sin(i / steps * pi2);
	        ring.getCoordinates().pushLatLngAlt(lat, lng, 0);
	    }
	    return ring;
	}
}
