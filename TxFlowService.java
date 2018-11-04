package org.onos.oneping.txflow;


import org.onosproject.net.flow.FlowRuleOperations;

import java.util.HashMap;

public interface TxFlowService {

    enum Process{
        NOT_STARTED,
        SUCCESS,
        TIMEOUT,
        ERROR
    }

    HashMap<String, Process> applyFlowTx(FlowRuleOperations.Builder applyOperations, FlowRuleOperations.Builder rollbackOperations, long applyTimeout, long rollbackTimeout);

}
