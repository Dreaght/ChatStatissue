package org.dreaght.chatstatissue;

import org.dreaght.chatstatissue.listener.SettingsKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

class GlobalKeyListenerInitializer {

    public static void initializeGlobalKeyListener(ChatSIApplication application) {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(new SettingsKeyListener(application));
    }

    public static void unregisterGlobalKeyListener() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }
}
