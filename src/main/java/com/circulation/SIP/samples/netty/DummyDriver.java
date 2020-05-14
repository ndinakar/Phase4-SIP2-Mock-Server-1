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
package com.circulation.SIP.samples.netty;

import com.circulation.SIP.messages.ACSStatus;
import com.circulation.SIP.messages.CheckInResponse;
import com.circulation.SIP.messages.CheckOutResponse;
import com.circulation.SIP.messages.EndSessionResponse;
import com.circulation.SIP.messages.FeePaidResponse;
import com.circulation.SIP.messages.HoldResponse;
import com.circulation.SIP.messages.ItemInformationResponse;
import com.circulation.SIP.messages.ItemStatusUpdateResponse;
import com.circulation.SIP.messages.LoginResponse;
import com.circulation.SIP.messages.PatronEnableResponse;
import com.circulation.SIP.messages.PatronInformationResponse;
import com.circulation.SIP.messages.PatronStatusRequest;
import com.circulation.SIP.messages.PatronStatusResponse;
import com.circulation.SIP.messages.RenewAllResponse;
import com.circulation.SIP.messages.RenewResponse;
import com.circulation.SIP.messages.SCStatus;
import com.circulation.SIP.netty.server.driver.AbstractDriver;
import com.circulation.SIP.netty.server.driver.operation.*;
import com.circulation.SIP.messages.*;

/**
 *
 * @author Matthew
 */
public class DummyDriver extends AbstractDriver
  implements BlockPatronOperation,
              CheckInOperation,
              CheckOutOperation,
              EndPatronSessionOperation,
              FeePaidOperation,
              HoldOperation,
              RecallOperation,
              BibOperation,
              ItemInformationOperation,
        ItemStatusUpdateOperation,
              LoginOperation,
              PatronEnableOperation,
              PatronInformationOperation,
              PatronStatusOperation,
              RenewAllOperation,
              RenewOperation
{

  @Override
  public ACSStatus Status(ACSStatus status, SCStatus msg) {
    status.setACSRenewalPolicy(false);
    status.setCheckInOk(true);
    status.setCheckOutOk(true);
    status.setOfflineOk(false);
    status.setStatusUpdateOk(true);
    return status;
  }

    @Override
    public PatronStatusResponse BlockPatron(
            BlockPatron msg) {
        return new PatronStatusResponse();
    }

    @Override
    public CheckInResponse CheckIn(
            CheckIn msg) {
        return new CheckInResponse();
    }

    @Override
    public CheckOutResponse CheckOut(
            CheckOut msg) {
        return new CheckOutResponse();
    }

    @Override
    public EndSessionResponse EndPatronSession(
            EndPatronSession msg) {
        return new EndSessionResponse();
    }

    @Override
    public FeePaidResponse FeePaid(
            FeePaid msg) {
        return new FeePaidResponse();
    }

    @Override
    public HoldResponse Hold(Hold msg) {
        return new HoldResponse();
    }

    @Override
    public RecallResponse Recall(Recall msg) {
        return new RecallResponse();
    }

    @Override
    public BibResponse Bib(Bib msg) {
        return new BibResponse();
    }

    @Override
    public ItemInformationResponse ItemInformation(
            ItemInformation msg) {
        return new ItemInformationResponse();
    }

    @Override
    public ItemStatusUpdateResponse ItemStatusUpdate(
            ItemStatusUpdate msg) {
        return new ItemStatusUpdateResponse();
    }

    @Override
    public LoginResponse Login(Login msg) {
        return new LoginResponse(true);
    }

    @Override
    public PatronEnableResponse PatronEnable(
            PatronEnable msg) {
        return new PatronEnableResponse();
    }

    @Override
    public PatronInformationResponse PatronInformation(
            PatronInformation msg) {
        return new PatronInformationResponse();
    }

    @Override
    public PatronStatusResponse PatronStatus(PatronStatusRequest msg) {
        return new PatronStatusResponse();
    }

    @Override
    public RenewResponse Renew(Renew msg) {
        return new RenewResponse();
    }

    @Override
    public RenewAllResponse RenewAll(
            RenewAll msg) {
        return new RenewAllResponse();
    }
}
