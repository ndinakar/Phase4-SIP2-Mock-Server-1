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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class SocketServer {
	

	
	public static void main(String[] args) throws Exception {
	    Properties properties = new Properties();
	    InputStreamReader in = null;
	    try {
	        in = new InputStreamReader(new FileInputStream(args[0]), "UTF-8");
	        properties.load(in);
	        String nyplServerIp = properties.getProperty("ils.nypl.sip.server.url");
		     String nyplServerPost = properties.getProperty("ils.nypl.sip.server.port");

		    System.out.println("NYPL IP " + "  " + nyplServerIp + "  "+ "NYPL Host" + "  "+ nyplServerPost  );

		    SocketDaemon thread = new SocketDaemon(nyplServerIp, Integer.parseInt(nyplServerPost), new MessageHandlerDummyImpl());
		    thread.start();

	    } finally
	    { if
	    (null != in)
	    { try
	    { in.close();
	    } catch (IOException ex) {
	    	}
	    }
	    }
	    }

 

}
