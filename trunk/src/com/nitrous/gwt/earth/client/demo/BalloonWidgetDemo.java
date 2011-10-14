/*
 * Copyright 2011 Nick Kerr
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.nitrous.gwt.earth.client.api.GEHtmlDivBalloon;
import com.nitrous.gwt.earth.client.api.GELayerId;
import com.nitrous.gwt.earth.client.api.GEPlugin;
import com.nitrous.gwt.earth.client.api.GEPluginReadyListener;
import com.nitrous.gwt.earth.client.api.GEVisibility;
import com.nitrous.gwt.earth.client.api.GoogleEarthWidget;
import com.nitrous.gwt.earth.client.api.KmlAltitudeMode;
import com.nitrous.gwt.earth.client.api.KmlBalloonOpeningEvent;
import com.nitrous.gwt.earth.client.api.KmlLookAt;
import com.nitrous.gwt.earth.client.api.KmlPlacemark;
import com.nitrous.gwt.earth.client.api.KmlPoint;
import com.nitrous.gwt.earth.client.api.event.BalloonListener;

/**
 * A demo that illustrates how a GWT widget may be rendered inside of a Google Earth balloon.
 * 
 * @author nick
 * 
 */
public class BalloonWidgetDemo implements EntryPoint {
	/** Maximum number of times to poll the DOM looking for the balloon DIV */
	private static final int MAX_RETRIES = 100;
	/** Sleep interval in milliseconds between DOM polling */
	private static final int RETRY_INTERVAL = 10;
	/** Current number of remaining retries when polling the DOM for the balloon DIV */
	private int retry;

	
	private GoogleEarthWidget earth;
	private KmlPlacemark placemark;
	
	public void onModuleLoad() {
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

		Button showButton = new Button("Show a balloon!");
		showButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GEPlugin ge = earth.getGEPlugin();
				
				// Create a DIV balloon with an empty div
				// A BalloonListener is used to insert a GWT widget into this DIV when the balloon is displayed 
				GEHtmlDivBalloon balloon = ge.createHtmlDivBalloon("");
				balloon.setCloseButtonEnabled(false);
				balloon.setContentDiv("<div id='gwt-widget-container'></div>");
				balloon.setFeature(placemark); // optional
				balloon.setMaxWidth(300);
				ge.setBalloon(balloon);
			}
		});
		Button closeButton = new Button("Close the balloon!");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// hide the balloon
				earth.getGEPlugin().setBalloon(null);
			}
		});

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(showButton);
		buttons.add(closeButton);
		
		VerticalPanel topPanel = new VerticalPanel();
		topPanel.setWidth("100%");
		topPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		topPanel.add(buttons);

		DockLayoutPanel layout = new DockLayoutPanel(Unit.PX);
		layout.addNorth(topPanel, 40D);
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

		// create the placemark
		placemark = ge.createPlacemark("");

		KmlPoint point = ge.createPoint("");
		point.setLatitude(37);
		point.setLongitude(-122);
		placemark.setGeometry(point);

		// add the placemark to the earth DOM
		ge.getFeatures().appendChild(placemark);

		  // look at the placemark we created
		KmlLookAt la = ge.createLookAt("");
		la.set(37, -122,
		    0, // altitude
		    KmlAltitudeMode.ALTITUDE_RELATIVE_TO_GROUND,
		    0, // heading
		    0, // straight-down tilt
		    5000 // range (inverse of zoom)
		    );
		ge.getView().setAbstractView(la);
		
		// register the balloon event listener
		ge.addBalloonListener(new BalloonListener(){
			@Override
			public void onBalloonOpening(KmlBalloonOpeningEvent event) {
				// begin polling the DOM for the DIV with ID 'gwt-widget-container'
				beginWaitingForDiv();
			}

			@Override
			public void onBalloonClose() {
			}
		});
	}
	
	/**
	 * Begin waiting for Google Earth to insert a DIV with ID 'gwt-widget-container' in the DOM
	 */
	private void beginWaitingForDiv() {
		retry = MAX_RETRIES;
		waitForDiv();
	}
	
	/**
	 * Check the DOM for the balloon DIV and if not present sleep and then retry
	 * until we either find the DIV or meet the maximum number of retries.
	 */
	private void waitForDiv() {
		retry--;
		Element container = DOM.getElementById("gwt-widget-container");
		if (container != null) {
			// output timing to GWT debug console
			long timeTaken = (MAX_RETRIES  - (retry + 1)) * RETRY_INTERVAL;
			GWT.log("Found the DIV container after "+timeTaken+"ms");
			
			// found the DIV inside the balloon
			populateBalloon(container);
		} else {
			GWT.log("Failed to locate DIV on attempt #"+((MAX_RETRIES  - (retry + 1)) + 1));
			
			// didn't find the DIV in the DOM, so sleep and retry
			if (retry > 0) {
				Timer t = new Timer() {
					@Override
					public void run() {
						waitForDiv();
					}
				};
				t.schedule(RETRY_INTERVAL);
			}
		}
	}
		
	/**
	 * Populate the visible google earth balloon with some widgets
	 */
	private void populateBalloon(Element container) {
		// build a simple panel with a label and button to display inside the balloon.
		VerticalPanel layout = new VerticalPanel();
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.add(new HTML("This&nbsp;is&nbsp;a&nbsp;GWT&nbsp;widget"));
		Button closeButton = new Button("Close");
		layout.add(closeButton);
		closeButton.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				// close the balloon
				earth.getGEPlugin().setBalloon(null);					
			}
		});
		
		// IMPORTANT: we must add to the root document first in order for the event listeners to work
		RootPanel.get().add(layout);
		
		// Now add the widget to the DIV within the balloon.
		DOM.insertChild(container, layout.getElement(), 0);
	}

}
