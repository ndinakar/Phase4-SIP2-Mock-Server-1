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
package com.circulation.SIP.messages;

import com.circulation.SIP.annotations.Command;
import com.circulation.SIP.annotations.PositionedField;
import com.circulation.SIP.annotations.TaggedField;
import com.circulation.SIP.fields.FieldPolicy;

import java.util.Date;

@Command("09")
public class CheckIn extends Message {
    private static final long serialVersionUID = -7321140594135175919L;
    @PositionedField(start = 2, end = 2)
    private Boolean noBlock;
    @PositionedField(start = 3, end = 20)
    private Date transactionDate;
    @PositionedField(start = 21, end = 38)
    private Date returnDate;
    @TaggedField(FieldPolicy.REQUIRED)
    private String currentLocation;
    @TaggedField
    private String institutionId;
    @TaggedField(FieldPolicy.REQUIRED)
    private String itemIdentifier;
    @TaggedField(FieldPolicy.REQUIRED)
    private String terminalPassword;
    @TaggedField(FieldPolicy.NOT_REQUIRED)
    private String itemProperties;
    @TaggedField
    private Boolean cancel;

    public Boolean isCancel() {
        return this.cancel;
    }

    public String getCurrentLocation() {
        return this.currentLocation;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public String getItemIdentifier() {
        return this.itemIdentifier;
    }

    public String getItemProperties() {
        return this.itemProperties;
    }

    public Boolean isNoBlock() {
        return this.noBlock;
    }

    public Date getReturnDate() {
        return this.returnDate;
    }

    public String getTerminalPassword() {
        return this.terminalPassword;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTerminalPassword(String terminalPassword) {
        this.terminalPassword = terminalPassword;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setNoBlock(Boolean noBlock) {
        this.noBlock = noBlock;
    }

    public void setItemProperties(String itemProperties) {
        this.itemProperties = itemProperties;
    }

    public void setItemIdentifier(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }
}
