package de.kneitzel;

import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.UsbDevice;

import java.util.List;

public class ListUSBTests {
    @Test
    public void testListAllUSBDevices() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        // In Struktur darstellen / ermitteln
        List<UsbDevice> devices =  hal.getUsbDevices(true);
        for (UsbDevice device: devices) {
            System.out.println(device);
        }

        List<UsbDevice> allDevices = hal.getUsbDevices(false);

        for (UsbDevice device: allDevices) {
            System.out.println(device.getName() + " (" + device.getVendor() + " / " + device.getVendor() + ":" + device.getProductId() + ")");
        }
    }
}
