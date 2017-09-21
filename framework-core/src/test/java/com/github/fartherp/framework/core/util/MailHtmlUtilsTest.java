/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = { "classpath:/applicationContext-test.xml" }, inheritLocations = false)
//@TransactionConfiguration(transactionManager = "transactionManager")
public class MailHtmlUtilsTest extends AbstractTestNGSpringContextTests {

    @BeforeMethod
    public void setUp() throws Exception {
        ServiceLocator.getInstance().setFactory(applicationContext);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testSendHtmlMail() {
        Map<String, Object> map = new HashMap<String, Object>();
        Flow flow = new Flow();
        flow.setPaticipants("wangyue10-zhanglihui-记文清qa-向海龙alb");
        flow.setNotstandardreason("KA外部特殊政策");
        map.put("flowid", flow.getId());
        List<Flowtask> flowTask = new ArrayList<Flowtask>();
        Flowtask task1 = new Flowtask();
        task1.setErpCustomerName("wangyue10");
        task1.setEndtime(new Date());
        task1.setOperatorName("wangyue10");
        task1.setFlowtaskstatusName("审核通过");
        task1.setMsg("1");
        flowTask.add(task1);
        Flowtask task2 = new Flowtask();
        task2.setErpCustomerName("zhanglihui");
        task2.setEndtime(new Date());
        task2.setOperatorName("zhanglihui");
        task2.setFlowtaskstatusName("审核通过");
        task2.setMsg("2");
        flowTask.add(task2);
        Flowtask task3 = new Flowtask();
        task3.setErpCustomerName("记文清qa");
        task3.setEndtime(new Date());
        task3.setOperatorName("记文清qa");
        task3.setFlowtaskstatusName("审核中");
        task3.setMsg("3");
        flowTask.add(task3);
        map.put("contractName", "新签");
        Contractinapprove contract = new Contractinapprove();
        contract.setContractid(105L);
        map.put("contractId", contract.getContractid());
        Flowtask task = new Flowtask();
        task.setErpCustomerName("zhanglihui");
        map.put("prePerson", task.getErpCustomerName());
        map.put("nextPerson", flowTask.get(flowTask.size() - 1).getErpCustomerName());
        map.put("flow", flow);
        map.put("flowTask", flowTask);
        // 发起人信息
        Useracct useracct = new Useracct();
        useracct.setUsername("陈刚");
        map.put("addUserName", useracct.getUsername());
        StringBuilder sb = new StringBuilder("您的【");
        sb.append("新签");
        sb.append("】合同【");
        sb.append(contract.getContractid());
        sb.append("】已经通过【");
        sb.append(task.getErpCustomerName());
        sb.append("】审批，当前待【");
        sb.append(flowTask.get(flowTask.size() - 1).getErpCustomerName());
        sb.append("】审批");
        String content = VelocityUtils.getInstance().parse("contract_approved_node_notice.vm", map);
//        MailHtmlUtils.sendHtmlMail("ck_queens@qq.com", sb.toString(), content,
//                "1", "214722930@qq.com");
    }

    public static class Flow {
        private Long id;
        private String paticipants;
        private String notstandardreason;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPaticipants() {
            return paticipants;
        }

        public void setPaticipants(String paticipants) {
            this.paticipants = paticipants;
        }

        public String getNotstandardreason() {
            return notstandardreason;
        }

        public void setNotstandardreason(String notstandardreason) {
            this.notstandardreason = notstandardreason;
        }
    }

    public static class Contractinapprove {
        private Long contractid;

        public Long getContractid() {
            return contractid;
        }

        public void setContractid(Long contractid) {
            this.contractid = contractid;
        }
    }

    public static class Flowtask {
        private String erpCustomerName;
        private Date endtime;
        private String operatorName;
        private String flowtaskstatusName;
        private String msg;

        public String getErpCustomerName() {
            return erpCustomerName;
        }

        public void setErpCustomerName(String erpCustomerName) {
            this.erpCustomerName = erpCustomerName;
        }

        public Date getEndtime() {
            return endtime;
        }

        public void setEndtime(Date endtime) {
            this.endtime = endtime;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getFlowtaskstatusName() {
            return flowtaskstatusName;
        }

        public void setFlowtaskstatusName(String flowtaskstatusName) {
            this.flowtaskstatusName = flowtaskstatusName;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class Useracct {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}