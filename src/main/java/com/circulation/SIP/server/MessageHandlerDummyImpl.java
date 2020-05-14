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

import com.circulation.SIP.dao.PulDao;
import com.circulation.SIP.messages.*;
import com.circulation.SIP.types.enumerations.HoldMode;

public class MessageHandlerDummyImpl implements MessageHandler {

    PulDao pulDao = new PulDao();

    @Override
    public ACSStatus Status(SCStatus msg) {
        ACSStatus response = new ACSStatus();
        response.getSupportedMessages().setBlockPatron(true);
        response.getSupportedMessages().setCheckIn(true);
        response.getSupportedMessages().setCheckOut(true);
        response.getSupportedMessages().setEndPatronSession(true);
        response.getSupportedMessages().setFeePaid(true);
        response.getSupportedMessages().setHold(true);
        response.getSupportedMessages().setItemInformation(true);
        response.getSupportedMessages().setItemStatusUpdate(true);
        response.getSupportedMessages().setLogin(true);
        response.getSupportedMessages().setPatronEnable(true);
        response.getSupportedMessages().setPatronInformation(true);
        response.getSupportedMessages().setPatronStatusRequest(true);
        response.getSupportedMessages().setRenew(true);
        response.getSupportedMessages().setRenewAll(true);
        response.getSupportedMessages().setScAcsStatus(true);
        response.getSupportedMessages().setRequestScAcsResend(true);
        return response;
    }

    @Override
    public PatronStatusResponse BlockPatron(
            BlockPatron msg) {
        return new PatronStatusResponse();
    }

    @Override
    public CheckInResponse CheckIn(
            CheckIn msg) {
        CheckInResponse checkInResponse = new CheckInResponse();
        checkInResponse.setItemIdentifier(msg.getItemIdentifier());
        checkInResponse.setOk(pulDao.checkInItem(msg.getItemIdentifier()));
        if(checkInResponse.isOk()){
            checkInResponse.setScreenMessage("CheckIn Successful.");
        }else{
            checkInResponse.setScreenMessage("CheckIn failed.");
        }
        return checkInResponse;
    }

    @Override
    public CheckOutResponse CheckOut(CheckOut msg) {
        CheckOutResponse checkOutResponse = new CheckOutResponse();
        checkOutResponse.setItemIdentifier(msg.getItemIdentifier());
        checkOutResponse.setPatronIdentifier(msg.getPatronIdentifier());
        checkOutResponse.setOk(pulDao.checkoutItem(msg));
        if(checkOutResponse.isOk()){
            checkOutResponse.setScreenMessage("Checkout Successful.");
        }else{
            checkOutResponse.setScreenMessage("Checkout failed.");
        }
        return checkOutResponse;
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
        HoldResponse holdResponse = new HoldResponse();
        if(msg.getHoldMode().equals(HoldMode.ADD)) {
            holdResponse.setScreenMessage("Request placed.");
        }else{
            holdResponse.setScreenMessage("Request Cancelled.");
        }
        holdResponse.setAvailable(true);
        holdResponse.setOk(true);
        holdResponse.setPatronIdentifier(msg.getPatronIdentifier());
        return holdResponse;
    }

    @Override
    public RecallResponse Recall(Recall msg) {
        RecallResponse recallResponse = new RecallResponse();
        if(msg.getHoldMode().equals(HoldMode.ADD)) {
            recallResponse.setScreenMessage("Request placed.");
        }else{
            recallResponse.setScreenMessage("Request Cancelled.");
        }
        recallResponse.setAvailable(true);
        recallResponse.setOk(true);
        recallResponse.setPatronIdentifier(msg.getPatronIdentifier());
        return recallResponse;
    }

    @Override
    public BibResponse Bib(Bib msg) {
        BibResponse bibResponse = new BibResponse();
        bibResponse.setItemIdentifier(msg.getItemIdentifier());
        Integer existingBibId = pulDao.findBibByItemId(msg.getItemIdentifier());
        if(existingBibId == null) {
            Integer bibId = pulDao.createBib(msg);
            if (bibId != null) {
                bibResponse.setBibIdentifier("" + bibId);
                bibResponse.setScreenMessage("Create Bib successful.");
            } else {
                bibResponse.setScreenMessage("CreateBib Failed.");
            }
        }else{
            bibResponse.setBibIdentifier("" + existingBibId);
            bibResponse.setScreenMessage("Item Barcode already Exist");
        }
        return bibResponse;
    }

    @Override
    public ItemInformationResponse ItemInformation(
            ItemInformation msg) {
        ItemInformationResponse itemInformationResponse = pulDao.findItemByItemId(msg.getItemIdentifier());
        if(itemInformationResponse.getItemIdentifier()!=null){
            itemInformationResponse.setScreenMessage("Item Info retrieved successfully");
        }else{
            itemInformationResponse.setItemIdentifier(msg.getItemIdentifier());
            itemInformationResponse.setScreenMessage("Item barcode not found");
        }
        return itemInformationResponse;
    }

    @Override
    public ItemStatusUpdateResponse ItemStatusUpdate(
            ItemStatusUpdate msg) {

        return new ItemStatusUpdateResponse();
    }

    @Override
    public LoginResponse Login(Login msg) {
        return new LoginResponse(pulDao.validateLogin(msg.getLoginUserId(),msg.getLoginPassword()));
    }

    @Override
    public PatronEnableResponse PatronEnable(
            PatronEnable msg) {
        return new PatronEnableResponse();
    }

    @Override
    public PatronInformationResponse PatronInformation(
            PatronInformation msg) {
        PatronInformationResponse patronInformationResponse = pulDao.findPatronByPatronId(msg.getPatronIdentifier());
        if(patronInformationResponse.getPatronIdentifier()!=null){
            patronInformationResponse.setScreenMessage("Patron validated successfully");
        }else{
            patronInformationResponse.setPatronIdentifier(msg.getPatronIdentifier());
            patronInformationResponse.setUnavailableHoldsCount(0);
            patronInformationResponse.setChargedItemsCount(0);
            patronInformationResponse.setHoldItemsCount(0);
            patronInformationResponse.setFineItemsCount(0);
            patronInformationResponse.setRecallItemsCount(0);
            patronInformationResponse.setOverdueItemsCount(0);
            patronInformationResponse.setScreenMessage("Patron barcode not found");
        }
        return patronInformationResponse;
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
