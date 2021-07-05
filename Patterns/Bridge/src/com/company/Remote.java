package com.company;

public class Remote {
    Device device;

    public Remote() {
        device = null;
    }
    public Remote(Device _device) {
        device = _device;
    }

    public void power() {
        if (device != null)
            if (device.isEnabled())
                device.disable();
            else
                device.enable();
    }
    public void volumeDown() {
        if (device != null)
            if (device.isEnabled())
                device.setVolume(device.getVolume() - 10);
    }
    public void volumeUp() {
        if (device != null)
            if (device.isEnabled())
                device.setVolume(device.getVolume() + 10);
    }
    public void channelDown() {
        if (device != null)
            if (device.isEnabled())
                if (device.getChannel() == 1)
                    device.setChannel(99);
                else device.setChannel(device.getChannel() - 1);
    }
    public void channelUp() {
        if (device != null)
            if (device.isEnabled())
                if (device.getChannel() == 99)
                    device.setChannel(1);
                else device.setChannel(device.getChannel() + 1);
    }
}
