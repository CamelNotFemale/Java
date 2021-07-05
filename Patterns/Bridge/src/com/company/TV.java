package com.company;

import java.util.Date;

public class TV implements Device {
    /** Уникальный номер нового устройства (дата создания) **/
    private final long NUM;
    /** Включено/выключено **/
    private boolean enabled;
    /** Громкость в процентах **/
    private int volume;
    /** Номер канала **/
    private int channel;

    public TV() {
        Date date = new Date();
        NUM = date.getTime();
        enabled = false;
        volume = 25;
        channel = 1;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        if (percent >= 0 && percent <= 100)
            volume = percent;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int _channel) {
        if (channel > 0)
            channel = _channel;
    }

    public void getInfo() {
        System.out.println("TV №" + NUM + " info:");
        if (isEnabled()) System.out.println("  Is enabled");
        else System.out.println("  Is disabled");
        System.out.println("  Volume: " + volume + "%");
        System.out.println("  Channel: " + channel);
    }
}
