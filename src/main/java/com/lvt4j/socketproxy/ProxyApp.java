package com.lvt4j.socketproxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.common.net.HostAndPort;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author LV on 2022年3月11日
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class ProxyApp {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApp.class, args);
    }
    
    
    public static HostAndPort validHostPort(String hostAndPort) {
        HostAndPort hp;
        try{
            if(StringUtils.isBlank(hostAndPort)) return null;
            hp = HostAndPort.fromString(hostAndPort);
            Validate.isTrue(hp.hasPort(), "缺少端口");
            return hp;
        }catch(Exception e){
            log.error("非法的地址:{}", hostAndPort, e);
            return null;
        }
    }
    public static String format(SocketAddress addr) {
        return StringUtils.strip(addr.toString(), "/");
    }
    public static String port(SocketAddress addr) {
        if(!(addr instanceof InetSocketAddress)) return "unknown";
        return String.valueOf(((InetSocketAddress)addr).getPort());
    }
    public static String format(InetAddress addr) {
        return StringUtils.strip(addr.toString(), "/");
    }
    
    public static void close(Channel channel){
        if(channel==null) return;
        try{
            channel.close();
        }catch(Exception ig){}
    }
    
    public static interface IOExceptionConsumer<T> {
        void accept(T t) throws IOException;
    }
    public static interface IOExceptionRunnable {
        void run() throws IOException;
    }
}