package dataconnector.GettingStarted;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.api.security.AppAuthenticator;

public class GetCustomerAccountsCount {
    public static void main(String[] args){
        AppAuthInfo appAuthInfo = new AppAuthInfo();

        /** replace with your authentication configuration */
        appAuthInfo.setApplicationId("KTrain.MDCID_K.1.0.0.Release");
        appAuthInfo.setSharedSecret("dea4fa3460d14e58a6fe2db124abc8b1");

        AppAuthenticator.initialize(appAuthInfo);

        /** replace with your tenant ID and site ID */
        MozuApiContext apiContext = new MozuApiContext(18740, 29081);

        /** create a customer account resource */
        CustomerAccountResource customerAccountResource = new CustomerAccountResource(apiContext);

        /** log the total number of customer accounts */
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(customerAccountResource.getAccounts());
            System.out.println(json);
            System.out.println("Number of Customer Accounts: " + customerAccountResource.getAccounts().getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}