package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleGroupDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleSettingDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.*;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupCouponRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupRepository;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleSettingRepository;
import com.ctbcbank.navi.mid.campaign.management.utils.SqlUtils;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleGrope;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleNode;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignDao {
    private final String CLASS_NAME = CampaignDao.class.getSimpleName();
    private final CampaignRepository campaignRepository;
    private final CampaignRuleSettingRepository campaignRuleSettingRepository;
    private final CampaignRuleGroupRepository campaignRuleGroupRepository;
    private final CampaignRuleGroupCouponRepository campaignRuleGroupCouponRepository;
    private final EntityManagerFactory entityManagerFactory;

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

            if (!CollectionUtils.isEmpty(queryCampaignConditionDto.getIdList())) {
                predicates.add(builder.in(root.get("id")).value(queryCampaignConditionDto.getIdList()));
            }

            if (StringUtils.isNotBlank(queryCampaignConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignConditionDto.getCampaignNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignConditionDto.getCampaignNameLike())) {
                predicates.add(builder.like(root.get("name"), "%" + queryCampaignConditionDto.getCampaignNameLike() + "%"));
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
                    RuleNameEnum ruleNameEnum = RuleNameEnum.valueOf(campaignRuleSettingEntity.getRuleName());
                    boolean isSync = campaignEntity.getIsImmediate();
                    RuleNode ruleNode = new RuleNode(
                            campaignRuleSettingEntity.getId(), campaignRuleSettingEntity.getFieldName(), isSync, ruleNameEnum, null,
                            RuleTypeEnum.valueOf(campaignRuleSettingEntity.getRuleType()),
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

    @Transactional(readOnly = true)
    public Page<BigInteger> queryCampaignByRule(List<BigInteger> campaignIdList, List<CampaignRuleSettingDto> campaignRuleSettingDtoList, int page, int size) {
        Map<String, Object> sqlParams = new HashMap<>();
        StringBuilder dataSql = new StringBuilder("""
                SELECT DISTINCT(CAMPAIGN_ID) FROM 
                (
                """);
        // 查TB_CAMPAIGN_RULE_SETTING 加上條件
        StringBuilder ruleSettingSql = new StringBuilder("""
                SELECT CAMPAIGN_ID
                 FROM TB_CAMPAIGN_RULE_SETTING
                 WHERE 1 = 1 
                """);
        StringBuilder ruleSettingConditionSql = generateRuleSettingConditionSql("ruleSetting", campaignRuleSettingDtoList, sqlParams);
        if (!ruleSettingConditionSql.isEmpty()) {
            ruleSettingSql.append(" " + ruleSettingConditionSql);
        }

        dataSql.append(ruleSettingSql);
        // UNION 會自動去除重複值
        // UNION ALL 不會自動去除重複值
        dataSql.append("""
                \n
                 UNION
                """);

        // 查TB_CAMPAIGN_RULE_SETTING_EXTRA 加上條件
        StringBuilder ruleSettingExtraSql = new StringBuilder("""
                 \n
                 SELECT CAMPAIGN_ID
                 FROM TB_CAMPAIGN_RULE_SETTING_EXTRA
                 WHERE 1 = 1
                """);
        StringBuilder ruleSettingExtraConditionSql = generateRuleSettingConditionSql("ruleSettingExtra", campaignRuleSettingDtoList, sqlParams);
        if (!ruleSettingExtraConditionSql.isEmpty()) {
            ruleSettingExtraSql.append(" " + ruleSettingExtraConditionSql);
        }
        dataSql.append(ruleSettingExtraSql);

        dataSql.append("""
                \n
                 ) WHERE 1 = 1
                """);
        if (!CollectionUtils.isEmpty(campaignIdList)) {
            dataSql.append(" AND CAMPAIGN_ID IN :campaignIdList");
            sqlParams.put("campaignIdList", campaignIdList);
        }
        dataSql.append("""
                 \n
                 ORDER BY CAMPAIGN_ID DESC
                """);

        StringBuilder countSql = new StringBuilder("""
                        SELECT COUNT(*) 
                        FROM (
                """);
        countSql.append(dataSql);
        countSql.append(" )");

        log.info("[{}][queryCampaignByRule][dataSql: {}]", CLASS_NAME, dataSql);
        log.info("[{}][queryCampaignByRule][countSql: {}]", CLASS_NAME, countSql);
        log.info("[{}][queryCampaignByRule][sqlParams: {}]", CLASS_NAME, sqlParams);

        Pageable pageable = PageRequest.of(page, size);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), BigInteger.class);
            sqlParams.forEach(dataQuery::setParameter);
            dataQuery.setFirstResult((int) pageable.getOffset());
            dataQuery.setMaxResults(pageable.getPageSize());
            List<BigInteger> campaignIdListResult = dataQuery.getResultList();

            Query countQuery = entityManager.createNativeQuery(countSql.toString(), BigInteger.class);
            sqlParams.forEach(countQuery::setParameter);
            Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
            return new PageImpl<>(campaignIdListResult, pageable, totalCount);
        }
    }

    /**
     * 產出規則設定條件SQL
     *
     * @param prefixKey
     * @param campaignRuleSettingDtoList
     * @param sqlParams
     * @return
     */
    private StringBuilder generateRuleSettingConditionSql(String prefixKey, List<CampaignRuleSettingDto> campaignRuleSettingDtoList, Map<String, Object> sqlParams) {
        StringBuilder ruleSettingSql = new StringBuilder();
        for (int i = 0; i < campaignRuleSettingDtoList.size(); ++i) {
            CampaignRuleSettingDto campaignRuleSettingDto = campaignRuleSettingDtoList.get(0);
            StringBuilder sql = new StringBuilder();
            String ruleSettingTransactionCodeKey = prefixKey + "TransactionCode" + i;
            sql.append(" TRANSACTION_CODE = :" + ruleSettingTransactionCodeKey);
            sqlParams.put(ruleSettingTransactionCodeKey, campaignRuleSettingDto.getTransactionCode());

            if (StringUtils.isNotBlank(campaignRuleSettingDto.getRuleName())) {
                String ruleSettingRuleNameKey = prefixKey + "RuleNameKey" + i;
                sql.append(" AND RULE_NAME = :" + ruleSettingRuleNameKey);
                sqlParams.put(ruleSettingRuleNameKey, campaignRuleSettingDto.getRuleName());
            }

            if (StringUtils.isNotBlank(campaignRuleSettingDto.getRuleType())) {
                String ruleSettingRuleTypeKey = prefixKey + "RuleTypeKey" + i;
                sql.append(" AND RULE_TYPE = :" + ruleSettingRuleTypeKey);
                sqlParams.put(ruleSettingRuleTypeKey, campaignRuleSettingDto.getRuleType());
            }

            if (StringUtils.isNotBlank(campaignRuleSettingDto.getRuleValue())) {
                String ruleSettingRuleValueKey = prefixKey + "RuleValueKey" + i;
                sql.append(" AND RULE_VALUE = :" + ruleSettingRuleValueKey);
                sqlParams.put(ruleSettingRuleValueKey, campaignRuleSettingDto.getRuleValue());
            }

            ruleSettingSql.append(" AND (");
            ruleSettingSql.append(sql);
            ruleSettingSql.append(" )");
        }
        return ruleSettingSql;
    }

    @Transactional(readOnly = true)
    public Page<BigInteger> queryCampaignByRules(boolean isSearchCampaignIdList, List<BigInteger> campaignIdList, List<CampaignRuleSettingDto> campaignRuleSettingDtoList, int page, int size) {
        try {
            List<CampaignRuleSettingEntity> campaignRuleSettingEntityList = generateCampaignRuleSettingEntityList(campaignRuleSettingDtoList);
            List<CampaignRuleSettingExtraEntity> campaignRuleSettingExtraEntityList = generateCampaignRuleSettingExtraEntityList(campaignRuleSettingDtoList);

            Map<String, Object> sqlParams = new HashMap<>();
            StringBuilder dataSql = new StringBuilder("""
                    SELECT CAMPAIGN_ID FROM 
                    (
                     \n
                    """);

            List<String> selectFields = Stream.of("CAMPAIGN_ID").collect(Collectors.toList());
            StringBuilder ruleSettingSB = SqlUtils.composeSql(campaignRuleSettingEntityList, selectFields, "TB_CAMPAIGN_RULE_SETTING", "ruleSetting", sqlParams);
            dataSql.append(ruleSettingSB);

            // UNION 會自動去除重複值
            // UNION ALL 不會自動去除重複值
            dataSql.append("""
                     \n
                     UNION 
                     \n
                    """);

            StringBuilder ruleSettingExtraSB = SqlUtils.composeSql(campaignRuleSettingExtraEntityList, selectFields, "TB_CAMPAIGN_RULE_SETTING_EXTRA", "ruleSettingExtra", sqlParams);
            dataSql.append(ruleSettingExtraSB);

            dataSql.append("""
                     \n
                     )
                    """);

            if (isSearchCampaignIdList) {
                dataSql.append(" WHERE CAMPAIGN_ID IN :campaignIdList");
                sqlParams.put("campaignIdList", campaignIdList);
            }

            dataSql.append("""
                     \n
                     ORDER BY CAMPAIGN_ID DESC
                    """);

            // 計算總數量的SQL
            StringBuilder countSql = new StringBuilder("""
                            SELECT COUNT(*) 
                            FROM (
                    """);
            countSql.append(dataSql);
            countSql.append(" )");

            log.info("[{}][queryCampaignByRule][dataSql: {}]", CLASS_NAME, dataSql);
            log.info("[{}][queryCampaignByRule][countSql: {}]", CLASS_NAME, countSql);
            log.info("[{}][queryCampaignByRule][sqlParams: {}]", CLASS_NAME, sqlParams);

            Pageable pageable = PageRequest.of(page, size);
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), BigInteger.class);
                sqlParams.forEach(dataQuery::setParameter);
                dataQuery.setFirstResult((int) pageable.getOffset());
                dataQuery.setMaxResults(pageable.getPageSize());
                List<BigInteger> campaignIdListResult = dataQuery.getResultList();

                Query countQuery = entityManager.createNativeQuery(countSql.toString(), BigInteger.class);
                sqlParams.forEach(countQuery::setParameter);
                Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
                return new PageImpl<>(campaignIdListResult, pageable, totalCount);
            }
        } catch (Exception ex) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, ex.getMessage());
        }

    }

    public List<CampaignRuleSettingEntity> generateCampaignRuleSettingEntityList(List<CampaignRuleSettingDto> campaignRuleSettingDtoList) {
        List<CampaignRuleSettingEntity> campaignRuleSettingEntityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignRuleSettingDtoList)) {
            return campaignRuleSettingEntityList;
        }
        campaignRuleSettingEntityList = campaignRuleSettingDtoList.stream().map(x -> {
            CampaignRuleSettingEntity campaignRuleSettingEntity = new CampaignRuleSettingEntity();
            campaignRuleSettingEntity.setTransactionCode(x.getTransactionCode());
            campaignRuleSettingEntity.setRuleName(x.getRuleName());
            campaignRuleSettingEntity.setRuleType(x.getRuleType());
            campaignRuleSettingEntity.setRuleValue(x.getRuleValue());
            return campaignRuleSettingEntity;
        }).toList();
        return campaignRuleSettingEntityList;
    }

    public List<CampaignRuleSettingExtraEntity> generateCampaignRuleSettingExtraEntityList(List<CampaignRuleSettingDto> campaignRuleSettingDtoList) {
        List<CampaignRuleSettingExtraEntity> campaignRuleSettingExtraEntityList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignRuleSettingDtoList)) {
            return campaignRuleSettingExtraEntityList;
        }
        campaignRuleSettingExtraEntityList = campaignRuleSettingDtoList.stream().map(x -> {
            CampaignRuleSettingExtraEntity campaignRuleSettingExtraEntity = new CampaignRuleSettingExtraEntity();
            campaignRuleSettingExtraEntity.setTransactionCode(x.getTransactionCode());
            campaignRuleSettingExtraEntity.setRuleName(x.getRuleName());
            campaignRuleSettingExtraEntity.setRuleType(x.getRuleType());
            campaignRuleSettingExtraEntity.setRuleValue(x.getRuleValue());
            return campaignRuleSettingExtraEntity;
        }).toList();
        return campaignRuleSettingExtraEntityList;
    }


}
