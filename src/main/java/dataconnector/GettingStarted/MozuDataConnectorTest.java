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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MozuDataConnectorTest {

    private MozuApiContext apiContext = new MozuApiContext(18740, 29081);

    /*
     INTERACTING WITH PRODUCT ATTRIBUTES
     */

    // Exercise 9.1
    private void getAttributes() throws Exception {
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
    private void addAttributes() throws Exception {
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

    /*
    INTERACTING WITH PRODUCT TYPES
     */
    // Exercise 10.1
    private void getProductTypes(){

        // create a new productType resource
        ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);

        // get list of attributes with max page size and starting index at the beginning
        try {
            ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 200, "", "", "");
            // each product type
//        productTypes.getItems().stream().forEach(
//            element -> System.out.println(element.getName())
//        );

            ArrayList<AttributeInProductType> options = new ArrayList<>();

            // filter through product type resource and
            // loop through all options
            productTypes.getItems().stream().filter(
                productType -> productType.getName().equals("Purse")
            ).forEach(
                pType -> pType.getOptions().forEach(
                    option -> options.add(option)
                )
            );

            // output options
            options.forEach(
                opt -> opt.getVocabularyValues().forEach(
                    val -> System.out.println(val.getValue())
                )
            );
        }catch (Exception e) {
            System.err.println("APIException: " + e.getMessage());
        }
    }

    // Exercise 10.2
    private void updateProductType() throws Exception{

        // create a new productType resource
        ProductTypeResource productTypeResource = new ProductTypeResource(apiContext);

        // get list of attributes with max page size and starting index at the beginning
        ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 200, "", "", "");

        MozuDataConnectorTest data = new MozuDataConnectorTest();

        data.addEOPToProductType(productTypes, "Purse", "tenant~purse-size", productTypeResource, "extra");

    }

    private void addEOPToProductType(ProductTypeCollection productTypeCollection, String prodTypeName, String fqn, ProductTypeResource resource, String choice) throws Exception{

        MozuDataConnectorTest data = new MozuDataConnectorTest();

        Attribute attr = data.getAttributeForProduct(fqn);

        List<AttributeInProductType> attributeInProductTypes = new ArrayList<>();

        attributeInProductTypes.add(data.createAttributeInProductType(attr));

        productTypeCollection.getItems().stream().filter(
            productType -> productType.getName().equals(prodTypeName)
        ).forEach(pType -> {

            // choice to add attribute as extra, option, or property
            switch (choice) {
                case "Extra":
                    pType.getExtras().forEach(
                        attrProd -> {
                            attributeInProductTypes.add(attrProd);
                        });
                    // add existing and new extras
                    pType.setExtras(attributeInProductTypes);
                    break;
                case "Option":
                    pType.getOptions().forEach(
                        attrProd -> {
                            attributeInProductTypes.add(attrProd);
                        });
                    // add existing and new options
                    pType.setOptions(attributeInProductTypes);
                    break;
                case "Property":
                    pType.getProperties().forEach(
                        attrProd -> {
                            attributeInProductTypes.add(attrProd);
                        });
                    // add existing and new properties
                    pType.setProperties(attributeInProductTypes);
                    break;
            }
            try {
                //update product type
                resource.updateProductType(pType, pType.getId());
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });
    }

    private Attribute getAttributeForProduct(String name) throws Exception{
        // create a new attribute resource
        AttributeResource attributeResource = new AttributeResource(apiContext);

        // list of attributes
        AttributeCollection attributes = attributeResource.getAttributes(0, 200, "", "", "");

        return attributeResource.getAttribute(name);
    }

    private AttributeInProductType createAttributeInProductType(Attribute attr) throws Exception{

        AttributeInProductType attributeInProductType = new AttributeInProductType();

        // add attribute to attribute in product type
        attributeInProductType.setAttributeFQN(attr.getAttributeFQN());

        return attributeInProductType;
    }

    /*
     INTERACTING WITH PRODUCTS
     */

    // Exercise 11.1
    private void getProducts() throws Exception{

        // create a new product resource
        ProductResource productResource = new ProductResource(apiContext);

        // get product type filtered by name
        ProductCollection products = productResource.getProducts(0, 200, "", "", "", null, null, "");
        Product prod = new Product();

        // create a new location inventory resource
        LocationInventoryResource inventoryResource = new LocationInventoryResource(apiContext);

        System.out.println("Total number of products: " + products.getItems().size());

        final AtomicInteger conf = new AtomicInteger();
        final AtomicInteger nonConf = new AtomicInteger();
        final AtomicInteger numOfTents = new AtomicInteger();
        products.getItems().forEach(product -> {
                if(product.getHasConfigurableOptions()){
                conf.incrementAndGet();
            }else{
                nonConf.incrementAndGet();
            }
            if(product.getProductTypeId().equals(8)){
                numOfTents.incrementAndGet();
            }
        });
        System.out.println("Total configurable products: "+ conf.get());
        System.out.println("Total non-configurable products: "+ nonConf.get());
        System.out.println("Total number of tent products: "+ numOfTents.get());

    }

    // Exercise 13.1
    private void getCustomers() throws Exception{

        CustomerAccountResource accountResource = new CustomerAccountResource(apiContext);

        CustomerContactResource contactResource = new CustomerContactResource(apiContext);
    }

    // Exercise 14.1
    private void getOrders() throws Exception{

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
//            mozuDataConnectorTest.addAttributes();
//            mozuDataConnectorTest.getProductTypes();
            mozuDataConnectorTest.updateProductType();
            mozuDataConnectorTest.getProducts();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
