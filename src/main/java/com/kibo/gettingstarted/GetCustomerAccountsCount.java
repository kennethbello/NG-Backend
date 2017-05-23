package com.kibo.gettingstarted;

import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.api.security.AppAuthenticator;

public class GetCustomerAccountsCount {
    public static void main(String[] args){
        AppAuthInfo appAuthInfo = new AppAuthInfo();
        
        /** replace with your authentication configuration */
        appAuthInfo.setApplicationId("MozuDataConnector_Todd");
        appAuthInfo.setSharedSecret("a679fb458f1f4e73817057717ec5dd90");
        
        AppAuthenticator.initialize(appAuthInfo);
        
        /** replace with your tenant ID and site ID */
        MozuApiContext apiContext = new MozuApiContext(18821, 29246);
        
        /** create a customer account resource */
        CustomerAccountResource customerAccountResource = new CustomerAccountResource(apiContext);
        
        /** log the total number of customer accounts */
        try {
            System.out.println("Number of Customer Accounts: " + customerAccountResource.getAccounts().getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
