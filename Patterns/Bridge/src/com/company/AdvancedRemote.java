package com.company;

public class AdvancedRemote extends Remote {

    public AdvancedRemote() {
        super();
    }
    public AdvancedRemote(Device _device) {
        super(_device);
    }

    public void mute() {
        if (device.isEnabled()) device.setVolume(0);
    }
}
