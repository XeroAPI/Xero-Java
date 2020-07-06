package com.xero.api.client;


import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.models.assets.*;



import org.threeten.bp.*;

import java.util.UUID;

import java.util.List;


public class AssetsApiTest {

  ApiClient defaultClient;
  AssetApi assetApi;
  String accessToken;
  String xeroTenantId;

  private static boolean setUpIsDone = false;

  @Before
  public void setUp() {

    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init clienthttps://xero-bank-feeds.getsandbox.com:443/bankfeeds.xro/1.0
    defaultClient =
        new ApiClient(
            "https://xero-assets.getsandbox.com:443/assets.xro/1.0", null, null, null, null);
    assetApi = AssetApi.getInstance(defaultClient);

    // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
    if (setUpIsDone) {
      return;
    }

    try {
      System.out.println("Sleep for 60 seconds");
      Thread.sleep(60);
    } catch (InterruptedException e) {
      System.out.println(e);
    }

    // do the setup
    setUpIsDone = true;
  }

  public void tearDown() {
    assetApi = null;
    defaultClient = null;
  }

  @Test
  public void testCreateAsset() throws Exception {
    System.out.println("@Test - createAsset");

    Asset newAsset = new Asset();
    try {
      Asset response = assetApi.createAsset(accessToken, xeroTenantId, newAsset);
      assertThat(
          response.getAssetId().toString(), (equalTo("2257c64a-77ca-444c-a5ea-fa9a588c7039")));
      assertThat(response.getAssetName().toString(), (equalTo("Computer74863")));
      assertThat(response.getAssetNumber().toString(), (equalTo("123477544")));
      assertThat(response.getPurchaseDate(), is(equalTo(LocalDate.of(2020, 01, 01))));
      assertThat(response.getPurchasePrice().toString(), is(equalTo("100.0")));
      assertThat(response.getDisposalPrice().toString(), is(equalTo("23.23")));
      assertThat(response.getAssetStatus(), is(equalTo(com.xero.models.assets.AssetStatus.DRAFT)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum
                      .STRAIGHTLINE)));
      assertThat(
          response.getBookDepreciationSetting().getAveragingMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum.ACTUALDAYS)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationRate().toString(),
          is(equalTo("0.5")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationCalculationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum
                      .NONE)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectId().toString(),
          (equalTo("2257c64a-77ca-444c-a5ea-fa9a588c7039")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectType().toString(),
          is(equalTo("Asset")));
      assertThat(
          response.getBookDepreciationSetting().getBookEffectiveDateOfChangeId().toString(),
          (equalTo("b58a2ace-1213-4681-9f11-2e30f57b5b8c")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentCapitalGain().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentGainLoss().toString(), is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getDepreciationStartDate(),
          is(equalTo(LocalDate.of(2020, 01, 02))));
      assertThat(
          response.getBookDepreciationDetail().getPriorAccumDepreciationAmount().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentAccumDepreciationAmount().toString(),
          is(equalTo("0.0")));
      assertThat(response.getCanRollback(), is(equalTo(true)));
      assertThat(response.getAccountingBookValue().toString(), is(equalTo("76.77")));
      assertThat(response.getIsDeleteEnabledForDate(), is(equalTo(true)));
      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }

  @Test
  public void testGetAssets() throws Exception {
    System.out.println("@Test - getAssets");

    try {

      Integer page = null;
      Integer pageSize = null;
      String orderBy = null;
      String sortDirection = null;
      String filterBy = null;
      Assets response =
          assetApi.getAssets(
              accessToken,
              xeroTenantId,
              com.xero.models.assets.AssetStatusQueryParam.DRAFT,
              page,
              pageSize,
              orderBy,
              sortDirection,
              filterBy);

      assertThat(
          response.getItems().get(0).getAssetId().toString(),
          (equalTo("68f17094-af97-4f1b-b36b-013b45b6ad3c")));
      assertThat(response.getItems().get(0).getAssetName().toString(), (equalTo("Computer47822")));
      assertThat(response.getItems().get(0).getAssetNumber().toString(), (equalTo("123478074")));
      assertThat(
          response.getItems().get(0).getPurchaseDate(), is(equalTo(LocalDate.of(2020, 01, 01))));
      assertThat(response.getItems().get(0).getPurchasePrice().toString(), is(equalTo("100.0")));
      assertThat(response.getItems().get(0).getDisposalPrice().toString(), is(equalTo("0.0")));
      assertThat(
          response.getItems().get(0).getAssetStatus(),
          is(equalTo(com.xero.models.assets.AssetStatus.DRAFT)));
      assertThat(
          response.getItems().get(0).getBookDepreciationSetting().getDepreciationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum
                      .STRAIGHTLINE)));
      assertThat(
          response.getItems().get(0).getBookDepreciationSetting().getAveragingMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum.ACTUALDAYS)));
      assertThat(
          response.getItems().get(0).getBookDepreciationSetting().getDepreciationRate().toString(),
          is(equalTo("0.5")));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationSetting()
              .getDepreciationCalculationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum
                      .NONE)));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationSetting()
              .getDepreciableObjectId()
              .toString(),
          (equalTo("68f17094-af97-4f1b-b36b-013b45b6ad3c")));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationSetting()
              .getDepreciableObjectType()
              .toString(),
          is(equalTo("Asset")));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationSetting()
              .getBookEffectiveDateOfChangeId()
              .toString(),
          (equalTo("5da77739-7f22-4109-b0a0-67480fb89af0")));
      assertThat(
          response.getItems().get(0).getBookDepreciationDetail().getCurrentCapitalGain().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getItems().get(0).getBookDepreciationDetail().getCurrentGainLoss().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getItems().get(0).getBookDepreciationDetail().getDepreciationStartDate(),
          is(equalTo(LocalDate.of(2020, 01, 02))));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationDetail()
              .getPriorAccumDepreciationAmount()
              .toString(),
          is(equalTo("0.0")));
      assertThat(
          response
              .getItems()
              .get(0)
              .getBookDepreciationDetail()
              .getCurrentAccumDepreciationAmount()
              .toString(),
          is(equalTo("0.0")));
      assertThat(response.getItems().get(0).getCanRollback(), is(equalTo(true)));
      assertThat(
          response.getItems().get(0).getAccountingBookValue().toString(), is(equalTo("100.0")));
      assertThat(response.getItems().get(0).getIsDeleteEnabledForDate(), is(equalTo(false)));

      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }

  @Test
  public void testGetAsset() throws Exception {
    System.out.println("@Test - getAsset");

    try {
      UUID assetId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
      Asset response = assetApi.getAssetById(accessToken, xeroTenantId, assetId);

      assertThat(
          response.getAssetId().toString(), (equalTo("68f17094-af97-4f1b-b36b-013b45b6ad3c")));
      assertThat(response.getAssetName().toString(), (equalTo("Computer47822")));
      assertThat(response.getAssetNumber().toString(), (equalTo("123478074")));
      assertThat(response.getPurchaseDate(), is(equalTo(LocalDate.of(2020, 01, 01))));
      assertThat(response.getPurchasePrice().toString(), is(equalTo("100.0")));
      assertThat(response.getDisposalPrice().toString(), is(equalTo("23.0")));
      assertThat(response.getAssetStatus(), is(equalTo(com.xero.models.assets.AssetStatus.DRAFT)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum
                      .STRAIGHTLINE)));
      assertThat(
          response.getBookDepreciationSetting().getAveragingMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum.ACTUALDAYS)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationRate().toString(),
          is(equalTo("0.5")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationCalculationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum
                      .NONE)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectId().toString(),
          (equalTo("68f17094-af97-4f1b-b36b-013b45b6ad3c")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectType().toString(),
          is(equalTo("Asset")));
      assertThat(
          response.getBookDepreciationSetting().getBookEffectiveDateOfChangeId().toString(),
          (equalTo("5da77739-7f22-4109-b0a0-67480fb89af0")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentCapitalGain().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentGainLoss().toString(), is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getDepreciationStartDate(),
          is(equalTo(LocalDate.of(2020, 01, 02))));
      assertThat(
          response.getBookDepreciationDetail().getPriorAccumDepreciationAmount().toString(),
          is(equalTo("0.0")));
      assertThat(
          response.getBookDepreciationDetail().getCurrentAccumDepreciationAmount().toString(),
          is(equalTo("0.0")));
      assertThat(response.getCanRollback(), is(equalTo(true)));
      assertThat(response.getAccountingBookValue().toString(), is(equalTo("77.0")));
      assertThat(response.getIsDeleteEnabledForDate(), is(equalTo(true)));

      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }

  @Test
  public void testCreateAssetType() throws Exception {
    System.out.println("@Test - createAssetType");

    AssetType newAssetType = new AssetType();
    try {
      AssetType response = assetApi.createAssetType(accessToken, xeroTenantId, newAssetType);

      assertThat(
          response.getAssetTypeId().toString(), (equalTo("85509b5d-308e-420d-9532-b85105058916")));
      assertThat(response.getAssetTypeName().toString(), (equalTo("Machinery11004")));
      assertThat(
          response.getFixedAssetAccountId().toString(),
          (equalTo("3d8d063a-c148-4bb8-8b3c-a5e2ad3b1e82")));
      assertThat(
          response.getDepreciationExpenseAccountId().toString(),
          (equalTo("d1602f69-f900-4616-8d34-90af393fa368")));
      assertThat(
          response.getAccumulatedDepreciationAccountId().toString(),
          (equalTo("9195cadd-8645-41e6-9f67-7bcd421defe8")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum
                      .DIMINISHINGVALUE100)));
      assertThat(
          response.getBookDepreciationSetting().getAveragingMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum.ACTUALDAYS)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationRate().toString(),
          is(equalTo("0.05")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciationCalculationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum
                      .NONE)));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectId().toString(),
          (equalTo("00000000-0000-0000-0000-000000000000")));
      assertThat(
          response.getBookDepreciationSetting().getDepreciableObjectType().toString(),
          is(equalTo("None")));
      assertThat(response.getLocks().toString(), (equalTo("0")));
      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }

  @Test
  public void testGetAssetSettings() throws Exception {
    System.out.println("@Test - getAssetSettingss");

    try {
      Setting response = assetApi.getAssetSettings(accessToken, xeroTenantId);
      assertThat(response.getAssetNumberPrefix().toString(), is(equalTo("FA-")));
      assertThat(response.getAssetNumberSequence().toString(), (equalTo("0007")));
      assertThat(response.getAssetStartDate(), is(equalTo(LocalDate.of(2016, 01, 01))));
      assertThat(response.getOptInForTax(), is(equalTo(false)));
      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }

  @Test
  public void testGetAssetTypes() throws Exception {
    System.out.println("@Test - getAssetTypes");

    try {
      List<AssetType> response = assetApi.getAssetTypes(accessToken, xeroTenantId);

      assertThat(
          response.get(0).getAssetTypeId().toString(),
          (equalTo("710255c6-d2ed-4463-b992-06c8685add5e")));
      assertThat(response.get(0).getAssetTypeName().toString(), (equalTo("Computer Equipment")));
      assertThat(
          response.get(0).getFixedAssetAccountId().toString(),
          (equalTo("37c35de7-8df0-44bf-8e7c-f1f67cf6a278")));
      assertThat(
          response.get(0).getDepreciationExpenseAccountId().toString(),
          (equalTo("0fbd1820-9dd0-454a-9515-6ec076a84cf7")));
      assertThat(
          response.get(0).getAccumulatedDepreciationAccountId().toString(),
          (equalTo("512eac06-6894-47cd-b421-673b4ca2693a")));
      assertThat(
          response.get(0).getBookDepreciationSetting().getDepreciationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum
                      .STRAIGHTLINE)));
      assertThat(
          response.get(0).getBookDepreciationSetting().getAveragingMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum.FULLMONTH)));
      assertThat(
          response.get(0).getBookDepreciationSetting().getDepreciationRate().toString(),
          is(equalTo("25.0")));
      assertThat(
          response.get(0).getBookDepreciationSetting().getDepreciationCalculationMethod(),
          is(
              equalTo(
                  com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum
                      .NONE)));
      assertThat(
          response.get(0).getBookDepreciationSetting().getDepreciableObjectId().toString(),
          (equalTo("710255c6-d2ed-4463-b992-06c8685add5e")));
      assertThat(
          response.get(0).getBookDepreciationSetting().getDepreciableObjectType().toString(),
          is(equalTo("AssetType")));
      assertThat(
          response.get(0).getBookDepreciationSetting().getBookEffectiveDateOfChangeId().toString(),
          (equalTo("39b9c2e9-62b1-4efc-ab75-fa9152ffaa5f")));
      assertThat(response.get(0).getLocks().toString(), (equalTo("0")));

      // System.out.println(response.toString());
    } catch (XeroApiException xe) {
      System.out.println(xe.toString());
    }
  }
}
