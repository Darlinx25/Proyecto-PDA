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
public class FiltroAlfanumerico extends DocumentFilter {
    private static final int MAX_LENGTH = 20;

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) {
            return;
        }

        String filtered = filterAlphanumeric(string);

        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength + filtered.length();

        if (newLength <= MAX_LENGTH) {
            super.insertString(fb, offset, filtered, attr);
        } else {
            int allowed = MAX_LENGTH - currentLength;
            if (allowed > 0) {
                super.insertString(fb, offset, filtered.substring(0, allowed), attr);
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

        String filtered = filterAlphanumeric(text);

        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength - length + filtered.length();

        if (newLength <= MAX_LENGTH) {
            super.replace(fb, offset, length, filtered, attrs);
        } else {
            int allowed = MAX_LENGTH - (currentLength - length);
            if (allowed > 0) {
                super.replace(fb, offset, length, filtered.substring(0, allowed), attrs);
            }
        }
    }

    private String filterAlphanumeric(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
