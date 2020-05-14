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

import com.circulation.SIP.messages.*;


public interface MessageHandler {
    public ACSStatus Status(SCStatus msg);

    public PatronStatusResponse BlockPatron(BlockPatron msg);

    public CheckInResponse CheckIn(CheckIn msg);

    public CheckOutResponse CheckOut(CheckOut msg);

    public EndSessionResponse EndPatronSession(EndPatronSession msg);

    public FeePaidResponse FeePaid(FeePaid msg);

    public HoldResponse Hold(Hold msg);

    public RecallResponse Recall(Recall msg);

    public BibResponse Bib(Bib msg);

    public ItemInformationResponse ItemInformation(ItemInformation msg);

    public ItemStatusUpdateResponse ItemStatusUpdate(ItemStatusUpdate msg);

    public LoginResponse Login(Login msg);

    public PatronEnableResponse PatronEnable(PatronEnable msg);

    public PatronInformationResponse PatronInformation(PatronInformation msg);

    public PatronStatusResponse PatronStatus(PatronStatusRequest msg);

    public RenewResponse Renew(Renew msg);

    public RenewAllResponse RenewAll(RenewAll msg);
}
