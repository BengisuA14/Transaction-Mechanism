package org.onos.oneping.txflow;

import org.onosproject.core.ApplicationId;
import org.onosproject.net.DeviceId;
import org.onosproject.net.flow.*;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class FlowRuleServiceMock implements FlowRuleService {

    private final Logger log = getLogger(getClass());

    enum Bev{
        SUCCESS,
        ERROR,
        NOTSTARTED
    }

    protected int apply_count = 0;

    Bev apply_bev;
    Bev rollback_bev;

    public void setRollback_bev(Bev rollback_bev) {
        this.rollback_bev = rollback_bev;
    }

    public void setApply_bev(Bev apply_bev) {
        this.apply_bev = apply_bev;
    }

    public Bev getApply_bev() {
        return apply_bev;
    }

    public Bev getRollback_bev() {
        return rollback_bev;
    }

    public int getApply_count() {
        return apply_count;
    }

    @Override
    public int getFlowRuleCount() {
        return 0;
    }

    @Override
    public Iterable<FlowEntry> getFlowEntries(DeviceId deviceId) {
        return null;
    }

    @Override
    public void applyFlowRules(FlowRule... flowRules) {

    }

    @Override
    public void purgeFlowRules(DeviceId deviceId) {

    }

    @Override
    public void removeFlowRules(FlowRule... flowRules) {

    }

    @Override
    public void removeFlowRulesById(ApplicationId applicationId) {

    }

    @Override
    public Iterable<FlowRule> getFlowRulesById(ApplicationId applicationId) {
        return null;
    }

    @Override
    public Iterable<FlowEntry> getFlowEntriesById(ApplicationId applicationId) {
        return null;
    }

    @Override
    public Iterable<FlowRule> getFlowRulesByGroupId(ApplicationId applicationId, short i) {
        return null;
    }

    @Override
    public void apply(FlowRuleOperations flowRuleOperations) {
        // Open new thred
        // Thred,n ,c,nde sthred sleep (x saniye)
        // call success/error

        Thread one = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(apply_count == 0 && apply_bev == Bev.SUCCESS){
                    flowRuleOperations.callback().onSuccess(flowRuleOperations);
                    apply_count++;
                }
                else if(apply_count == 0 && apply_bev == Bev.ERROR){
                    flowRuleOperations.callback().onError(flowRuleOperations);
                    apply_count++;
                }

                else if(apply_count == 1 && apply_bev == Bev.SUCCESS){
                    flowRuleOperations.callback().onSuccess(flowRuleOperations);
                }
                else if(apply_count == 1 && apply_bev == Bev.ERROR){
                    flowRuleOperations.callback().onError(flowRuleOperations);
                }
            }
        };
        one.start();

    }

    @Override
    public Iterable<TableStatisticsEntry> getFlowTableStatistics(DeviceId deviceId) {
        return null;
    }

    @Override
    public void addListener(FlowRuleListener flowRuleListener) {

    }

    @Override
    public void removeListener(FlowRuleListener flowRuleListener) {

    }
}
