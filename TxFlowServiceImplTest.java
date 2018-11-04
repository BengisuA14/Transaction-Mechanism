package org.onos.oneping.txflow;

import org.junit.Assert;
import org.junit.Before;
import org.onlab.packet.Ethernet;
import org.onlab.packet.IPv4;
import org.onos.oneping.txflow.FlowRuleServiceMock.Bev;
import org.onos.oneping.txflow.TxFlowService.Process;
import org.onosproject.net.DeviceId;
import org.onosproject.net.flow.*;
import org.slf4j.Logger;

import java.util.HashMap;

import static junit.framework.TestCase.fail;
import static org.slf4j.LoggerFactory.getLogger;


public class TxFlowServiceImplTest {
    private final Logger log = getLogger(getClass());

    TxFlowServiceImpl txFlowServiceImpl = new TxFlowServiceImpl();
    FlowRuleServiceMock flowRuleServiceMock = new FlowRuleServiceMock();

    @Before
    public void setUp(){
        txFlowServiceImpl.flowRuleService = flowRuleServiceMock;
    }

    @org.junit.Test
    public void applyFlowTx_SUCCESS() {

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.SUCCESS);
        exp_res.put("Rollback_process", Process.NOT_STARTED);


        flowRuleServiceMock.setApply_bev(Bev.SUCCESS);
        flowRuleServiceMock.setRollback_bev(Bev.NOTSTARTED);
        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 10000, 10000));
    }

    @org.junit.Test
    public void applyFlowTx_SUCCESS2() {

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");
        DeviceId dev3 = DeviceId.deviceId("of:0000000000000013");
        DeviceId dev4 = DeviceId.deviceId("of:0000000000000014");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();
        FlowRule.Builder f3 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev3).withSelector(ts)
                .withTreatment(drop).makePermanent();
        FlowRule.Builder f4 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev4).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        addRules.add(f3.build());
        addRules.add(f4.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.SUCCESS);
        exp_res.put("Rollback_process", Process.NOT_STARTED);

        flowRuleServiceMock.setApply_bev(Bev.SUCCESS);
        flowRuleServiceMock.setRollback_bev(Bev.NOTSTARTED);
        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 10000, 10000));
    }

    @org.junit.Test
    public void applyFlowTx_SUCCESS3() {

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");
        DeviceId dev3 = DeviceId.deviceId("of:0000000000000013");
        DeviceId dev4 = DeviceId.deviceId("of:0000000000000014");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();
        FlowRule.Builder f3 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev3).withSelector(ts)
                .withTreatment(drop).makePermanent();
        FlowRule.Builder f4 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev4).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        addRules.add(f3.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());
        removeRules.remove(f4.build());

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.SUCCESS);
        exp_res.put("Rollback_process", Process.NOT_STARTED);


         flowRuleServiceMock.setApply_bev(Bev.SUCCESS);
        flowRuleServiceMock.setRollback_bev(Bev.NOTSTARTED);
        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 10000, 10000));
    }


    @org.junit.Test
    public void applyFlowTx_TIMEOUT_BOTH1() {

        //timeout for both flowrules true

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        flowRuleServiceMock.setApply_bev(Bev.SUCCESS);
        flowRuleServiceMock.setRollback_bev(Bev.SUCCESS);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.TIMEOUT);
        exp_res.put("Rollback_process", Process.TIMEOUT);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 2000, 2000));

    }

    @org.junit.Test
    public void applyFlowTx_TIMEOUT_BOTH2() {

        //timeout for both flowrules false

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());


        flowRuleServiceMock.setApply_bev(Bev.ERROR);
        flowRuleServiceMock.setRollback_bev(Bev.ERROR);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.TIMEOUT);
        exp_res.put("Rollback_process", Process.TIMEOUT);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 2000, 2000));

    }

    @org.junit.Test
    public void applyFlowTx_APPLY_TIMEOUT_RB_SUCCESS() {

        //both flowrules are correct

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());


        flowRuleServiceMock.setApply_bev(Bev.SUCCESS);
        flowRuleServiceMock.setRollback_bev(Bev.SUCCESS);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.TIMEOUT);
        exp_res.put("Rollback_process", Process.SUCCESS);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 2000, 7000));

    }

    @org.junit.Test
    public void applyFlowTx_APPLY_ERROR_RB_TIMEOUT() {

        //timeout for both flowrules false

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());


        flowRuleServiceMock.setApply_bev(Bev.ERROR);
        flowRuleServiceMock.setRollback_bev(Bev.ERROR);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.ERROR);
        exp_res.put("Rollback_process", Process.TIMEOUT);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 6000, 2000));

    }

    @org.junit.Test
    public void applyFlowTx_APPLY_ERROR_RB_SUCCESS() {

        //timeout for both flowrules false

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        flowRuleServiceMock.setApply_bev(Bev.ERROR);
        flowRuleServiceMock.setRollback_bev(Bev.ERROR);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.ERROR);
        exp_res.put("Rollback_process", Process.SUCCESS);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 6000, 6000));

    }

    @org.junit.Test
    public void applyFlowTx_Fail_Service_Error1() {
        //all flowrules are wrong

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        flowRuleServiceMock.setApply_bev(Bev.ERROR);
        flowRuleServiceMock.setRollback_bev(Bev.ERROR);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.ERROR);
        exp_res.put("Rollback_process", Process.SUCCESS);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 10000, 10000));
    }

    @org.junit.Test
    public void applyFlowTx_Fail_Service_Error2() {
        //one flow rule is wrong

        FlowRuleOperations.Builder addRules = FlowRuleOperations.builder();
        FlowRuleOperations.Builder removeRules = FlowRuleOperations.builder();
        TrafficTreatment drop = DefaultTrafficTreatment.builder()
                .drop().build();

        TrafficSelector ts = DefaultTrafficSelector.builder()
                .matchEthType(Ethernet.TYPE_IPV4).matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();

        DeviceId dev1 = DeviceId.deviceId("of:0000000000000011");
        DeviceId dev2 = DeviceId.deviceId("of:0000000000000012");

        FlowRule.Builder f1 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev1).withSelector(ts)
                .withTreatment(drop).makePermanent();

        ts = DefaultTrafficSelector.builder()
                .matchIPProtocol(IPv4.PROTOCOL_ICMP)
                .build();


        FlowRule.Builder f2 = DefaultFlowRule.builder().withCookie(System.currentTimeMillis())
                .withPriority(45000)
                .forDevice(dev2).withSelector(ts)
                .withTreatment(drop).makePermanent();

        addRules.add(f1.build());
        addRules.add(f2.build());
        removeRules.remove(f1.build());
        removeRules.remove(f2.build());

        flowRuleServiceMock.setApply_bev(Bev.ERROR);
        flowRuleServiceMock.setRollback_bev(Bev.ERROR);

        HashMap<String, Process> exp_res = new HashMap<>();

        exp_res.put("Apply_process", Process.ERROR);
        exp_res.put("Rollback_process", Process.SUCCESS);

        Assert.assertEquals(exp_res, txFlowServiceImpl.applyFlowTx(addRules, removeRules, 10000, 10000));

        log.info("count: " + flowRuleServiceMock.getApply_count());
    }
}