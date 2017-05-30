package com.kibo.dataconnector;

import com.mozu.api.ApiException;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.core.Phone;
import com.mozu.api.contracts.customer.CustomerAccount;
import com.mozu.api.contracts.customer.CustomerAccountCollection;
import com.mozu.api.contracts.customer.CustomerContact;
import com.mozu.api.contracts.productadmin.*;
import com.mozu.api.contracts.tenant.MasterCatalog;
import com.mozu.api.contracts.tenant.Site;
import com.mozu.api.contracts.tenant.Tenant;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.api.resources.commerce.catalog.admin.LocationInventoryResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductVariationResource;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.api.resources.commerce.customer.accounts.CustomerContactResource;
import com.mozu.api.resources.platform.TenantResource;
import com.mozu.api.security.AppAuthenticator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by todd.purcell on 4/21/17.
 */

public class MozuDataConnectorTest {

    private AppAuthInfo appAuthInfo = new AppAuthInfo();
    public AppAuthInfo getAppAuthInfo() {
        return appAuthInfo;
    }

    public void setAppAuthInfo(AppAuthInfo appAuthInfo) {
        this.appAuthInfo = appAuthInfo;
    }


    private MozuApiContext apiContext;
    public MozuApiContext getApiContext() {
        return apiContext;
    }

    public void setApiContext(MozuApiContext apiContext) {
        this.apiContext = apiContext;
    }

    private Tenant tenant;
    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    private int tenantId = 0;
    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }


    private int siteId = 0;
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }


    private String applicationId;
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }


    private String sharedSecret;
    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }


    private String testAttrSearchFQN;
    public String getTestAttrSearchFQN() {
        return testAttrSearchFQN;
    }

    public void setTestAttrSearchFQN(String testAttrSearchFQN) {
        this.testAttrSearchFQN = testAttrSearchFQN;
    }


    private String testOptSearchFQN;
    public String getTestOptSearchFQN() {
        return testOptSearchFQN;
    }

    public void setTestOptSearchFQN(String testOptSearchFQN) {
        this.testOptSearchFQN = testOptSearchFQN;
    }


    private String simpleAttributeName;
    public String getSimpleAttributeName() {
        return simpleAttributeName;
    }

    public void setSimpleAttributeName(String simpleAttributeName) {
        this.simpleAttributeName = simpleAttributeName;
    }


    private String optionAttributeName;
    public String getOptionAttributeName() {
        return optionAttributeName;
    }
    private String[] optionItemList;
    public void setOptionAttributeName(String optionAttributeName) {
        this.optionAttributeName = optionAttributeName;
    }

    public String[] getOptionItemList() {
        return optionItemList;
    }

    private String testProductType;
    public void setOptionItemList(String[] optionItemList) {
        this.optionItemList = optionItemList;
    }

    public String getTestProductType() {
        return testProductType;
    }

    public void setTestProductType(String testProductType) {
        this.testProductType = testProductType;
    }

    private String testProductSearchCode;
    public String getTestProductSearchCode() {
        return testProductSearchCode;
    }

    public void setTestProductSearchCode(String testProductSearchCode) {
        this.testProductSearchCode = testProductSearchCode;
    }

    private String locationCode;
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    private String productCode;
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Constructor
     */
    public MozuDataConnectorTest(){
        System.out.println("In Constructor");

        setTenantId(Integer.parseInt(System.getenv("TenantId")));
        System.out.println(getTenantId());

        setSiteId(Integer.parseInt(System.getenv("SiteId")));
        System.out.println(getSiteId());

        setApplicationId(System.getenv("ApplicationId"));
        System.out.println(getApplicationId());

        setSharedSecret(System.getenv("SharedSecret"));
        System.out.println(getSharedSecret());

        setTestAttrSearchFQN(System.getenv("TestAttrSearchFQN"));
        System.out.println(getTestAttrSearchFQN());

        setTestOptSearchFQN(System.getenv("TestOptSearchFQN"));
        System.out.println(getTestOptSearchFQN());

        setSimpleAttributeName(System.getenv("SimpleAttributeName"));
        System.out.println(getSimpleAttributeName());

        setOptionAttributeName(System.getenv("OptionAttributeName"));
        System.out.println(getOptionAttributeName());

        setOptionItemList(System.getenv("OptionItemList").split(","));
        for(int i = 0; i < optionItemList.length ; i++){
            System.out.println(optionItemList[i]);
        }

        setTestProductType(System.getenv("TestProductType"));
        System.out.println(getTestProductType());

        setTestProductSearchCode(System.getenv("TestProductSearchCode"));
        System.out.println(getTestProductSearchCode());

        setLocationCode(System.getenv("LocationCode"));
        System.out.println(getLocationCode());

        setProductCode(System.getenv("ProductCode"));
        System.out.println(getProductCode());

        getAppAuthInfo().setApplicationId(this.applicationId);
        getAppAuthInfo().setSharedSecret(this.sharedSecret);
        AppAuthenticator.initialize(appAuthInfo);
        setApiContext(new MozuApiContext(tenantId, siteId));
    }

    public static void main(String[] args){

        MozuDataConnectorTest mozuDataConnectorTest = new MozuDataConnectorTest();

        try {
            System.out.println("In Main Method Try");
            mozuDataConnectorTest.assignTenant();
            mozuDataConnectorTest.getAttributes();
            mozuDataConnectorTest.addAttribute();
            mozuDataConnectorTest.addOption();
            mozuDataConnectorTest.getProductTypes();
            mozuDataConnectorTest.updateProductType();
            mozuDataConnectorTest.getProducts();
            mozuDataConnectorTest.getProduct();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exercise 8
     * Get Tenant
     * @throws Exception
     */
    public void assignTenant() throws Exception{

        System.out.println("\npublic void assignTenant() ");

        //create a new tenant resource
        TenantResource tenantResource = new TenantResource(getApiContext());

        //get an instance of your tenant (store)
        this.setTenant(tenantResource.getTenant(getApiContext().getTenantId()));

        System.out.println("Tenant ID: "+ getTenant().getId());
        System.out.println("Tenant Name: "+ getTenant().getName());
        System.out.println("Domain: "+ getTenant().getDomain());

        Iterator<MasterCatalog> masterCatalogs = getTenant().getMasterCatalogs().iterator();
        while(masterCatalogs.hasNext()){
            MasterCatalog masterCatalog = masterCatalogs.next();
            System.out.println("Master Catalog ID: "+ masterCatalog.getId());
            System.out.println("Master Catalog Name: "+ masterCatalog.getName());
        }
        Iterator<Site> sites = getTenant().getSites().iterator();
        while(sites.hasNext()){
            Site site = sites.next();
            System.out.println("Site ID: "+ site.getId());
            System.out.println("Site Name: "+ site.getName());

        }

    }

    /**
     * Exercise 9.1
     * INTERACTING WITH PRODUCT ATTRIBUTES
     * @throws Exception
     */
    public void getAttributes() throws Exception {

        System.out.println("\npublic void getAttributes() ");

        AttributeResource attributeResource = new AttributeResource(getApiContext());

        Iterator<Attribute> attributes = attributeResource.getAttributes(0, 200, "", "", "").getItems().iterator();

        Attribute testAttr = attributeResource.getAttribute("tenant~"+getTestAttrSearchFQN());

        Iterator<Attribute> attrOptions = attributeResource.getAttributes(null, null, "", "attributecode sw '" + getTestOptSearchFQN()+ "'", "").getItems().iterator();


        while(attributes.hasNext()){
            Attribute attribute = attributes.next();
            showAttributeData(attribute);
        }

        showAttributeData(testAttr);

        while(attrOptions.hasNext()){
            Attribute attrOption = attrOptions.next();
            showAttributeData(attrOption);
        }
    }

    protected void showAttributeData(Attribute attribute){

        System.out.println("\nprotected void showAttributeData(Attribute attribute)");

        String attrName = attribute.getAdminName();

        System.out.println(attrName +" datatype: " + attribute.getDataType());
        System.out.println(attrName +" inputtype: " + attribute.getInputType());
        System.out.println(attrName +" extra: " + attribute.getIsExtra());
        System.out.println(attrName +" option: " + attribute.getIsOption());
        System.out.println(attrName +" property: " + attribute.getIsProperty());
        System.out.println(attrName +" valuetype: "+ attribute.getValueType());
        for( int i = 0 ; attribute.getVocabularyValues()!=null && i < attribute.getVocabularyValues().size(); i++) {
            AttributeVocabularyValue attributeVocabularyValue = attribute.getVocabularyValues().get(i);
            if(attributeVocabularyValue!=null && attributeVocabularyValue.getContent()!=null) {
                System.out.println("Locale code: " + attributeVocabularyValue.getContent().getLocaleCode());
                System.out.println("String value: " + attributeVocabularyValue.getContent().getStringValue());
                System.out.println("Display Order: " + attributeVocabularyValue.getDisplayOrder());
            }
        }

    }



    /**
     * Exercise 9.2 Add Attribute
     * @throws Exception
     */
    public void addAttribute() throws Exception {

        System.out.println("\npublic void addAttribute()");

        AttributeResource attributeResource = new AttributeResource(getApiContext());

        // Exercise 9.2

        Attribute attribute = attributeResource.getAttribute("tenant~" + getSimpleAttributeName().toLowerCase());

        if (attribute == null) {
            System.out.println("Attribute "+ getSimpleAttributeName() +" doesn't exist, creating!");
            // define the attribute name
            attribute = new Attribute();

            AttributeLocalizedContent content = new AttributeLocalizedContent();
            content.setName(getSimpleAttributeName());

            // 9.2.2
            // add attribute values
            attribute.setAdminName(getSimpleAttributeName());
            attribute.setAttributeCode(getSimpleAttributeName().toLowerCase());
            attribute.setAttributeFQN("tenant~" + getSimpleAttributeName().toLowerCase());
            attribute.setInputType("TextBox");
            attribute.setDataType("String");
            attribute.setIsExtra(true);
            attribute.setIsOption(false);
            attribute.setIsProperty(false);
            attribute.setNamespace("Tenant");
            attribute.setContent(content);
            attribute.setValueType("ShopperEntered");

            try {
                // add new attribute
                Attribute createdAttribute = attributeResource.addAttribute(attribute);

                // update search options, update attributes, return back only the
                // attributeFQN and no the whole object
                Attribute updatedAttribute = attributeResource.updateAttribute(createdAttribute, createdAttribute.getAttributeFQN(), "AttributeFQN");

                System.out.println(updatedAttribute.getAttributeFQN());
            } catch (ApiException e) {
                System.err.println("ApiException: " + e.getMessage());
            }
        }else{
            System.out.println("Attribute "+ getSimpleAttributeName() +" exists!");

        }
        System.out.println("Attribute Fully Qualified Name: "+ attribute.getAttributeFQN());

    }

    /**
     * Lesson 9.3 add a new option attribute
     * @throws Exception
     */
    public void addOption() throws Exception{

        System.out.println("\npublic void addOption()");

        AttributeResource attributeResource = new AttributeResource(getApiContext());

        String optionAttrCode = getOptionAttributeName().toLowerCase().replace(" ","-");
        String optionAttrFQN = "tenant~"+optionAttrCode;
        String localeCode = "en-US";


        Attribute optionItemAttr = attributeResource.getAttribute(optionAttrFQN);

        if(optionItemAttr == null){
            optionItemAttr = new Attribute();
            optionItemAttr.setAdminName(getOptionAttributeName());
            optionItemAttr.setAttributeCode(optionAttrCode);
            optionItemAttr.setAttributeDataTypeSequence(5);
            optionItemAttr.setAttributeFQN(optionAttrFQN);
            optionItemAttr.setAttributeSequence(8);
            optionItemAttr.setDataType("String");
            optionItemAttr.setInputType("List");
            optionItemAttr.setIsExtra(false);
            optionItemAttr.setIsOption(true);
            optionItemAttr.setIsProperty(true);
            optionItemAttr.setMasterCatalogId(1);
            optionItemAttr.setNamespace("Tenant");
            optionItemAttr.setValueType("PreDefined");

            AttributeLocalizedContent optionContent = new AttributeLocalizedContent();
            optionContent.setName(getOptionAttributeName());
            optionContent.setDescription(getOptionAttributeName());
            optionContent.setLocaleCode(localeCode);
            optionItemAttr.setContent(optionContent);

            AttributeSearchSettings attributeSearchSettings = new AttributeSearchSettings();
            attributeSearchSettings.setAllowFilteringAndSortingInStorefront(true);
            attributeSearchSettings.setSearchableInAdmin(true);
            attributeSearchSettings.setSearchableInStorefront(true);
            attributeSearchSettings.setSearchDisplayValue(true);
            optionItemAttr.setSearchSettings(attributeSearchSettings);

            Iterator<String> optionItems = Arrays.asList(getOptionItemList()).iterator();
            List<AttributeVocabularyValue> vocabularyValues = new ArrayList<AttributeVocabularyValue>();

            for(int i = 0; optionItems.hasNext(); i++){
                String optionItem = optionItems.next();
                AttributeVocabularyValue attributeVocabularyValue = new AttributeVocabularyValue();
                attributeVocabularyValue.setDisplayOrder(i);
                attributeVocabularyValue.setValue(optionItem);
                attributeVocabularyValue.setValueSequence(i);

                AttributeVocabularyValueLocalizedContent content = new AttributeVocabularyValueLocalizedContent();
                content.setLocaleCode("en-US");
                content.setStringValue(optionItem);
                attributeVocabularyValue.setContent(content);

                vocabularyValues.add(attributeVocabularyValue);
            }

            optionItemAttr.setVocabularyValues(vocabularyValues);

            try{
                Attribute createdPurseAttribute = attributeResource.addAttribute(optionItemAttr);
                Attribute updatedPurseAttribute = attributeResource.updateAttribute(createdPurseAttribute, createdPurseAttribute.getAttributeFQN(), "AttributeFQN");
                System.out.println(updatedPurseAttribute.getAttributeFQN());
            } catch (ApiException e){
                System.err.println("ApiException: " + e.getMessage());
                e.printStackTrace();
                Attribute updatedPurseAttribute = attributeResource.updateAttribute(optionItemAttr, optionItemAttr.getAttributeFQN(), "AttributeFQN");
                System.out.println(updatedPurseAttribute.getAttributeFQN());
            }

        }

    }

    /**
     * Exercise 10.1 INTERACTING WITH PRODUCT TYPES
     * @throws Exception
     */
    public void getProductTypes() throws Exception{
        System.out.println("\nprivate void getProductTypes() ");

        // create a new productType resource
        ProductTypeResource productTypeResource = new ProductTypeResource(getApiContext());

        // get list of attributes with max page size and starting index at the beginning
        try {
            ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 200, "", "", "");
            // each product type
            productTypes.getItems().stream().forEach(
                    element -> System.out.println(element.getName())
            );

            ArrayList<AttributeInProductType> options = new ArrayList<>();

            // filter through product type resource and
            // loop through all options
            productTypes.getItems().stream().filter(
                    productType -> productType.getName().equals(getTestProductType())
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

    /**
     * Exercise 10.2
     * @throws Exception
     */
    public void updateProductType() throws Exception{

        System.out.println("\nprivate void updateProductType()");

        // create a new productType resource
        ProductTypeResource productTypeResource = new ProductTypeResource(getApiContext());

        // get list of attributes with max page size and starting index at the beginning
        ProductTypeCollection productTypes = productTypeResource.getProductTypes(0, 200, "", "", "");

        String testFNQ = getOptionAttributeName().toLowerCase().replace(" ","-");

        this.addEOPToProductType(productTypes, getTestProductType(), "tenant~"+testFNQ, productTypeResource, "extra");

    }

    /**
     * Exercise 10.2
     * @param productTypeCollection
     * @param prodTypeName
     * @param fqn
     * @param resource
     * @param choice
     * @throws Exception
     */
    private void addEOPToProductType(ProductTypeCollection productTypeCollection, String prodTypeName, String fqn, ProductTypeResource resource, String choice) throws Exception{

        System.out.println("private void addEOPToProductType(ProductTypeCollection productTypeCollection, String prodTypeName, String fqn, ProductTypeResource resource, String choice)");

        Attribute attr = this.getAttributeForProduct(fqn);

        List<AttributeInProductType> attributeInProductTypes = new ArrayList<>();

        attributeInProductTypes.add(this.createAttributeInProductType(attr));

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

    /**
     * Exercise 10.2
     * @param name
     * @return
     * @throws Exception
     */
    private Attribute getAttributeForProduct(String name) throws Exception{
        // create a new attribute resource
        AttributeResource attributeResource = new AttributeResource(getApiContext());

        // list of attributes
        AttributeCollection attributes = attributeResource.getAttributes(0, 200, "", "", "");

        return attributeResource.getAttribute(name);
    }

    /**
     * Exercise 10.2
     * @param attr
     * @return
     * @throws Exception
     */
    private AttributeInProductType createAttributeInProductType(Attribute attr) throws Exception{

        AttributeInProductType attributeInProductType = new AttributeInProductType();

        // add attribute to attribute in product type
        attributeInProductType.setAttributeFQN(attr.getAttributeFQN());

        return attributeInProductType;
    }

    /**
     * Exercise 11.1 INTERACTING WITH PRODUCTS
     * @throws Exception
     */
    private void getProducts() throws Exception{

        // create a new product resource
        ProductResource productResource = new ProductResource(getApiContext());

        // get product type filtered by name
        ProductCollection products = productResource.getProducts(0, 200, "", "", "", null, null, "");

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

        Product product = productResource.getProduct(getTestProductSearchCode());
        ProductPrice productPrice =  product.getPrice();
        if(productPrice!=null){
            if(productPrice.getPrice()!=null)
                System.out.println("getPrice:"+ productPrice.getPrice());
            if(productPrice.getIsoCurrencyCode()!=null)
                System.out.println("getIsoCurrencyCode:"+ productPrice.getIsoCurrencyCode());
            if(productPrice.getCreditValue()!=null)
                System.out.println("getCreditValue:"+ productPrice.getCreditValue());
            if(productPrice.getMap()!=null)
                System.out.println("getMap:"+ productPrice.getMap());
            if(productPrice.getMapEndDate()!=null)
                System.out.println("getMapEndDate:"+ productPrice.getMapEndDate());
            if(productPrice.getMapStartDate()!=null)
                System.out.println("getMapStartDate:"+ productPrice.getMapStartDate());
            if(productPrice.getMsrp()!=null)
                System.out.println("getMsrp:"+ productPrice.getMsrp());
            if(productPrice.getSalePrice()!=null)
                System.out.println("getSalePrice:"+ productPrice.getSalePrice());
        }

        Iterator<ProductOption> options=null;
        if(product.getOptions()!=null)
            options = product.getOptions().iterator();

        while(options!=null && options.hasNext()){
            ProductOption option = options.next();
            String attributeFQN = option.getAttributeFQN();
            System.out.println("attributeFQN: "+ attributeFQN);
            Iterator<ProductOptionValue> productOptionValueIterator = option.getValues().iterator();
            while(productOptionValueIterator.hasNext()) {
                ProductOptionValue productOptionValue = productOptionValueIterator.next();
                System.out.println(productOptionValue.getAttributeVocabularyValueDetail().getContent().getStringValue());
            }
        }

        ProductInventoryInfo productInventoryInfo = product.getInventoryInfo();
        if(productInventoryInfo!=null){
            System.out.println("Manage Stock: "+ productInventoryInfo.getManageStock());
            System.out.println("Out of Stock Behavior: "+ productInventoryInfo.getOutOfStockBehavior());
        }

        ProductVariationResource productVariationResource = new ProductVariationResource(getApiContext());
        LocationInventoryResource locationInventoryResource = new LocationInventoryResource(getApiContext());
        ProductVariationPagedCollection productVariationPagedCollection = productVariationResource.getProductVariations(getTestProductSearchCode());
        Iterator<ProductVariation> productVariationIterator = productVariationPagedCollection.getItems().iterator();
        while(productVariationIterator.hasNext()){
            ProductVariation productVariation = productVariationIterator.next();
            System.out.println(productVariation.getIsActive());

            LocationInventory locationInventory = locationInventoryResource.getLocationInventory(getLocationCode(),productVariation.getVariationProductCode());
            System.out.println("locationInventory.getBaseProductCode(): "+locationInventory.getBaseProductCode());
            System.out.println("locationInventory.getProductCode(): "+locationInventory.getProductCode());
            System.out.println("locationInventory.getStockOnHand(): "+locationInventory.getStockOnHand());

        }

    }

    /**
     * Exercise 11.2 CREATE A NEW PRODUCT
     * @throws Exception
     */
    private Product getProduct() throws Exception {
        String code = getProductCode();
        // create a new product resource
        ProductResource productResource = new ProductResource(getApiContext());

        if( productResource.getProduct(code) == null ) {
            createProduct(code);
        }

        return productResource.getProduct(code);
    }

    private void createProduct(String code) throws Exception{

        // create a new product resource
        ProductResource productResource = new ProductResource(getApiContext());

        Product product = new Product();

        // a.
        ProductLocalizedContent content = new ProductLocalizedContent();
        content.setProductName("Sport Handbag");

        // b.
        content.setLocaleCode("en-US");
        product.setContent(content);

        // c.
        List<String> fullfillmentTypes = new ArrayList<>();
        fullfillmentTypes.add("DirectShip");
        product.setFulfillmentTypesSupported(fullfillmentTypes);

        // d.
        product.setHasConfigurableOptions(true);

        // f.
        Measurement measurement = new Measurement();
        measurement.setValue(10.25);
        measurement.setUnit("in");
        product.setPackageLength(measurement);
        measurement.setValue(3.0);
        product.setPackageWidth(measurement);
        measurement.setValue(7.0);
        product.setPackageHeight(measurement);

        // g.
        ProductTypeResource productTypeResource = new ProductTypeResource(getApiContext());
        ProductTypeCollection productTypeCollection = productTypeResource.getProductTypes(0, 200, "", "", "");
        Optional<ProductType> productType =  productTypeCollection.getItems().stream().filter(pType -> pType.getName().equals("Purse")).findFirst();
        product.setProductTypeId(productType.isPresent() ? productType.get().getId() : null);
        if(product.getProductTypeId() != null) {
            // h.
            List<AttributeInProductType> extraPTypes = productType.isPresent() ? productType.get().getExtras() : null;
            Optional<AttributeInProductType> extra = extraPTypes.stream().filter(attributeInProductType -> attributeInProductType.getAttributeFQN().equals("tenant~monogram")).findFirst();
            ProductExtra productExtra = new ProductExtra();
            productExtra.setAttributeFQN(extra.isPresent() ? extra.get().getAttributeFQN() : null);
            List<ProductExtra> extras = new ArrayList<>();
            if (productExtra.getAttributeFQN() != null) {
                extras.add(productExtra);
                product.setExtras(extras);
            }

            // i.
            List<AttributeInProductType> optionPTypes = productType.isPresent() ? productType.get().getOptions() : null;
            Optional<AttributeInProductType> option = optionPTypes.stream().filter(attributeInProductType -> attributeInProductType.getAttributeFQN().equals("tenant~purse-size")).findFirst();
            ProductOption productOption = new ProductOption();
            productOption.setAttributeFQN(option.isPresent() ? option.get().getAttributeFQN() : null);
            if (productOption.getAttributeFQN() != null) {
                ProductOptionValue productOptionValue = new ProductOptionValue();
                List<ProductOptionValue> optionValues = new ArrayList<>();
                option.get().getVocabularyValues().forEach(attributeVocabularyValueInProductType -> {
                    String attribute = attributeVocabularyValueInProductType.getValue().toString();
                    if(attribute.equals("Petite") || attribute.equals("Classic")){
                        productOptionValue.setValue(attributeVocabularyValueInProductType.getValue());
                        optionValues.add(productOptionValue);
                    }
                });
                productOption.setValues(optionValues);
                List<ProductOption> options = new ArrayList<>();
                options.add(productOption);
                product.setOptions(options);
            }
        }

        // j.
        // TODO: Not sure what search settings are

        // k.
        CategoryResource categoryResource = new CategoryResource(getApiContext());
        CategoryPagedCollection categoryPagedCollection =  categoryResource.getCategories(0, 200, "", "", "");
        Optional<Category> category = categoryPagedCollection.getItems().stream().filter(cat -> cat.getContent().getName().equals("Bags")).findFirst();
        if(category.isPresent()) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategoryId(category.get().getId());
            ProductInCatalogInfo productInCatalogInfo = new ProductInCatalogInfo();
            List<ProductCategory> productCategories = new ArrayList<>();
            productCategories.add(productCategory);
            productInCatalogInfo.setProductCategories(productCategories);
            List<ProductInCatalogInfo> productInCatalogInfos = new ArrayList<>();
            productInCatalogInfos.add(productInCatalogInfo);
            product.setProductInCatalogs(productInCatalogInfos);
        }

        Product createdProduct = productResource.addProduct(product);

        productResource.updateProduct(createdProduct, createdProduct.getProductCode());

        addInventory(createdProduct);

    }

    /**
     * Exercise 11.3 ADD INVENTORY TO PRODUCT
     * @throws Exception
     */
    // TODO: Not sure I am doing this correctly.
    private void addInventory(Product product) throws Exception{
        LocationInventoryResource locationInventoryResource = new LocationInventoryResource(getApiContext());
        LocationInventoryAdjustment locationInventoryAdjustment = new LocationInventoryAdjustment();
        locationInventoryAdjustment.setLocationCode(getLocationCode());
        locationInventoryAdjustment.setProductCode(product.getProductCode());
        locationInventoryAdjustment.setType("Stock On Hand");
        locationInventoryAdjustment.setValue(10);
        List<LocationInventoryAdjustment> locationInventoryAdjustments = new ArrayList<>();
        locationInventoryAdjustments.add(locationInventoryAdjustment);
        locationInventoryResource.updateLocationInventory(locationInventoryAdjustments, getLocationCode());

    }

    /**
     * Exercise 13.1 GET CUSTOMER ACCOUNTS
     * @throws Exception
     */
    private void getCustomers() throws Exception {
        CustomerAccountResource customerAccountResource = new CustomerAccountResource(getApiContext());
        CustomerContactResource customerContactResource = new CustomerContactResource(getApiContext());
        CustomerAccountCollection customerAccountCollection = customerAccountResource.getAccounts(0,200, "", "", "", "", null, false, "");
        customerAccountCollection.getItems().forEach(customer -> {
            System.out.println("Customer Number: " + customer.getUserId());
            System.out.println("Customer Email Address: " + customer.getEmailAddress());
            System.out.println("Gift Card or Store Credits: " + customer.getCommerceSummary());
        });
    }

    /**
     * Exercise 13.2 CREATE A NEW CUSTOMER ACCOUNT
     * @throws Exception
     */
    private void createCustomer() throws Exception {
        CustomerAccountResource customerAccountResource = new CustomerAccountResource(getApiContext());
        CustomerAccount customerAccount = new CustomerAccount();
        CustomerContact customerContact = new CustomerContact();
        // a.
        customerAccount.setEmailAddress("example@kibo.com");
        // b.
        customerAccount.setFirstName("Taco");
        customerAccount.setLastName("Tuesday");
        // c - e.
        customerAccount.setIsActive(true);
        customerAccount.setIsAnonymous(false);
        customerAccount.setTaxExempt(false);
        // f.
        customerAccount.setUserName("example@kibo.com");
        // g.
        // TODO: no method to add password
        // h.
        Phone phone = new Phone();
        phone.setMobile("1234567890");
        customerContact.setPhoneNumbers(phone);
        Address address = new Address();
        address.setAddress1("1234 Harwood St.");
        address.setCityOrTown("Dallas");
        address.setStateOrProvince("Texas");
        address.setPostalOrZipCode("75201");
        customerContact.setAddress(address);
        // i.
        // TODO: no method for isImport

        List<CustomerContact> customerContacts = new ArrayList<>();
        customerContacts.add(customerContact);
        customerAccount.setContacts(customerContacts);
        CustomerAccount account = customerAccountResource.addAccount(customerAccount);
        customerAccountResource.updateAccount(account, account.getId());
    }

}
