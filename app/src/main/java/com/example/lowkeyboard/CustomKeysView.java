/*
DESCRIPTION:
This class extends the KeyboardView class to create
a custom look-and-feel for each key.
*/

package com.example.lowkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.os.Vibrator;

import java.util.List;

public class CustomKeysView extends KeyboardView {

    private Vibrator vibrator;

    public CustomKeysView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Initialize vibrator for haptic feedback
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get keys
        List<Keyboard.Key> keys = getKeyboard().getKeys();

        Paint dotPaint = new Paint();
        dotPaint.setStyle(Paint.Style.FILL);

        // Set dot size
        float dotRadius = 6;

        // Loop through each key
        for (Keyboard.Key key : keys) {
            // Draw tactile dots for ambiguous two-letter grouped keys
            if (key.label != null && (key.label.equals("0  ␣"))) {

                // Set dot spacing
                float dotSpacing = key.width / 7;

                // Arrange dots in vertical center above key label
                float startX = key.x + (key.width / 2f) - (dotSpacing * 0.5f);
                float dotY = key.y + (key.height * 0.2f);

                // First dot (active)
                dotPaint.setColor(Color.WHITE);
                canvas.drawCircle(startX, dotY, dotRadius, dotPaint);

                // Second dot (disabled)
                dotPaint.setColor(Color.GRAY);
                canvas.drawCircle(startX + dotSpacing, dotY, dotRadius, dotPaint);

            } else if (key.label != null && (key.label.equals("abc") ||
                                             key.label.equals("def") ||
                                             key.label.equals("ghi") ||
                                             key.label.equals("jkl") ||
                                             key.label.equals("mno") ||
                                             key.label.equals("tuv") ||
                                             key.label.equals("ABC") ||
                                             key.label.equals("DEF") ||
                                             key.label.equals("GHI") ||
                                             key.label.equals("JKL") ||
                                             key.label.equals("MNO") ||
                                             key.label.equals("TUV"))) {
                // Draw tactile dots for ambiguous three-letter grouped keys

                // Set dot spacing
                float dotSpacing = key.width / 7;

                // Arrange dots in vertical center above key label
                float startX = key.x + (key.width / 2f) - (dotSpacing);
                float dotY = key.y + (key.height * 0.2f);

                // First dot (active)
                dotPaint.setColor(Color.WHITE);
                canvas.drawCircle(startX, dotY, dotRadius, dotPaint);

                // Second and third dots (disabled)
                dotPaint.setColor(Color.GRAY);
                canvas.drawCircle(startX + dotSpacing, dotY, dotRadius, dotPaint);
                canvas.drawCircle(startX + dotSpacing * 2, dotY, dotRadius, dotPaint);

            } else if (key.label.equals("pqrs") ||
                       key.label.equals("wxyz") ||
                       key.label.equals("PQRS") ||
                       key.label.equals("WXYZ")) {
                // Draw tactile dots for ambiguous four-letter grouped keys

                // Set dot spacing
                float dotSpacing = key.width / 7;

                // Arrange dots in vertical center above key label
                float startX = key.x + (key.width / 2f) - (dotSpacing * 1.5f);
                float dotY = key.y + (key.height * 0.2f);

                // First dot (active)
                dotPaint.setColor(Color.WHITE);
                canvas.drawCircle(startX, dotY, dotRadius, dotPaint);

                // Second, third and fourth dots (disabled)
                dotPaint.setColor(Color.GRAY);
                canvas.drawCircle(startX + dotSpacing, dotY, dotRadius, dotPaint);
                canvas.drawCircle(startX + dotSpacing * 2, dotY, dotRadius, dotPaint);
                canvas.drawCircle(startX + dotSpacing * 3, dotY, dotRadius, dotPaint);

            }
        }
    }
    @Override
    public boolean onLongPress(Keyboard.Key key) {
        if (key.popupCharacters != null) {
            // Debug line
            Log.d("CustomKeysView", "Long press registered for key: " + key.label);

            // Trigger haptic feedback
            if (vibrator != null) {
                vibrator.vibrate(50); // Vibration duration
            }

            // Show popup characters
            return super.onLongPress(key);
        }
        return false;
    }

}

