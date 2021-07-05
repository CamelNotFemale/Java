package com.company;

public class Main {

    public static void main(String[] args) {
        Device tv = new TV();
        Remote remote = new Remote(tv);
        remote.power();
        remote.volumeUp();
        tv.getInfo();

        Device radio = new Radio();
        AdvancedRemote remote2 = new AdvancedRemote(radio);
        remote2.power();
        remote2.mute();
        remote2.channelDown();
        radio.getInfo();
    }
}
