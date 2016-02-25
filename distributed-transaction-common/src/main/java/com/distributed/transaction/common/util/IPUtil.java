package com.distributed.transaction.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 * 
 * @author yubing
 *
 */
public class IPUtil {
	
	/**
     * current local IP
     */
    private static String localIp;

    /**
     * current local IP
     */
    public static String getLocalNetWorkIp() {
        if (localIp != null) {
            return localIp;
        }
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces
                        .nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()
                        || ni.isPointToPoint()) {
                    continue;
                }
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress address = addresss.nextElement();
                    if (address instanceof Inet4Address) {
                        ip = address;
                        break;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
            if (ip != null) {
                localIp = ip.getHostAddress();
            } else {
                localIp = "127.0.0.1";
            }
        } catch (Exception e) {
            localIp = "127.0.0.1";
        }
        return localIp;
    }
	
	public static void main(String[] args){
		System.out.println(getLocalNetWorkIp());
	}

}
