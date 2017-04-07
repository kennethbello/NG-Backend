package dataconnector.GettingStarted;

import com.mozu.api.ApiException;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.contracts.productadmin.*;
import com.mozu.api.resources.commerce.OrderResource;
import com.mozu.api.resources.commerce.catalog.admin.LocationInventoryResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.api.resources.commerce.customer.accounts.CustomerContactResource;
import com.mozu.api.security.AppAuthenticator;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MozuDataConnectorTest {

    MozuApiContext apiContext = new MozuApiContext(18740, 29081);

    // Exercise 9.1
    public void getAttributes() throws Exception {
        AttributeResource attributeResource = new AttributeResource(apiContext);
        AttributeCollection attributes = attributeResource.getAttributes(0, 200, "", "", "");

        Attribute attrRating = attributeResource.getAttribute("tenant~rating");

        // 9.1.3
        System.out.println("attrRating datatype: " + attrRating.getDataType());
        System.out.println("attrRating inputtype: " + attrRating.getInputType());
        System.out.println("attrRating extra: " + attrRating.getIsExtra());
        System.out.println("attrRating option: " + attrRating.getIsOption());
        System.out.println("attrRating property: " + attrRating.getIsProperty());
        System.out.println(attrRating.getVocabularyValues());

        Attribute attrColor = attributeResource.getAttributes(null, null, "", "attributecode sw 'color'", "").getItems().get(0);

        // 9.1.3
        System.out.println("attrColor datatype: " + attrColor.getDataType());
        System.out.println("attrColor inputtype: " + attrColor.getInputType());
        System.out.println("attrColor extra: " + attrColor.getIsExtra());
        System.out.println("attrColor option: " + attrColor.getIsOption());
        System.out.println("attrColor property: " + attrColor.getIsProperty());
        for( int i = 0 ; i < attrColor.getVocabularyValues().size(); i++) {
            System.out.println("attrColor value: " + attrColor.getVocabularyValues().get(i).getValue());
            System.out.println("attrColor string: " + attrColor.getVocabularyValues().get(i).getContent().getStringValue());
        }

    }


    // Exercise 9.2
    public void addAttributes() throws Exception {
        AttributeResource attributeResource = new AttributeResource(apiContext);

        String attributeName = "Monogram";

        // define the attribute name
        Attribute attribute = new Attribute();

        AttributeLocalizedContent content = new AttributeLocalizedContent();
        content.setName(attributeName);

        // 9.2.2
        // add attribute values
        attribute.setAdminName(attributeName);
        attribute.setAttributeCode("monogram");
        attribute.setAttributeFQN("tenant~monogram");
        attribute.setInputType("TextBox");
        attribute.setDataType("String");
        attribute.setIsExtra(true);
        attribute.setIsOption(false);
        attribute.setIsProperty(false);
        attribute.setNamespace("tenant");
        attribute.setContent(content);
        attribute.setValueType("ShopperEntered");

        try{
            // add new attribute
            Attribute createdAttribute = attributeResource.addAttribute(attribute);

            // update search options, update attributes, return back only the
            // attributeFQN and no the whole object
            Attribute updatedAttribute = attributeResource.updateAttribute(createdAttribute, createdAttribute.getAttributeFQN(), "AttributeFQN");

            System.out.println(updatedAttribute.getAttributeFQN());
        } catch (ApiException e){
            System.err.println("ApiException: " + e.getMessage());
        }


        // 9.3 [Optional]
        // setting option to true or setting the input type to list is causing a 409
        // TODO: find correct attributes that must be set
//        String purseAttrName  = "Purse-Size";
//
//        AttributeLocalizedContent purseContent = new AttributeLocalizedContent();
//        purseContent.setName(purseAttrName);
//
//        Attribute purseSizeAttr = new Attribute();
//
//        purseSizeAttr.setAdminName(purseAttrName);
//        purseSizeAttr.setAttributeCode("Purse-Size");
//        purseSizeAttr.setAttributeFQN("tenant~Purse-Size");
//        purseSizeAttr.setInputType("TextBox");
//        purseSizeAttr.setDataType("String");
//        purseSizeAttr.setIsExtra(false);
//        purseSizeAttr.setIsOption(true);
//        purseSizeAttr.setIsProperty(false);
//        purseSizeAttr.setNamespace("tenant");
//        purseSizeAttr.setContent(purseContent);
//        purseSizeAttr.setValueType("ShopperEntered");
//        List<AttributeVocabularyValue> vocabularyValues = Arrays.asList(new AttributeVocabularyValue(), new AttributeVocabularyValue(), new AttributeVocabularyValue());
//        vocabularyValues.get(0).setDisplayOrder(1);
//        vocabularyValues.get(0).setValue("Petite");
//        vocabularyValues.get(1).setDisplayOrder(2);
//        vocabularyValues.get(1).setValue("Classic");
//        vocabularyValues.get(2).setDisplayOrder(3);
//        vocabularyValues.get(2).setValue("Alta");
//        purseSizeAttr.setVocabularyValues(vocabularyValues);
//
//        try{
//            Attribute createdPurseAttribute = attributeResource.addAttribute(purseSizeAttr);
//            Attribute updatedPurseAttribute = attributeResource.updateAttribute(createdPurseAttribute, createdPurseAttribute.getAttributeFQN(), "AttributeFQN");
//            System.out.println(updatedPurseAttribute.getAttributeFQN());
//        } catch (ApiException e){
//            System.err.println("ApiException: " + e.getMessage());
//            Attribute updatedPurseAttribute = attributeResource.updateAttribute(purseSizeAttr, purseSizeAttr.getAttributeFQN(), "AttributeFQN");
//            System.out.println(updatedPurseAttribute.getAttributeFQN());
//        }



    }

    // Exercise 10.1
    public void getProductTypes() throws Exception{

        // create a new productType resource
        ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);

        // get list of attributes with max page size and starting index at the beginning
        ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 200, "", "", "");
        productTypes.getItems().stream().forEach(
            element -> System.out.println(element.getName())
        );

        Stream purseType = productTypes.getItems().stream().filter(productType -> productType.getName() == "Monogram");

        System.out.println(purseType.collect(Collectors.toList()));

    }

    // Exercise 11.1
    public void getProducts() throws Exception{

        // create a new product resource
        ProductResource productResource = new ProductResource(apiContext);

        // get product type filtered by name
        ProductCollection products = productResource.getProducts(0, 200, "", "", "", null, null, "");

        // create a new location inventory resource
        LocationInventoryResource inventoryResource = new LocationInventoryResource(apiContext);

    }

    // Exercise 13.1
    public void getCustomers() throws Exception{

        CustomerAccountResource accountResource = new CustomerAccountResource(apiContext);

        CustomerContactResource contactResource = new CustomerContactResource(apiContext);
    }

    // Exercise 14.1
    public void getOrders() throws Exception{

        OrderResource orderResource = new OrderResource(apiContext);
    }

    public static void main(String[] args){
        AppAuthInfo appAuthInfo = new AppAuthInfo();

        /** replace with your authentication configuration */
        appAuthInfo.setApplicationId("KTrain.MDCID_K.1.0.0.Release");
        appAuthInfo.setSharedSecret("dea4fa3460d14e58a6fe2db124abc8b1");

        AppAuthenticator.initialize(appAuthInfo);

        MozuDataConnectorTest mozuDataConnectorTest = new MozuDataConnectorTest();

        try {
//            mozuDataConnectorTest.getAttributes();
            mozuDataConnectorTest.addAttributes();
            mozuDataConnectorTest.getProductTypes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
