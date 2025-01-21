package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.QueryCampaignCustomerListDetailConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignCustomerListDetailEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignCustomerListDetailRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignCustomerListDetailDao {
    private final String CLASS_NAME = CampaignCustomerListDetailDao.class.getSimpleName();
    private final CampaignCustomerListDetailRepository campaignCustomerListDetailRepository;
    private final JdbcTemplate jdbcTemplate;
    private final int QUERY_MAX_SIZE = 1000;


    @Transactional(readOnly = true)
    public List<CampaignCustomerListDetailDto> queryCampaignCustomerListDetail(QueryCampaignCustomerListDetailConditionDto queryCampaignCustomerListDetailConditionDto) {
        List<CampaignCustomerListDetailDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignCustomerListDetailConditionDto) || StringUtils.isBlank(queryCampaignCustomerListDetailConditionDto.getCustomerListNo())) {
            return reDataList;
        }

        List<CampaignCustomerListDetailEntity> campaignCustomerListDetailEntities = campaignCustomerListDetailRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignCustomerListDetailConditionDto.getCustomerListNo())) {
                predicates.add(builder.equal(root.get("customerListNo"), queryCampaignCustomerListDetailConditionDto.getCustomerListNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignCustomerListDetailEntities).forEach(entity -> {
            CampaignCustomerListDetailDto dto = new CampaignCustomerListDetailDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public Page<CampaignCustomerListDetailDto> queryCampaignCustomerListDetailByCustomerListNo(String customerListNo, int page, int size, String sortDirection) {
        // 檢查是否有客戶名單編號、當前頁數、每頁幾筆
        if (StringUtils.isBlank(customerListNo) || ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(size)) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "customerListNo && page && size should not be null.");
        }

        // 檢查每頁幾筆不能大於1000筆
        if (size > QUERY_MAX_SIZE) {
            size = QUERY_MAX_SIZE;
        }

        Specification<CampaignCustomerListDetailEntity> specification = (root, query, criteriaBuilder) -> {
            if (customerListNo != null) {
                return criteriaBuilder.equal(root.get("customerListNo"), customerListNo);
            }
            return null;
        };

        Sort sort = Sort.by("id");
        if (StringUtils.isNotBlank(sortDirection)) {
            sort = StringUtils.equalsAnyIgnoreCase("desc") ? sort.descending() : sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CampaignCustomerListDetailEntity> entityPage = campaignCustomerListDetailRepository.findAll(specification, pageable);
        return entityPage.map(this::convertToDto);
    }

    private CampaignCustomerListDetailDto convertToDto(CampaignCustomerListDetailEntity entity) {
        CampaignCustomerListDetailDto campaignCustomerListDetailDto = new CampaignCustomerListDetailDto();
        campaignCustomerListDetailDto.setCustomerListNo(entity.getCustomerListNo());
        campaignCustomerListDetailDto.setStatus(entity.getStatus());
        campaignCustomerListDetailDto.setIdNo(entity.getIdNo());
        campaignCustomerListDetailDto.setName(entity.getName());
        campaignCustomerListDetailDto.setIpNoList(entity.getIpNoList());
        campaignCustomerListDetailDto.setMessage(entity.getMessage());
        campaignCustomerListDetailDto.setIsSingleIpNo(entity.getIsSingleIpNo());
        campaignCustomerListDetailDto.setChosenIpNo(entity.getChosenIpNo());
        campaignCustomerListDetailDto.setId(entity.getId());
        campaignCustomerListDetailDto.setCreateDttm(entity.getCreateDttm());
        campaignCustomerListDetailDto.setUpdateDttm(entity.getUpdateDttm());
        return campaignCustomerListDetailDto;
    }

    public CampaignCustomerListDetailDto saveCampaignCustomerListDetail(CampaignCustomerListDetailDto campaignCustomerListDetailDto) {
        CampaignCustomerListDetailDto reCampaignCustomerListDetailDto = new CampaignCustomerListDetailDto();
        CampaignCustomerListDetailEntity campaignCustomerListDetailEntity = new CampaignCustomerListDetailEntity();
        BeanUtils.copyProperties(campaignCustomerListDetailDto, campaignCustomerListDetailEntity);
        campaignCustomerListDetailRepository.save(campaignCustomerListDetailEntity);
        BeanUtils.copyProperties(campaignCustomerListDetailEntity, reCampaignCustomerListDetailDto);
        return reCampaignCustomerListDetailDto;
    }

    public void saveCampaignCustomerListDetail(List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList, int batchSize) {
        if (CollectionUtils.isEmpty(campaignCustomerListDetailDtoList)) {
            return;
        }
        String sql = """
                INSERT INTO TB_CAMPAIGN_CUSTOMER_LIST_DETAIL 
                ( ID
                , CREATE_DTTM
                , UPDATE_DTTM
                , ID_NO
                , NAME
                , IP_NO_LIST
                , IS_SINGLE_IP_NO
                , STATUS
                , MESSAGE
                , CUSTOMER_LIST_NO
                , CHOSEN_IP_NO
                )
                VALUES
                ( SEQ_CAMPAIGN_CUSTOMER_LIST_DETAIL.NEXTVAL
                , SYSTIMESTAMP
                , SYSTIMESTAMP
                , ?
                , ?
                , ?
                , ?
                , ?
                , ?
                , ?
                , ?
                )
                """;

        jdbcTemplate.batchUpdate(sql, campaignCustomerListDetailDtoList, batchSize, (ps, argument) -> {
            ps.setString(1, argument.getIdNo());
            ps.setString(2, argument.getName());
            ps.setString(3, argument.getIpNoList().toString());
            ps.setBoolean(4, argument.getIsSingleIpNo());
            ps.setString(5, argument.getStatus().toString());
            ps.setString(6, argument.getMessage());
            ps.setString(7, argument.getCustomerListNo());
            ps.setLong(8, argument.getChosenIpNo().longValue());
        });
    }


}
