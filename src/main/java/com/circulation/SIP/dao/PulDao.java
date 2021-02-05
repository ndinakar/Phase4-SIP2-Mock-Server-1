Skip to content
Search or jump to…

Pull requests
Issues
Marketplace
Explore
 
@srinduri04 
ResearchCollectionsAndPreservation
/
Phase4-SIP2-Mock-Server
Private
3
0
0
Code
Issues
Pull requests
Actions
Projects
Security
Insights
Settings
Phase4-SIP2-Mock-Server/src/main/java/com/circulation/SIP/dao/PulDao.java /
@JancyRoachR
JancyRoachR Mysql version change and added loggers
Latest commit 9b264fb on Jul 8, 2020
 History
 2 contributors
@mosesjoel@JancyRoachR
152 lines (140 sloc)  7.54 KB
  
package com.circulation.SIP.dao;

import com.circulation.SIP.messages.*;
import com.circulation.SIP.types.enumerations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.*;
/**
 * Created by giris on 3/3/20.
 */
public class PulDao {

    public static Connection connection = null;

    private static Log logger = LogFactory.getLog(PulDao.class);
    public static Connection getConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "", "", "");
        }
        return connection;
    }

    public boolean validateLogin(String userName, String password) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select username from login where username = ? and password = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            logger.error("username = "+userName);
            logger.error("password ="+password);
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }

    public PatronInformationResponse findPatronByPatronId(String patronIdentifier) {
        PatronInformationResponse patronInformationResponse = new PatronInformationResponse();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select * from patron where patron_identifier = ?");
            preparedStatement.setString(1, patronIdentifier);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patronInformationResponse.setUnavailableHoldsCount(resultSet.getInt("unavailable_holds_count"));
                patronInformationResponse.setChargedItemsCount(resultSet.getInt("charged_Items_count"));
                patronInformationResponse.setHoldItemsCount(resultSet.getInt("hold_Items_count"));
                patronInformationResponse.setFineItemsCount(resultSet.getInt("fine_Items_count"));
                patronInformationResponse.setRecallItemsCount(resultSet.getInt("recall_Items_count"));
                patronInformationResponse.setOverdueItemsCount(resultSet.getInt("overdue_Items_count"));
                patronInformationResponse.setTransactionDate(resultSet.getDate("transaction_date"));
                patronInformationResponse.setPersonalName(resultSet.getString("person_name"));
                patronInformationResponse.setValidPatron(resultSet.getBoolean("valid_patron"));
                patronInformationResponse.setValidPatronPassword(resultSet.getBoolean("valid_Patron_password"));
                patronInformationResponse.setFeeAmount(resultSet.getString("fee_amount"));
                patronInformationResponse.setEmailAddress(resultSet.getString("email_address"));
                patronInformationResponse.setPatronIdentifier(patronIdentifier);
                patronInformationResponse.setCurrencyType(CurrencyType.US_DOLLARS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patronInformationResponse;
    }

    public ItemInformationResponse findItemByItemId(String itemIdentifier) {
        ItemInformationResponse itemInformationResponse = new ItemInformationResponse();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select * from item where item_identifier = ?");
            preparedStatement.setString(1, itemIdentifier);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemInformationResponse.setItemIdentifier(itemIdentifier);
                itemInformationResponse.setCirculationStatus(CirculationStatus.AVAILABLE);
                itemInformationResponse.setSecurityMarker(SecurityMarker.OTHER);
                itemInformationResponse.setFeeType(FeeType.OTHER);
                itemInformationResponse.setTransactionDate(resultSet.getDate("transaction_date"));
                itemInformationResponse.setHoldQueueLength(resultSet.getInt("hold_queue_length"));
                itemInformationResponse.setDueDate(resultSet.getString("due_date"));
                itemInformationResponse.setRecallDate(resultSet.getDate("recal_date"));
                itemInformationResponse.setHoldPickupDate(resultSet.getDate("hold_pickup_date"));
                itemInformationResponse.setTitleIdentifier(resultSet.getString("title_identifier"));
                itemInformationResponse.setMediaType(MediaType.OTHER);
                itemInformationResponse.setPermanentLocation(resultSet.getString("permanent_location"));
                itemInformationResponse.setCurrentLocation(resultSet.getString("current_location"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemInformationResponse;
    }

    public boolean checkoutItem(CheckOut checkOut){
        try{
            String sql = "Insert into check_out (transaction_id, transaction_date, patron_identifier, item_identifier,due_date)values (?,?,?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, ""+Math.random());
            preparedStatement.setDate(2, new Date(new java.util.Date().getTime()));
            preparedStatement.setString(3, ""+checkOut.getPatronIdentifier());
            preparedStatement.setString(4, ""+checkOut.getItemIdentifier());
            preparedStatement.setDate(5, new Date(checkOut.getNbDueDate().getTime()));
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkInItem(String itemIdentifier){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select transaction_id from check_out where item_identifier = ?");
            preparedStatement.setString(1, itemIdentifier);
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }

    public Integer findBibByItemId(String itemIdentifier){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select bib_id from bib where item_identifier = ?");
            preparedStatement.setString(1, itemIdentifier);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("bib_id");
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Integer createBib(Bib bib){
        try{
            String sql = "Insert into bib(patron_identifier, item_identifier, title_identifier)values (?,?,?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, ""+bib.getPatronIdentifier());
            preparedStatement.setString(2, ""+bib.getItemIdentifier());
            preparedStatement.setString(3, ""+bib.getTitleIdentifier());
            preparedStatement.execute();
            ResultSet resultSet =  preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
