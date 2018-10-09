package com.owant.lint;

import org.junit.Test;

import static org.junit.Assert.*;

public class LintImageTest {

    @Test
    public void main() {
        System.out.println("hello");
        LintImage lintImage = new LintImage("/Users/owant/MyGit/VeSync_RN/app/Module/air_purifier_131",
                "/Users/owant/MyGit/VeSync_RN/app/Module/air_purifier_131/Components/Images",
                true);
    }
}