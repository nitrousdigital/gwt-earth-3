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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
import com.nitrous.gwt.earth.client.api.KmlEvent;
import com.nitrous.gwt.earth.client.api.KmlLookAt;
import com.nitrous.gwt.earth.client.api.KmlPlacemark;
import com.nitrous.gwt.earth.client.api.KmlPoint;
import com.nitrous.gwt.earth.client.api.event.KmlEventListener;

/**
 * A GWT implementation of the demo found <a href="http://code.google.com/apis/ajax/playground/#toggling_event_listeners">here</a>.
 * 
 * @author nick
 * 
 */
public class ToggleEventListenerDemo implements EntryPoint {

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

		Button enableButton = new Button("Enable Click Listener");
		enableButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				enableListener();
			}
		});
		Button disableButton = new Button("Disable Click Listener");
		disableButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				disableListener();
			}
		});

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(enableButton);
		buttons.add(disableButton);

		HorizontalPanel topPanel = new HorizontalPanel();
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
	}

	private HandlerRegistration registration = null;
	private void enableListener() {
		if (registration == null) {
			registration = GoogleEarth.addEventListener(placemark, "click", new KmlEventListener() {
				@Override
				public void onEvent(KmlEvent event) {
					 // Defer execution to prevent deadlock in some browsers
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
						    Window.alert("You clicked the placemark!");
						}
					});
				}
			});
		}
	}
	
	private void disableListener() {
		if (registration != null) {
			registration.removeHandler();
			registration = null;
		}
	}
}
