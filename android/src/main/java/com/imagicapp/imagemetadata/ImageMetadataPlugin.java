package com.imagicapp.imagemetadata;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginMethod;

import org.json.JSONArray;

import java.util.List;
import java.util.Date;


@CapacitorPlugin(name = "ImageMetadataPlugin")
public class ImageMetadataPlugin extends Plugin {
    private ImageMetadata implementation;

    @Override
    public void load() {
        implementation = new ImageMetadata(getContext());
    }

    @PluginMethod()
    public void getImagesBetweenDates(PluginCall call) {
        long validFrom = call.getLong("validFrom");
        long validTo = call.getLong("validTo");

        implementation.logCrashlyticsMessage("Fetching images between dates: validFrom=" + validFrom + ", validTo=" + validTo);

        if (validFrom == 0 || validTo == 0) {
            call.reject("Valid date range must be provided.");
            return;
        }

        try {
            List<String> imagePaths = implementation.getImagesBetweenDates(new Date(validFrom), new Date(validTo));
            implementation.logCrashlyticsMessage("Fetched " + imagePaths.size() + " images.");
            JSObject ret = new JSObject();
            ret.put("imagePaths", new JSONArray(imagePaths));
            call.resolve(ret);
        } catch (Exception e) {
            implementation.logCrashlyticsMessage("Error fetching images: " + e.getMessage());
            call.reject("Error fetching images: " + e.getMessage());
        }
    }

    @PluginMethod()
    public void getMetadata(PluginCall call) {
        String filePath = call.getString("filePath");

        if (filePath == null) {
            call.reject("File path must be provided.");
            return;
        }

        try {
            Date creationDate = implementation.getCreationDate(filePath);
            if (creationDate != null) {
                JSObject ret = new JSObject();
                ret.put("creationDate", creationDate.getTime());
                call.resolve(ret);
            } else {
                call.reject("Unable to retrieve metadata.");
            }
        } catch (Exception e) {
            call.reject("Error retrieving metadata: " + e.getMessage());
        }
    }

    @PluginMethod()
    public void logCrashlyticsMessage(PluginCall call) {
        String message = call.getString("message");

        if (message == null) {
            call.reject("Message must be provided.");
            return;
        }

        try {
            implementation.logCrashlyticsMessage(message);
            call.resolve();
        } catch (Exception e) {
            call.reject("Error logging message: " + e.getMessage());
        }
    }
}
