package com.ctbcbank.navi.mid.campaign.management.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class RuleGrope {
    private String ruleGroupName;
    private List<String> couponTemplateNo;
    private LinkedList<RuleNode> root;
    private LinkedList<RuleGrope> children = new LinkedList<>();

    /**
     * Calculates the number of successful nodes.
     * A `ruleGroup` consists of multiple `RuleNode` objects.
     * The entire `ruleGroup` is considered successful only if
     * at least `countAmount` `RuleNode` objects are successful.
     */
    private long matchCount = 1;

    public RuleGrope(String ruleGroupName, List<String> couponTemplateNo, LinkedList<RuleNode> root){
        this.ruleGroupName = ruleGroupName;
        this.couponTemplateNo = couponTemplateNo;
        this.root = root;
    }


    public void addChild(RuleGrope... childNodes) {
        children.addAll(List.of(childNodes));
    }

    public void addChild(LinkedList<RuleGrope> childNodeList) {
        children.addAll(childNodeList);
    }
}
