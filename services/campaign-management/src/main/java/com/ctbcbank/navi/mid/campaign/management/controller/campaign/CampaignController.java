package com.ctbcbank.navi.mid.campaign.management.controller.campaign;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.*;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRs;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.*;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CampaignCreateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CampaignCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CreateCampaignConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CreateCampaignService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform.CampaignCreateCouponRequestFormConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform.CampaignCreateCouponRequestFormRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform.CampaignCreateCouponRequestFormService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate.CampaignCreateCouponTemplateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate.CampaignCreateCouponTemplateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate.CampaignCreateCouponTemplateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate.CampaignCreateCouponTemplateService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdBoRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdBoRs;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno.CampaignQueryByCampaignNoConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno.CampaignQueryByCampaignNoRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno.CampaignQueryByCampaignNoRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno.CampaignQueryByCampaignNoService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform.CampaignQueryCouponRequestFormConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform.CampaignQueryCouponRequestFormRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform.CampaignQueryCouponRequestFormRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform.CampaignQueryCouponRequestFormService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate.CampaignQueryCouponTemplateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate.CampaignQueryCouponTemplateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate.CampaignQueryCouponTemplateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate.CampaignQueryCouponTemplateService;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateService;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.web.api.annotation.GetApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PutApiMapping;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/campaign/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignController {

    private final CampaignService campaignService;
    private final CreateCampaignService createCampaignService;
    private final CampaignQueryService campaignQueryService;
    private final CampaignQueryByCampaignIdService campaignQueryByCampaignIdService;
    private final CampaignUpdateService campaignUpdateService;
    private final CampaignCreateCouponTemplateService campaignCreateCouponTemplateService;
    private final CampaignQueryCouponTemplateService campaignQueryCouponTemplateService;
    private final CampaignQueryByCampaignNoService campaignQueryByCampaignNoService;
    private final CampaignCreateCouponRequestFormService campaignCreateCouponRequestFormService;
    private final CampaignQueryCouponRequestFormService campaignQueryCouponRequestFormService;
    private final CampaignAddParticipantListService campaignAddParticipantListService;

    @Operation(summary = "取得RuleEngine的Tree By 條件(轉帳時間、TransactionCode、IP_NO)", description = "取得RuleEngine的Tree By 條件(轉帳時間、TransactionCode、IP_NO)")
    @PostApiMapping(value = "query-rule-engine-tree/by-condition")
    QueryRuleEngineTreeByConditionResponse queryRuleEngineTreeByCondition(@Valid @RequestBody QueryRuleEngineTreeByConditionRequest queryRuleEngineTreeByConditionRequest) {
        Optional<QueryRuleEngineTreeByConditionRequestBo> queryRuleEngineTreeByConditionRequestBoOptional = QueryRuleEngineTreeByConditionRequest.parstToBo(queryRuleEngineTreeByConditionRequest);
        QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo = queryRuleEngineTreeByConditionRequestBoOptional.orElseThrow(
                () -> new NaviException(FabricResponseCode.INVALID_DATA));
        QueryRuleEngineTreeByConditionResponseBo queryRuleEngineTreeByConditionResponseBo = campaignService.queryRuleEngineTreeByCondition(queryRuleEngineTreeByConditionRequestBo);
        return QueryRuleEngineTreeByConditionResponse.parseToResponse(queryRuleEngineTreeByConditionResponseBo);
    }

    @Operation(summary = "新增 行銷活動", description = "新增 行銷活動")
    @PostApiMapping(value = "create")
    CampaignCreateRs create(@Valid @RequestBody CampaignCreateRq campaignCreateRq) {
        CampaignCreateRqBo campaignCreateRqBo = CreateCampaignConverter.parseToBo(campaignCreateRq);
        CampaignCreateRsBo campaignCreateRsBo = createCampaignService.create(campaignCreateRqBo);
        return CreateCampaignConverter.parseToRs(campaignCreateRsBo);
    }


    @Operation(summary = "查詢 行銷活動條件", description = "查詢 行銷活動條件")
    @PostApiMapping(value = "query-rule")
    QueryRuleRs queryRule(@Valid @RequestBody QueryRuleRq queryRuleRq) {
        QueryRuleRqBo queryRuleRqBo = QueryCampaignRuleConverter.parseToBo(queryRuleRq);
        QueryRuleRsBo queryRuleRsBo = campaignService.queryRule(queryRuleRqBo);
        return QueryCampaignRuleConverter.parseToRs(queryRuleRsBo);
    }

    @Operation(summary = "查詢 行銷活動", description = "查詢 行銷活動")
    @PostApiMapping(value = "query")
    CampaignQueryRs query(@Valid @RequestBody CampaignQueryRq campaignQueryRq) {
        CampaignQueryRqBo campaignQueryRqBo = CampaignQueryConverter.parseRqToRqBo(campaignQueryRq);
        CampaignQueryRsBo campaignQueryRsBo = campaignQueryService.query(campaignQueryRqBo);
        CampaignQueryRs campaignQueryRs = CampaignQueryConverter.parseRsBoToRs(campaignQueryRsBo);
        return campaignQueryRs;
    }

    @Operation(summary = "查詢 行銷活動 By Campaign ID", description = "查詢 行銷活動 By Campaign ID")
    @GetApiMapping("/query/by-campaign-id")
    CampaignQueryByCampaignIdRs queryByCampaignId(@Valid @ParameterObject CampaignQueryByCampaignIdRq campaignQueryByCampaignIdRq) {
        CampaignQueryByCampaignIdBoRq campaignQueryByCampaignIdBoRq = CampaignQueryByCampaignIdConverter.parseRqToBoRq(campaignQueryByCampaignIdRq);
        CampaignQueryByCampaignIdBoRs campaignQueryByCampaignIdBoRs = campaignQueryByCampaignIdService.queryByCampaignId(campaignQueryByCampaignIdBoRq);
        CampaignQueryByCampaignIdRs campaignQueryByCampaignIdRs = CampaignQueryByCampaignIdConverter.parseBoRsToRs(campaignQueryByCampaignIdBoRs);
        return campaignQueryByCampaignIdRs;
    }

    @Operation(summary = "更新行銷活動", description = "更新行銷活動")
    @PutApiMapping("update")
    CampaignUpdateRs update(@Valid @RequestBody CampaignUpdateRq campaignUpdateRq) {
        CampaignUpdateRqBo campaignUpdateRqBo = CampaignUpdateConverter.parseRqToRqBo(campaignUpdateRq);
        CampaignUpdateRsBo campaignUpdateRsBo = campaignUpdateService.update(campaignUpdateRqBo);
        CampaignUpdateRs campaignUpdateRs = CampaignUpdateConverter.parseRsBoToRs(campaignUpdateRsBo);
        return campaignUpdateRs;
    }

    @Operation(summary = "新增 活動-優惠券樣板", description = "新增 活動-優惠券樣板")
    @PostApiMapping(value = "create/coupon-template")
    CampaignCreateCouponTemplateRs createCouponTemplate(@Valid @RequestBody CampaignCreateCouponTemplateRq campaignCreateCouponTemplateRq) {
        CampaignCreateCouponTemplateRqBo campaignCreateCouponTemplateRqBo = CampaignCreateCouponTemplateConverter.parseRqToRqBo(campaignCreateCouponTemplateRq);
        CampaignCreateCouponTemplateRsBo campaignCreateCouponTemplateRsBo = campaignCreateCouponTemplateService.createCouponTemplate(campaignCreateCouponTemplateRqBo);
        CampaignCreateCouponTemplateRs campaignCreateCouponTemplateRs = CampaignCreateCouponTemplateConverter.parseRsBoToRs(campaignCreateCouponTemplateRsBo);
        return campaignCreateCouponTemplateRs;
    }

    @Operation(summary = "查詢 活動-優惠券樣板", description = "查詢 活動-優惠券樣板")
    @PostApiMapping(value = "query/coupon-template")
    CampaignQueryCouponTemplateRs queryCouponTemplate(@Valid @RequestBody CampaignQueryCouponTemplateRq campaignQueryCouponTemplateRq) {
        CampaignQueryCouponTemplateRqBo campaignQueryCouponTemplateRqBo = CampaignQueryCouponTemplateConverter.parseRqToRqBo(campaignQueryCouponTemplateRq);
        CampaignQueryCouponTemplateRsBo campaignQueryCouponTemplateRsBo = campaignQueryCouponTemplateService.queryCouponTemplate(campaignQueryCouponTemplateRqBo);
        CampaignQueryCouponTemplateRs campaignQueryCouponTemplateRs = CampaignQueryCouponTemplateConverter.parseRsBoToRs(campaignQueryCouponTemplateRsBo);
        return campaignQueryCouponTemplateRs;
    }

    @Operation(summary = "查詢 行銷活動 By Campaign NO", description = "查詢 行銷活動 By Campaign NO")
    @GetApiMapping("/query/by-campaign-no")
    CampaignQueryByCampaignNoRs queryByCampaignNo(@Valid @ParameterObject CampaignQueryByCampaignNoRq campaignQueryByCampaignNoRq) {
        CampaignQueryByCampaignNoRqBo campaignQueryByCampaignNoRqBo = CampaignQueryByCampaignNoConverter.parseRqToRqBo(campaignQueryByCampaignNoRq);
        CampaignQueryByCampaignNoRsBo campaignQueryByCampaignNoRsBo = campaignQueryByCampaignNoService.queryByCampaignNo(campaignQueryByCampaignNoRqBo);
        CampaignQueryByCampaignNoRs campaignQueryByCampaignNoRs = CampaignQueryByCampaignNoConverter.parseRsBoToRs(campaignQueryByCampaignNoRsBo);
        return campaignQueryByCampaignNoRs;
    }

    @Operation(summary = "新增 活動-優惠券申請單", description = "新增 活動-優惠券申請單")
    @PostApiMapping(value = "create/coupon-request-form")
    ApiResponsePayload createCouponRequestForm(@Valid @RequestBody CampaignCreateCouponRequestFormRq campaignCreateCouponRequestFormRq) {
        CampaignCreateCouponRequestFormRqBo campaignCreateCouponRequestFormRqBo = CampaignCreateCouponRequestFormConverter.parseRqToRqBo(campaignCreateCouponRequestFormRq);
        campaignCreateCouponRequestFormService.createCouponRequestForm(campaignCreateCouponRequestFormRqBo);
        return new ApiResponsePayload();
    }

    @Operation(summary = "查詢 活動-優惠券申請單", description = "查詢 活動-優惠券申請單")
    @PostApiMapping(value = "query/coupon-request-form")
    CampaignQueryCouponRequestFormRs queryCouponRequestForm(@Valid @RequestBody CampaignQueryCouponRequestFormRq campaignQueryCouponRequestFormRq) {
        CampaignQueryCouponRequestFormRqBo campaignQueryCouponRequestFormRqBo = CampaignQueryCouponRequestFormConverter.parseRqToRqBo(campaignQueryCouponRequestFormRq);
        CampaignQueryCouponRequestFormRsBo campaignQueryCouponRequestFormRsBo = campaignQueryCouponRequestFormService.queryCouponRequestForm(campaignQueryCouponRequestFormRqBo);
        CampaignQueryCouponRequestFormRs campaignQueryCouponRequestFormRs = CampaignQueryCouponRequestFormConverter.parseRsBoToRs(campaignQueryCouponRequestFormRsBo);
        return campaignQueryCouponRequestFormRs;
    }

    @Operation(summary = "新增參與名單", description = "新增參與名單")
    @PostApiMapping(value = "add/participant-list")
    CampaignAddParticipantListRs addParticipantList(@Valid @RequestBody CampaignAddParticipantListRq campaignAddParticipantListRq) {
        CampaignAddParticipantListRqBo campaignAddParticipantListRqBo = CampaignAddParticipantListConverter.parseRqToRqBo(campaignAddParticipantListRq);
        CampaignAddParticipantListRsBo campaignAddParticipantListRsBo = campaignAddParticipantListService.addParticipantList(campaignAddParticipantListRqBo);
        CampaignAddParticipantListRs campaignAddParticipantListRs = CampaignAddParticipantListConverter.parseRsBoToRs(campaignAddParticipantListRsBo);
        return campaignAddParticipantListRs;
    }

}
