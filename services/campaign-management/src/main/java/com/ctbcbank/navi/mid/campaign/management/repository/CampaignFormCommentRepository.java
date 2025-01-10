package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormCommentEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CampaignFormCommentRepository extends BaseRepository<CampaignFormCommentEntity, BigInteger> {

}
