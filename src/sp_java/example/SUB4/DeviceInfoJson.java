package com.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.test.DeviceInfoJson.DeviceInfo.Device;


public class DeviceInfoJson {
	@SerializedName("deviceInfo")
	List<DeviceInfo> deviceInfoList;
	
	public DeviceInfoJson(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}
	
	public List<DeviceInfo> getDeviceInfoList() {
		return deviceInfoList;
	}
	
	public class DeviceInfo {
		private String type;
		private List<Device> deviceList;
		private long parallelProcessingCount;
		
		public String getType() {
			return type;
		}
		public List<Device> getDeviceList() {
			return deviceList;
		}
		public long getParallelProcessingCount() {
			return parallelProcessingCount;
		}
		
		public class Device {
			String device;
			String hostname;
			String port;
			
			public Device(String device, String hostname, String port) {
				this.device = device;
				this.hostname = hostname;
				this.port = port;
			}
			
			public String getDevice() {
				return device;
			}

			public String getHostname() {
				return hostname;
			}

			public String getPort() {
				return port;
			}
		}
	}
	
	public String getType(String deviceName) {
		for (DeviceInfo deviceInfo : deviceInfoList) {
			for (Device device : deviceInfo.getDeviceList()) {
				if (device.getDevice().contentEquals(deviceName)) {
					return deviceInfo.getType();
				}
			}
		}
		return null;
	}
	
	public Device getDevice(String devceName) {
		for(DeviceInfo deviceInfo : deviceInfoList) {
			for (Device device : deviceInfo.getDeviceList()) {
				if(device.getDevice().contentEquals(devceName)) {
					return device;
				}
			}
		}
		return null;
	}
}
