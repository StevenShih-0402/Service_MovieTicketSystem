package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignCustomerListDetailEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CampaignCustomerListDetailRepository extends BaseRepository<CampaignCustomerListDetailEntity, BigInteger>, JpaSpecificationExecutor<CampaignCustomerListDetailEntity> {

}
