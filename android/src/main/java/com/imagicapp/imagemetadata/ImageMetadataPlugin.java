package com.imagicapp.imagemetadata;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.PluginMethod;

@CapacitorPlugin(name = "ImageMetadataPlugin")
public class ImageMetadataPlugin extends Plugin {
    private ImageMetadata implementation;

    @Override
    public void load() {
        implementation = new ImageMetadata(getContext());
    }

    @PluginMethod
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
}
