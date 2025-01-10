package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleGroupDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignEntity;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupCouponEntity;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupEntity;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleSettingEntity;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupCouponRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleSettingRepository;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleGrope;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleNode;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignDao {
    private final String CLASS_NAME = CampaignDao.class.getSimpleName();
    private final CampaignRepository campaignRepository;
    private final CampaignRuleSettingRepository campaignRuleSettingRepository;
    private final CampaignRuleGroupRepository campaignRuleGroupRepository;
    private final CampaignRuleGroupCouponRepository campaignRuleGroupCouponRepository;

    @Transactional(readOnly = true)
    public List<CampaignDto> queryCampaign(QueryCampaignConditionDto queryCampaignConditionDto) {
        List<CampaignDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignConditionDto)) {
            return reDataList;
        }
        List<CampaignEntity> campaignEntities = campaignRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getId())) {
                predicates.add(builder.equal(root.get("id"), queryCampaignConditionDto.getId()));
            }

            if (StringUtils.isNotBlank(queryCampaignConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignConditionDto.getCampaignNo()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getCampaignDateTime())) {
                predicates.add(builder.lessThan(root.get("startDateTime"), queryCampaignConditionDto.getCampaignDateTime()));
                predicates.add(builder.greaterThanOrEqualTo(root.get("endDateTime"), queryCampaignConditionDto.getCampaignDateTime()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignConditionDto.getCategoryList())) {
                predicates.add(builder.in(root.get("category")).value(queryCampaignConditionDto.getCategoryList()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getEndStartDateTime())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("endDateTime"), queryCampaignConditionDto.getEndStartDateTime()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getEndEndDateTime())) {
                predicates.add(builder.lessThan(root.get("endDateTime"), queryCampaignConditionDto.getEndEndDateTime()));
                predicates.add(builder.greaterThanOrEqualTo(root.get("endDateTime"), queryCampaignConditionDto.getEndStartDateTime()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getIsImmediate())) {
                predicates.add(builder.equal(root.get("isImmediate"), queryCampaignConditionDto.getIsImmediate()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignConditionDto.getIsListing())) {
                predicates.add(builder.equal(root.get("isListing"), queryCampaignConditionDto.getIsListing()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignConditionDto.getListingList())) {
                predicates.add(builder.in(root.get("isListing")).value(queryCampaignConditionDto.getListingList()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignConditionDto.getParticipantTypeList())) {
                predicates.add(builder.in(root.get("participantType")).value(queryCampaignConditionDto.getParticipantTypeList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignEntities).forEach(entity -> {
            CampaignDto dto = new CampaignDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional(readOnly = true)
    public RuleEngine getRuleEngineByCampaignId(BigInteger campaignId) {
        RuleEngine ruleEngine = null;
        Optional<CampaignEntity> campaignEntityOptional = campaignRepository.findById(campaignId);
        if (!campaignEntityOptional.isPresent()) {
            log.error("[{}][getRuleEngineByCampaignId][campaignRepository not fund: {}]", CLASS_NAME, campaignId);
            return ruleEngine;
        }
        CampaignEntity campaignEntity = campaignEntityOptional.get();
        Map<BigInteger, RuleGrope> ruleGropeMap = new HashMap<>();
        Map<BigInteger, LinkedList<RuleGrope>> ruleGropeParentIdMap = new HashMap<>();
        AtomicReference<RuleGrope> root = new AtomicReference<>();
        try {
            List<CampaignRuleGroupEntity> campaignRuleGroupEntityList = campaignRuleGroupRepository.findByCampaignIdOrderById(campaignId);
            campaignRuleGroupEntityList.forEach(campaignRuleGroupEntity -> {
                RuleGrope ruleGrope = new RuleGrope();
                //each ruleGrope
                List<CampaignRuleSettingEntity> ruleSettingEntityList = campaignRuleSettingRepository.findByRuleGroupId(campaignRuleGroupEntity.getId());
                LinkedList<RuleNode> ruleNodeList = new LinkedList<>();
                ruleSettingEntityList.forEach(campaignRuleSettingEntity -> {
                    //each ruleNode
                    Optional<RuleNameEnum> ruleNameEnumOptional = RuleNameEnum.fromCode(campaignRuleSettingEntity.getRuleName());
                    boolean isSync = campaignEntity.getIsImmediate();
                    RuleNode ruleNode = new RuleNode(
                            campaignRuleSettingEntity.getId(), campaignRuleSettingEntity.getFieldName(), isSync, ruleNameEnumOptional.get(), null,
                            RuleTypeEnum.fromCode(campaignRuleSettingEntity.getRuleType()),
                            campaignRuleSettingEntity.getRuleValue()
                    );
                    ruleNodeList.addLast(ruleNode);
                });

                BigInteger ruleGroupParentId = campaignRuleGroupEntity.getRuleGroupParentId();
                ruleGrope.setMatchCount(campaignRuleGroupEntity.getMatchCount());
                ruleGrope.setRoot(ruleNodeList);

                if (campaignRuleGroupEntity.getHasCoupon().equals("Y")) {
                    List<String> couponTemplateNos = new ArrayList<>();
                    List<CampaignRuleGroupCouponEntity> campaignRuleGroupCouponEntities = campaignRuleGroupCouponRepository.findByRuleGroupId(campaignRuleGroupEntity.getId());
                    if (!CollectionUtils.isEmpty(campaignRuleGroupCouponEntities)) {
                        couponTemplateNos = campaignRuleGroupCouponEntities.stream().map(CampaignRuleGroupCouponEntity::getCouponTemplateNo).toList();
                    }
                    ruleGrope.setCouponTemplateNo(couponTemplateNos);
                } else {
                    ruleGrope.setCouponTemplateNo(null);
                }

                ruleGropeMap.put(campaignRuleGroupEntity.getId(), ruleGrope);

                if (ruleGroupParentId == null) {
                    root.set(ruleGrope);
                } else {
                    if (ruleGropeParentIdMap.containsKey(ruleGroupParentId)) {
                        ruleGropeParentIdMap.get(ruleGroupParentId).add(ruleGrope);
                    } else {
                        LinkedList<RuleGrope> ruleGropeList = new LinkedList<>();
                        ruleGropeList.add(ruleGrope);
                        ruleGropeParentIdMap.put(ruleGroupParentId, ruleGropeList);
                    }
                }
            });

            ruleGropeParentIdMap.forEach((parentId, ruleGropeList) -> {
                ruleGropeMap.get(parentId).addChild(ruleGropeList);
            });

            ruleEngine = new RuleEngine();
            ruleEngine.setCampaignId(campaignId);
            ruleEngine.setRoot(root.get());
            ruleEngine.setStartTime(campaignEntity.getStartDateTime());
            ruleEngine.setEndTime(campaignEntity.getEndDateTime());
        } catch (Exception ex) {
            log.error("[{}][getRuleEngineByCampaignId][Error: {}]", CLASS_NAME, ex);
            return ruleEngine;
        }
        return ruleEngine;
    }

    public CampaignDto saveCampaign(CampaignDto campaignDto) {
        CampaignDto reCampaignDto = new CampaignDto();
        CampaignEntity campaignEntity = new CampaignEntity();
        BeanUtils.copyProperties(campaignDto, campaignEntity);
        campaignRepository.save(campaignEntity);
        BeanUtils.copyProperties(campaignEntity, reCampaignDto);
        return reCampaignDto;
    }

    public CampaignRuleGroupDto saveCampaignRuleGroup(CampaignRuleGroupDto campaignRuleGroupDto) {
        CampaignRuleGroupDto reCampaignRuleGroupDto = new CampaignRuleGroupDto();
        CampaignRuleGroupEntity campaignRuleGroupEntity = new CampaignRuleGroupEntity();
        BeanUtils.copyProperties(campaignRuleGroupDto, campaignRuleGroupEntity);
        campaignRuleGroupRepository.save(campaignRuleGroupEntity);
        BeanUtils.copyProperties(campaignRuleGroupEntity, reCampaignRuleGroupDto);
        return reCampaignRuleGroupDto;
    }


}
