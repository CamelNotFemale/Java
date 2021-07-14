package com.company;

import java.applet.Applet;
import java.awt.*;

public class SimpleApplet extends Applet {
    public void paint(Graphics graphics) {
        graphics.drawString("Hello, world!!!", 25, 50);
    }
}
