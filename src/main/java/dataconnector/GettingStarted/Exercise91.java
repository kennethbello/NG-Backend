package dataconnector.GettingStarted;

import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.contracts.productadmin.*;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.security.AppAuthenticator;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Exercise91 {

    MozuApiContext apiContext = new MozuApiContext(18740, 29081);

    public void getAttributes() throws Exception {
        AttributeResource attributeResource = new AttributeResource(apiContext);
        AttributeCollection attributes = attributeResource.getAttributes(0, 200, "", "", "");

        Attribute attrRating = attributeResource.getAttribute("tenant~rating");

        System.out.println("attrRating datatype: " + attrRating.getDataType());
        System.out.println("attrRating inputtype: " + attrRating.getInputType());
        System.out.println("attrRating extra: " + attrRating.getIsExtra());
        System.out.println("attrRating option: " + attrRating.getIsOption());
        System.out.println("attrRating property: " + attrRating.getIsProperty());
        System.out.println(attrRating.getVocabularyValues());

        Attribute attrColor = attributeResource.getAttributes(null, null, "", "attributecode sw 'color'", "").getItems().get(0);
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

    public void addAttributes() throws Exception {
        AttributeResource attributeResource = new AttributeResource(apiContext);

        String attributeName = "Monogram";

        // define the attribute name
        Attribute attribute = new Attribute();

        AttributeLocalizedContent content = new AttributeLocalizedContent();
        content.setName(attributeName);

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

        // add new attribute
        Attribute createdAttribute = attributeResource.addAttribute(attribute);

        // update search options, update attributes, return back only the
        // attributeFQN and no the whole object
        Attribute updatedAttribute = attributeResource.updateAttribute(createdAttribute, createdAttribute.getAttributeFQN(), "AttributeFQN");

        System.out.println(updatedAttribute.getAttributeFQN());
    }

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

    public static void main(String[] args){
        AppAuthInfo appAuthInfo = new AppAuthInfo();

        /** replace with your authentication configuration */
        appAuthInfo.setApplicationId("KTrain.MDCID_K.1.0.0.Release");
        appAuthInfo.setSharedSecret("dea4fa3460d14e58a6fe2db124abc8b1");

        AppAuthenticator.initialize(appAuthInfo);

        Exercise91 exercise91 = new Exercise91();

        try {
//            exercise91.getAttributes();
//            exercise91.addAttributes();
            exercise91.getProductTypes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
