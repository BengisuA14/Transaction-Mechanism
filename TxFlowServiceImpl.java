package org.onos.oneping.txflow;

import com.google.common.base.Stopwatch;
import org.apache.felix.scr.annotations.*;
import org.onosproject.net.flow.FlowRuleOperation;
import org.onosproject.net.flow.FlowRuleOperations;
import org.onosproject.net.flow.FlowRuleOperationsContext;
import org.onosproject.net.flow.FlowRuleService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;


import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;


@Component(immediate = true)
@Service
public class TxFlowServiceImpl implements TxFlowService {

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowRuleService flowRuleService;

    private final Logger log = getLogger(getClass());

    boolean rollb_started = false;


    Process applyprocess = Process.NOT_STARTED;
    Process rollbackprocess = Process.NOT_STARTED;

    HashSet<Long> failedFlows =  new HashSet<Long> ();
    HashMap<String, Process> result = new HashMap<>();

    CountDownLatch add_latch = new CountDownLatch(1);
    FlowRuleOperationsContext transactionContext_add = new FlowRuleOperationsContext() {
         Stopwatch timer_add = Stopwatch.createStarted();

        @Override
        public void onSuccess(FlowRuleOperations ops) {

            if(rollb_started){
                return;
            }

            long elapsed = timer_add.elapsed(TimeUnit.MILLISECONDS);
            log.info("Flow rules installed in "+elapsed+" ms.");
            log.info("ADD FLOWRULES - OnSuccess FLOWRULES: " + ops.stages());
            add_latch.countDown();
            applyprocess = Process.SUCCESS;
        };

        @Override
        public void onError(FlowRuleOperations ops) {

            if(rollb_started){
                return;
            }

            long elapsed = timer_add.elapsed(TimeUnit.MILLISECONDS);
            log.info("Flow rules installation error. Elapsed time : "+elapsed+" ms.");
            System.out.println("Flow rules installation error. Elapsed time : "+elapsed+" ms.");
            if(ops!=null && !ops.stages().isEmpty()){
                for(FlowRuleOperation oper : ops.stages().get(0)){
                    failedFlows.add(oper.rule().id().value());
                }
            }
            log.info("ADD FLOWRULES - onError FLOWRULES: " + ops.stages());
            add_latch.countDown();
            applyprocess = Process.ERROR;
        }
    };


    CountDownLatch remove_latch = new CountDownLatch(1);
    FlowRuleOperationsContext transactionContext_remove = new FlowRuleOperationsContext() {
        Stopwatch timer_remove = Stopwatch.createStarted();

        @Override
        public void onSuccess(FlowRuleOperations ops) {

            log.info("REMOVE FLOWRULES - OnSuccess FLOWRULES: " + ops.stages());
            long elapsed = timer_remove.elapsed(TimeUnit.MILLISECONDS);
            log.info("Remove operation installed (SUCCESS) in "+elapsed+" ms.");

            remove_latch.countDown();
            rollbackprocess = Process.SUCCESS;

        };

        @Override
        public void onError(FlowRuleOperations ops) {

            log.info("REMOVE FLOWRULES - onError FLOWRULES: " + ops.stages());
            if(ops!=null && !ops.stages().isEmpty()){
                for(FlowRuleOperation oper : ops.stages().get(0)){
                    if(failedFlows.contains(oper.rule().id().value())){
                        continue;
                    }else {
                        long elapsed = timer_remove.elapsed(TimeUnit.MILLISECONDS);
                        log.info("Remove operation error. Elapsed time : "+elapsed+" ms.");
                        System.out.println("Error while removing flowrules. Elapsed time : "+elapsed+" ms.");
                        remove_latch.countDown();
                        rollbackprocess = Process.ERROR;
                        return;
                    }
                }
            }
            log.info("REMOVE FLOWRULES - Success FLOWRULES: " + ops.stages());
            long elapsedre = timer_remove.elapsed(TimeUnit.MILLISECONDS);
            log.info("Remove operation installed (ERROR) in "+elapsedre+" ms.");
            remove_latch.countDown();
            rollbackprocess = Process.SUCCESS;

        }
    };


    @Activate
    public void activate(ComponentContext context) {
        log.warn("TxFlowService Started");
    }

    @Deactivate
    public void deactivate() {
        log.warn("TxFlowService Stopped");
    }

    @Override
    public HashMap<String, Process> applyFlowTx(FlowRuleOperations.Builder applyOperations, FlowRuleOperations.Builder rollbackOperations, long applyTimeout, long rollbackTimeout) {

        flowRuleService.apply(applyOperations.build(transactionContext_add));
        try {
            add_latch.await(applyTimeout, TimeUnit.MILLISECONDS);

            if(applyprocess == Process.NOT_STARTED){
                applyprocess = Process.TIMEOUT;
                rollb_started = true;
            }

        } catch (InterruptedException e) {
            log.error("",e);
        }

        log.info("applyprocess: "+ applyprocess);

        if(applyprocess == Process.ERROR){
            log.info("Flowrule is onError...");
        }else if(applyprocess == Process.TIMEOUT) {
            log.info("Timeout during apply operation ...");
        }

        if (applyprocess != Process.SUCCESS) {
            log.info("Removing flows ...");


            flowRuleService.apply(rollbackOperations.build(transactionContext_remove));

            try {
                remove_latch.await(rollbackTimeout, TimeUnit.MILLISECONDS);
                if(rollbackprocess == Process.NOT_STARTED){
                    rollbackprocess = Process.TIMEOUT;
                }
            } catch (InterruptedException e) {
                log.error("",e);
            }
        }
        log.info("rollbackprocess: "+ rollbackprocess);

        result.put("Apply_process",applyprocess);
        result.put("Rollback_process", rollbackprocess);

        return result;
    }
}
