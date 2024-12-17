package uk.ac.nott.cs.comp2013.froggergame.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface KeyEventHandlerProvider {
    EventHandler<? super KeyEvent> getKeyPressedHandler();
    EventHandler<? super KeyEvent> getKeyReleasedHandler();
}

