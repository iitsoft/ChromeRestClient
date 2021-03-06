/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.rest.client.ui.desktop;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.rest.client.log.Log;
import org.rest.client.ui.ErrorDialogView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

/**
 * A simple glass panel popup that terminates interaction with the application.
 */
public class ErrorDialogViewImpl implements ErrorDialogView {
	interface Binder extends UiBinder<DialogBox, ErrorDialogViewImpl> {
	}

	@UiField DialogBox errorDialog;
	@UiField Label errorMessage;
	@UiField Label errorTitle;

	public ErrorDialogViewImpl() {
		GWT.<Binder> create(Binder.class).createAndBindUi(this);
	}

	@Override
	public Handler getHandler() {
		return new Handler() {
			{
				setLevel(Level.SEVERE);
			}

			@Override
			public void close() {
			}

			@Override
			public void flush() {
			}

			@Override
			public void publish(LogRecord record) {
				
				String name = record.getLoggerName();
				if(name != null && name.equals("request")){
					errorTitle.setText("An error occured during the request");
				}
				
				Log.error("An error occured.", record.getThrown());
				errorMessage.setText(record.getMessage());
				errorDialog.getElement().getStyle().setZIndex(10000);
				errorDialog.center();
				errorDialog.show();
			}
		};
	}
	
	
	
	
	@UiHandler("dismiss")
	void onDismiss(ClickEvent event) {
		errorDialog.hide();
	}

	@UiHandler("reload")
	void onReload(ClickEvent event) {
		Window.Location.reload();
	}

	@Override
	public void setTitle(String message) {
		errorTitle.setText(message);
	}
}