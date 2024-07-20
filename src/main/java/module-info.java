module org.dreaght.chatstatissue {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jnativehook;
    requires java.logging;
    requires java.desktop;
    requires Java.WebSocket;

    opens org.dreaght.chatstatissue to javafx.fxml;
    exports org.dreaght.chatstatissue;
    exports org.dreaght.chatstatissue.handler;
    opens org.dreaght.chatstatissue.handler to javafx.fxml;
    exports org.dreaght.chatstatissue.chat;
    opens org.dreaght.chatstatissue.chat to javafx.fxml;
    exports org.dreaght.chatstatissue.controller;
    opens org.dreaght.chatstatissue.controller to javafx.fxml;
    exports org.dreaght.chatstatissue.listener;
    opens org.dreaght.chatstatissue.listener to javafx.fxml;
}