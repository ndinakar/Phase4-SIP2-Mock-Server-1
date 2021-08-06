/*
 * Copyright (C) 2020 Ceridwen Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.circulation.SIP.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class SocketServer {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            properties.load(in);
            String mockSipServerIp = properties.getProperty("ils.mock.sip.server.url"); //pre-defined host to run the server
            String mockSipServerPort = properties.getProperty("ils.mock.sip.server.port");

            System.out.println("ILS Mock SIP Server IP : " + mockSipServerIp);
            System.out.println("ILS Mock SIP Server Port : " + mockSipServerPort);

            SocketDaemon thread = new SocketDaemon(mockSipServerIp, Integer.parseInt(mockSipServerPort), new MessageHandlerDummyImpl());
            thread.start();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
