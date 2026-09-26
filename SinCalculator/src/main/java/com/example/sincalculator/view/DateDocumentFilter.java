package com.example.sincalculator.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DateDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass bypass, int offset, String text,
                              AttributeSet attributes) throws BadLocationException {
        replace(bypass, offset, 0, text, attributes);
    }

    @Override
    public void replace(FilterBypass bypass, int offset, int length, String text,
                        AttributeSet attributes) throws BadLocationException {
        String current = bypass.getDocument().getText(0, bypass.getDocument().getLength());
        String before = current.substring(0, offset);
        String after = current.substring(offset + length);
        String raw = (before + (text == null ? "" : text) + after)
                .replaceAll("\\D", "");

        if (raw.length() > 8) {
            raw = raw.substring(0, 8);
        }

        String formatted = format(raw);
        bypass.replace(0, bypass.getDocument().getLength(), formatted, attributes);
    }

    @Override
    public void remove(FilterBypass bypass, int offset, int length)
            throws BadLocationException {
        String current = bypass.getDocument().getText(0, bypass.getDocument().getLength());
        String raw = (current.substring(0, offset) + current.substring(offset + length))
                .replaceAll("\\D", "");
        bypass.replace(0, bypass.getDocument().getLength(), format(raw), null);
    }

    private String format(String raw) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            if (i == 2 || i == 4) {
                result.append('.');
            }
            result.append(raw.charAt(i));
        }
        return result.toString();
    }
}
