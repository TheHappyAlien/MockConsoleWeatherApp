package com.company;

import CSI.CSIThread;

import CSI.Station;
import K.U.P.A.User;
import K.U.P.A.UserLoginScreen;
import Sensors_TheOverkillPackage.*;
import Sensors_TheOverkillPackage.UsabilityClasses.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        CSIThread csiThread = new CSIThread();

        csiThread.setup();
        csiThread.start();

        User.setCsiThread(csiThread);
        UserLoginScreen.setCSIThread(csiThread);

        UserLoginScreen.goToLoginScreen();

        csiThread.join();
    }
}
