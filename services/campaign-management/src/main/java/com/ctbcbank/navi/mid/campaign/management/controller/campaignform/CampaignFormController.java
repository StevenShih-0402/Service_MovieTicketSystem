package com.ctbcbank.navi.mid.campaign.management.controller.campaignform;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcomment.CampaignFormCreateCommentRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcomment.CampaignFormCreateCommentRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query.CampaignFormQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query.CampaignFormQueryRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querybycampaignformno.CampaignFormQueryByFormNoRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querybycampaignformno.CampaignFormQueryByFormNoRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.review.CampaignFormReviewRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.updatereviewstatus.CampaignFormUpdateReviewStatusRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.updatereviewstatus.CampaignFormUpdateReviewStatusRs;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment.CampaignFormCreateCommentConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment.CampaignFormCreateCommentRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment.CampaignFormCreateCommentRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment.CampaignFormCreateCommentService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform.CampaignFormCreateCouponTemplateFormConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform.CampaignFormCreateCouponTemplateFormService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.query.CampaignFormQueryConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.query.CampaignFormQueryRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.query.CampaignFormQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.query.CampaignFormQueryService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno.CampaignFormQueryByFormNoConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno.CampaignFormQueryByFormNoRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno.CampaignFormQueryByFormNoRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno.CampaignFormQueryByFormNoService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewServiceFactory;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus.CampaignFormUpdateReviewStatusConverter;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus.CampaignFormUpdateReviewStatusRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus.CampaignFormUpdateReviewStatusRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus.CampaignFormUpdateReviewStatusService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/campaign-form/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignFormController {
    private final CampaignFormCreateService campaignFormCreateService;
    private final CampaignFormUpdateService campaignFormUpdateService;
    private final CampaignFormQueryService campaignFormQueryService;
    private final CampaignFormQueryByFormNoService campaignFormQueryByFormNoService;
    private final CampaignFormQueryCouponTemplateFormService campaignFormQueryCouponTemplateFormService;
    private final CampaignFormCreateCouponTemplateFormService campaignFormCreateCouponTemplateFormService;
    private final CampaignFormUpdateReviewStatusService campaignFormUpdateReviewStatusService;
    private final CampaignFormCreateCommentService campaignFormCreateCommentService;
    private final CampaignFormReviewServiceFactory campaignFormReviewServiceFactory;

    @Operation(summary = "新增 行銷活動申請單", description = "新增 行銷活動申請單")
    @PostApiMapping(value = "create")
    CampaignFormCreateRs create(@Valid @RequestBody CampaignFormCreateRq campaignFormCreateRq) {
        CampaignFormCreateRqBo campaignFormCreateRqBo = CampaignFormCreateConverter.parseRqToRqBo(campaignFormCreateRq);
        CampaignFormCreateRsBo campaignFormCreateRsBo = campaignFormCreateService.create(campaignFormCreateRqBo);
        CampaignFormCreateRs campaignFormCreateRs = CampaignFormCreateConverter.parseRsBoToRs(campaignFormCreateRsBo);
        return campaignFormCreateRs;
    }

    @Operation(summary = "更新 行銷活動申請單", description = "更新 行銷活動申請單")
    @PutApiMapping(value = "update")
    CampaignFormUpdateRs update(@Valid @RequestBody CampaignFormUpdateRq campaignFormUpdateRq) {
        CampaignFormUpdateRqBo campaignFormUpdateRqBo = CampaignFormUpdateConverter.parseRqToRqBo(campaignFormUpdateRq);
        CampaignFormUpdateRsBo campaignFormUpdateRsBo = campaignFormUpdateService.update(campaignFormUpdateRqBo);
        CampaignFormUpdateRs campaignFormUpdateRs = CampaignFormUpdateConverter.parseRsBoToRs(campaignFormUpdateRsBo);
        return campaignFormUpdateRs;
    }

    @Operation(summary = "查詢 行銷活動申請單清單", description = "查詢 行銷活動申請單清單")
    @PostApiMapping(value = "query")
    CampaignFormQueryRs query(@Valid @RequestBody CampaignFormQueryRq campaignFormQueryRq) {
        CampaignFormQueryRqBo campaignFormQueryRqBo = CampaignFormQueryConverter.parseRqToRqBo(campaignFormQueryRq);
        CampaignFormQueryRsBo campaignFormQueryRsBo = campaignFormQueryService.query(campaignFormQueryRqBo);
        CampaignFormQueryRs campaignFormQueryRs = CampaignFormQueryConverter.parseRsBoToRs(campaignFormQueryRsBo);
        return campaignFormQueryRs;
    }

    @Operation(summary = "查詢 行銷活動申請單 By 編號", description = "查詢 行銷活動申請單 By 編號")
    @GetApiMapping(value = "query/by-campaign-form-no")
    CampaignFormQueryByFormNoRs queryByCampaignFormNo(@Valid @ParameterObject CampaignFormQueryByFormNoRq campaignQueryByFormNoRq) {
        CampaignFormQueryByFormNoRqBo campaignFormQueryByFormNoRqBo = CampaignFormQueryByFormNoConverter.parseRqToRqBo(campaignQueryByFormNoRq);
        CampaignFormQueryByFormNoRsBo campaignQueryByFormNoRsBo = campaignFormQueryByFormNoService.queryByCampaignFormNo(campaignFormQueryByFormNoRqBo);
        CampaignFormQueryByFormNoRs campaignQueryByFormNoRs = CampaignFormQueryByFormNoConverter.parseRsBoToRs(campaignQueryByFormNoRsBo);
        return campaignQueryByFormNoRs;
    }

    @Operation(summary = "查詢 行銷活動表單-優惠券樣板表單 清單", description = "查詢 行銷活動表單-優惠券樣板表單 清單")
    @PostApiMapping(value = "query/coupon-template-form")
    CampaignFormQueryCouponTemplateFormRs queryCouponTemplateForm(@Valid @RequestBody CampaignFormQueryCouponTemplateFormRq campaignFormQueryCouponTemplateFormRq) {
        CampaignFormQueryCouponTemplateFormRqBo campaignFormQueryCouponTemplateFormRqBo = CampaignFormQueryCouponTemplateFormConverter.parseRqToRqBo(campaignFormQueryCouponTemplateFormRq);
        CampaignFormQueryCouponTemplateFormRsBo campaignFormQueryCouponTemplateFormRsBo = campaignFormQueryCouponTemplateFormService.queryCouponTemplateForm(campaignFormQueryCouponTemplateFormRqBo);
        CampaignFormQueryCouponTemplateFormRs campaignFormQueryCouponTemplateFormRs = CampaignFormQueryCouponTemplateFormConverter.parseRsBoToRs(campaignFormQueryCouponTemplateFormRsBo);
        return campaignFormQueryCouponTemplateFormRs;
    }

    @Operation(summary = "新增 行銷活動表單-優惠券樣板表單", description = "查詢 行銷活動表單-優惠券樣板表單")
    @PostApiMapping(value = "create/coupon-template-form")
    CampaignFormCreateCouponTemplateFormRs createCouponTemplateForm(@Valid @RequestBody CampaignFormCreateCouponTemplateFormRq campaignFormCreateCouponTemplateFormRq) {
        CampaignFormCreateCouponTemplateFormRqBo campaignFormCreateCouponTemplateFormRqBo = CampaignFormCreateCouponTemplateFormConverter.parseRqToRqBo(campaignFormCreateCouponTemplateFormRq);
        CampaignFormCreateCouponTemplateFormRsBo campaignFormCreateCouponTemplateFormRsBo = campaignFormCreateCouponTemplateFormService.createCouponTemplateForm(
                campaignFormCreateCouponTemplateFormRqBo);
        CampaignFormCreateCouponTemplateFormRs campaignFormCreateCouponTemplateFormRs = CampaignFormCreateCouponTemplateFormConverter.parseRsBoToRs(campaignFormCreateCouponTemplateFormRsBo);
        return campaignFormCreateCouponTemplateFormRs;
    }

    @Operation(summary = "更新 行銷活動申請單狀態", description = "更新 行銷活動申請單狀態")
    @PutApiMapping(value = "update/review-status")
    CampaignFormUpdateReviewStatusRs updateReviewStatus(@Valid @RequestBody CampaignFormUpdateReviewStatusRq campaignFormUpdateReviewStatusRq) {
        CampaignFormUpdateReviewStatusRqBo campaignFormUpdateReviewStatusRqBo = CampaignFormUpdateReviewStatusConverter.parseRqToRqBo(campaignFormUpdateReviewStatusRq);
        CampaignFormUpdateReviewStatusRsBo campaignFormUpdateReviewStatusRsBo = campaignFormUpdateReviewStatusService.updateReviewStatus(campaignFormUpdateReviewStatusRqBo);
        CampaignFormUpdateReviewStatusRs campaignFormUpdateReviewStatusRs = CampaignFormUpdateReviewStatusConverter.parseRsBoToRs(campaignFormUpdateReviewStatusRsBo);
        return campaignFormUpdateReviewStatusRs;
    }

    @Operation(summary = "新增 行銷活動申請單備註", description = "新增 行銷活動申請單備註")
    @PostApiMapping(value = "create/comment")
    CampaignFormCreateCommentRs createComment(@Valid @RequestBody CampaignFormCreateCommentRq campaignFormCreateCommentRq) {
        CampaignFormCreateCommentRqBo campaignFormCreateCommentRqBo = CampaignFormCreateCommentConverter.parseRqToRqBo(campaignFormCreateCommentRq);
        CampaignFormCreateCommentRsBo campaignFormCreateCommentRsBo = campaignFormCreateCommentService.createComment(campaignFormCreateCommentRqBo);
        CampaignFormCreateCommentRs campaignFormCreateCommentRs = CampaignFormCreateCommentConverter.parseRsBoToRs(campaignFormCreateCommentRsBo);
        return campaignFormCreateCommentRs;
    }

    @Operation(summary = "審核 行銷活動申請單", description = "審核 行銷活動申請單")
    @PostApiMapping(value = "review")
    ApiResponsePayload review(@Valid @RequestBody CampaignFormReviewRq campaignFormReviewRq) {
        CampaignFormReviewRqBo campaignFormReviewRqBo = CampaignFormReviewConverter.parseRqToRqBo(campaignFormReviewRq);
        CampaignFormReviewService campaignFormReviewService = campaignFormReviewServiceFactory.getCampaignFormReviewService(campaignFormReviewRqBo);
        campaignFormReviewService.review(campaignFormReviewRqBo);
        return new ApiResponsePayload();
    }


}
