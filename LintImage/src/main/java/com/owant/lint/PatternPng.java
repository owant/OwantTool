package com.owant.lint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PatternPng implements Runnable {
    private String doc;
    private PatternPng.FindCallback callback;

    PatternPng(String doc, PatternPng.FindCallback callback) {
        this.doc = doc;
        this.callback = callback;
    }

    private void clear() {
        this.doc = null;
    }

    public void run() {
        Pattern pattern = Pattern.compile("[\"'].[^:\\s,\"']*\\.png[\"']");
        Matcher matcher = pattern.matcher(this.doc);

        while(matcher.find()) {
            String target = this.doc.substring(matcher.start(), matcher.end());
            if (this.callback != null) {
                target = target.substring(target.lastIndexOf("/") + 1, target.length() - 1);
                this.callback.onTarget(target);
            }
        }

        if (LintImage.counter.decrementAndGet() == 0) {
            this.callback.onFilter();
        }

    }

    interface FindCallback {
        void onTarget(String var1);

        void onFilter();
    }
}
