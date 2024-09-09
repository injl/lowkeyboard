package com.example.lowkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.inputmethod.InputConnection;
import android.view.KeyEvent;
import android.view.View;

public class LowkeyKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private CustomKeysView keyboardView;
    // Declare keyboard modes
    private Keyboard keyboard;

    // Track current mode
    private boolean isUppercase = false;
    private boolean isNumeric = false;

    @Override
    public View onCreateInputView() {
        // Inflate the keyboard view
        keyboardView = (CustomKeysView) getLayoutInflater().inflate(R.layout.lowkey_keyboard_view, null);

        // Load default keyboard mode
        keyboard = new Keyboard(this, R.xml.lowkey_keyboard_lowercase);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);

        // Disable key previews
        keyboardView.setPreviewEnabled(false);

        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        // Handling interaction with the currently-focused input field
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection == null) {
            return;
        }

        switch (primaryCode) {
            case 1000:
                // Handle Shift key
                toggleKeyboardMode();
                break;
            case Keyboard.KEYCODE_DELETE:
                // Handle delete key
                inputConnection.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_DONE:
                // Handle enter key
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                // Insert appropriate character
                if (isNumeric) {
                    inputConnection.commitText(String.valueOf((char) primaryCode), 1);
                } else {
                    char code = (char) primaryCode;
                    inputConnection.commitText(String.valueOf(code), 1);
                    break;
                }
        }
    }

    private void toggleKeyboardMode() {
        if (isUppercase) {
            isUppercase = false;
            isNumeric = true;
            // Switch keyboard to numeric mode
            keyboard = new Keyboard(this, R.xml.lowkey_keyboard_numeric);
        } else if (isNumeric) {
            isNumeric = false;
            isUppercase = false;
            // Switch keyboard back to lowercase mode (default)
            keyboard = new Keyboard(this, R.xml.lowkey_keyboard_lowercase);
        } else {
            isUppercase = true;
            isNumeric = false;
            // Switch keyboard to uppercase mode
            keyboard = new Keyboard(this, R.xml.lowkey_keyboard_uppercase);
        }
        keyboardView.setKeyboard(keyboard);
    }

    // Required override methods
    @Override
    public void onPress(int primaryCode) {}
    @Override
    public void onRelease(int primaryCode) {}
    @Override
    public void onText(CharSequence text) {}
    @Override
    public void swipeLeft() {}
    @Override
    public void swipeRight() {}
    @Override
    public void swipeDown() {}
    @Override
    public void swipeUp() {}
}
