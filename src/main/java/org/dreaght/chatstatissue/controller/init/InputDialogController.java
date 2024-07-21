package org.dreaght.chatstatissue.controller.init;

import java.util.Optional;

public interface InputDialogController {
    Optional<String> getResult();
    void setHeaderText(String text);
}
