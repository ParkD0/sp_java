package com.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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
		String device;
		String hostname;
		String port;
		
		public DeviceInfo(String device, String hostname, String port) {
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
