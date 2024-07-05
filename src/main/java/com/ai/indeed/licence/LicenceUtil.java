package com.ai.indeed.licence;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LicenceUtil {
    public static String getMacAddress() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            String macAddress = getMacAddress();
            System.out.println("Local MAC Address: " + macAddress);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }
}
