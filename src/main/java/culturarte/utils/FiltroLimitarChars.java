/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author mark
 */
public class FiltroLimitarChars extends DocumentFilter {
    private final int maxLength;

    public FiltroLimitarChars(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) {
            return;
        }

        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength + string.length();

        if (newLength <= maxLength) {
            super.insertString(fb, offset, string, attr);
        } else {
            int allowed = maxLength - currentLength;
            if (allowed > 0) {
                super.insertString(fb, offset, string.substring(0, allowed), attr);
            }
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength - length + text.length();

        if (newLength <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            int allowed = maxLength - (currentLength - length);
            if (allowed > 0) {
                super.replace(fb, offset, length, text.substring(0, allowed), attrs);
            }
        }
    }
}
