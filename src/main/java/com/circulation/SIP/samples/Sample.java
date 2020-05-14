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
package com.circulation.SIP.samples;

import java.util.Date;

import com.circulation.SIP.exceptions.ChecksumError;
import com.circulation.SIP.exceptions.InvalidFieldLength;
import com.circulation.SIP.exceptions.MandatoryFieldOmitted;
import com.circulation.SIP.exceptions.MessageNotUnderstood;
import com.circulation.SIP.exceptions.RetriesExceeded;
import com.circulation.SIP.exceptions.SequenceError;
import com.circulation.SIP.netty.server.SIPDaemon;
import com.circulation.SIP.samples.netty.DummyDriverFactory;
import com.circulation.SIP.transport.SSLSocketConnection;
import com.circulation.SIP.transport.SocketConnection;
import com.circulation.SIP.types.enumerations.ProtocolVersion;
import com.circulation.SIP.messages.*;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sample {
  
  private static final boolean SSL = false;
  private static SelfSignedCertificate ssc;
  
  public static void main(String[] args) {
    try {
      System.setProperty("com.ceridwen.circulation.SIP.charset", "ISO8859_1");

      SIPDaemon server;

      // Run netty server
      if (SSL) {
        ssc = new SelfSignedCertificate();
        server = new SIPDaemon("Sample", "192.168.55.189", 12345, ssc.certificate(), ssc.privateKey(), new DummyDriverFactory(), true);
      } else {
        server = new SIPDaemon("Sample", "192.168.55.189", 12345, new DummyDriverFactory(), true);
      }
      server.start();

      // Do sample checkout
      Sample.getItem();

      // Shut down netty server
      server.stop();
    } catch (Exception ex) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void getItem() {
    /**
     * Now try basic client commands
     */
    SocketConnection connection;

    if (SSL) {
      connection = new SSLSocketConnection();
      ((SSLSocketConnection) connection).setServerCertificateCA(ssc.certificate());
    } else {
      connection = new SocketConnection();
    }
    connection.setHost("192.168.55.189");
    connection.setPort(12345);
    connection.setConnectionTimeout(30000);
    connection.setIdleTimeout(30000);
    connection.setRetryAttempts(2);
    connection.setRetryWait(500);

    try {
      connection.connect();
    } catch (Exception ex) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
      return;
    }

    /**
     * It is necessary to send a SC Status with protocol version 2.0 else server
     * will assume 1.0)
     */
    SCStatus scStatusRequest = new SCStatus();
    scStatusRequest.setProtocolVersion(ProtocolVersion.VERSION_2_00);

    Message scStatusResponse;

    try {
      scStatusResponse = connection.send(scStatusRequest);
    } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
      return;
    }

    if (!(scStatusResponse instanceof ACSStatus)) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, "Error - Status Request did not return valid response from server.");
      return;
    }

    /**
     * For debugging XML handling code (but could be useful in Cocoon)
     */
    scStatusResponse.xmlEncode(System.out);

    /**
     * Check if the server can support checkout
     */
    if (!((ACSStatus) scStatusResponse).getSupportedMessages().isCheckOut()) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, "Check out not supported");
      return;
    }

    ItemInformation itemInformation = new ItemInformation();

    /**
     * Now try a checkout request
     */
    itemInformation.setItemIdentifier("300000000");
    itemInformation.setTransactionDate(new Date());

    Message checkOutResponse;

    try {
      checkOutResponse = connection.send(itemInformation);
    } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
      return;
    }

    if (!(checkOutResponse instanceof ItemInformationResponse)) {
      Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, "Error - CheckOut Request did not return valid response from server");
      return;
    }
    checkOutResponse.xmlEncode(System.out);

    connection.disconnect();
  }

}
