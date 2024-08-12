package com.africa.semiclon.capStoneProject.services;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ScaleReader {
    private SerialPort serialPort;

    public interface WeightCallback {
        void onWeightRead(String weight);
    }

    public void readFromScale(String portName, WeightCallback callback) {
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.addEventListener(event -> {
                if (event.isRXCHAR()) {
                    try {
                        byte[] buffer = serialPort.readBytes();
                        String weightData = new String(buffer);
                        callback.onWeightRead(weightData);
                    } catch (SerialPortException ex) {
                        System.out.println("Error in receiving string from COM-port: " + ex);
                    }
                }
            });
        } catch (SerialPortException ex) {
            System.out.println("Error in opening port: " + ex);
        }
    }
}

