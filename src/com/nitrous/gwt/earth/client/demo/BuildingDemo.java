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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.nitrous.gwt.earth.client.api.GELayerId;
import com.nitrous.gwt.earth.client.api.GEPlugin;
import com.nitrous.gwt.earth.client.api.GEPluginReadyListener;
import com.nitrous.gwt.earth.client.api.GEVisibility;
import com.nitrous.gwt.earth.client.api.GoogleEarth;
import com.nitrous.gwt.earth.client.api.GoogleEarthWidget;
import com.nitrous.gwt.earth.client.api.KmlAltitudeMode;
import com.nitrous.gwt.earth.client.api.KmlFolder;
import com.nitrous.gwt.earth.client.api.KmlLookAt;
import com.nitrous.gwt.earth.client.api.KmlObject;

/**
 * A GWT implementation of the demo found <a href="http://code.google.com/apis/ajax/playground/#show/hide_photorealistic_buildings">here</a>.
 * 
 * @author nick
 * 
 */
public class BuildingDemo implements EntryPoint {
	
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

		Button showButton = new Button("Show buildings");
		showButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showBuildings();
			}
		});

		Button hideButton = new Button("Hide buildings");
		hideButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideBuildings();
			}
		});

		Button checkVisibleButton = new Button("Are Buildings Visible?");
		checkVisibleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				areBuildingsVisibleClick();
			}
		});
		
		HorizontalPanel topPanel = new HorizontalPanel();
		topPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		topPanel.add(showButton);
		topPanel.add(hideButton);
		topPanel.add(checkVisibleButton);

		DockLayoutPanel layout = new DockLayoutPanel(Unit.PX);
		layout.addNorth(topPanel, 30D);
		layout.add(earth);
		RootLayoutPanel.get().add(layout);

		// begin loading the Google Earth Plug-in
		earth.init();
	}

	/**
	 * Display content on the map
	 */
	private void loadMapContent() {
		// The GEPlugin is the core class and is a great place to start browsing
		// the API
		GEPlugin ge = earth.getGEPlugin();
		ge.getWindow().setVisibility(true);

		// add a navigation control
		ge.getNavigationControl().setVisibility(GEVisibility.VISIBILITY_AUTO);

		// add some layers
		ge.enableLayer(GELayerId.LAYER_BORDERS, true);
		ge.enableLayer(GELayerId.LAYER_ROADS, true);

		// fly to San Francisco to see some 3D buildings
		KmlLookAt la = ge.createLookAt("");
		la.set(37.79333, -122.40, 0, KmlAltitudeMode.ALTITUDE_RELATIVE_TO_GROUND, 0, 70, 1000);
		ge.getView().setAbstractView(la);

	}

	private void showBuildings() {
		final GEPlugin ge = earth.getGEPlugin();
		ge.enableLayer(GELayerId.LAYER_BUILDINGS, true);		
		// NOTE: you can use LAYER_BUILDINGS_LOW_RESOLUTION for gray buildings
		
		new Timer() {			
			@Override
			public void run() {
				 KmlLookAt la = ge.createLookAt("");
	             la.set(37.79333, -122.40, 0, KmlAltitudeMode.ALTITUDE_RELATIVE_TO_GROUND, 180, 50, 1000);
	             ge.getView().setAbstractView(la);
	        }
		}.schedule(10000);
		
	}

	private void hideBuildings() {
		GEPlugin ge = earth.getGEPlugin();
		ge.enableLayer(GELayerId.LAYER_BUILDINGS, false);
	}

	private void areBuildingsVisibleClick() {
		GEPlugin ge = earth.getGEPlugin();
		
		KmlFolder buildingLayer = ge.getLayerRoot().getLayerById(GELayerId.getBuildingLayerId(ge));
		boolean visible = getInheritedVisibility(buildingLayer);
		Window.alert(visible ? "Buildings are visible" : "Buildings are NOT visible");
	}

	private boolean getInheritedVisibility(KmlFolder layer) {
		if (!layer.getVisibility()) {
			return false;
		} else {
			KmlObject parent = layer.getParentNode();
			if (parent == null) {
				return true;
			}
			return getInheritedVisibility((KmlFolder)parent);
		}
	}
}
