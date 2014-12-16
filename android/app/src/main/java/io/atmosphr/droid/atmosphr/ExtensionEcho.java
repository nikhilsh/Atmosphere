package io.atmosphr.droid.atmosphr;

import org.xwalk.core.XWalkExtension;

public class ExtensionEcho extends XWalkExtension {
    private static String name = "echo";

    private static String jsapi = "var echoListener = null;" +
            "extension.setMessageListener(function(msg) {" +
            "  if (echoListener instanceof Function) {" +
            "    echoListener(msg);" + "  };" + "});" +
            "exports.echo = function (msg, callback) {" +
            "  echoListener = callback;" + "  extension.postMessage(msg);" +
            "};" + "exports.echoSync = function (msg) {" +
            "  return extension.internal.sendSyncMessage(msg);" + "};";

    public ExtensionEcho() {
        super(name, jsapi);
    }

    @Override
    public void onMessage(int instanceID, String message) {
        postMessage(instanceID, "From java: " + message);
    }

    @Override
    public String onSyncMessage(int instanceID, String message) {
        return "From java sync: " + message;
    }

}
