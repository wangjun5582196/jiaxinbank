package com.ai.indeed.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: huzhang
 * @Date: 2023/2/17 10:43 上午
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",10290);
        //获取用户输入
        String input = "00001807<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><SYS_HEAD><SvcCd>50220001</SvcCd><ScnCd>03</ScnCd><BsnSrlNo>GNNT20230620117500000008</BsnSrlNo><TxnInttDt>20230227</TxnInttDt><TxnInttTm>091833</TxnInttTm><CnlTp>C1010101</CnlTp><CnsmrSysNo>NNT</CnsmrSysNo><CnsmrSysSrlNo>CNNT20230620000016385081</CnsmrSysSrlNo><InstId>01020</InstId><OprtrNo>11750</OprtrNo></SYS_HEAD><APP_HEAD></APP_HEAD><BODY><SysTp>GM</SysTp><ParmStrg>{\"ShBankCode\":\"\",\"UpperBasicAccountNum\":\"33001637335056000264\",\"NationalCertiNum\":\"\",\"Telephone\":\"123\",\"Address\":\"中国浙江省嘉兴市平湖市辽宁省抚顺新抚区上海街道632号\",\"PostalCode\":\"314200\",\"ShDepoType\":\"01\",\"ShBusiLicense\":\"123\",\"IDType\":\"101\",\"ShAccount\":\"8010000003730\",\"Name\":\"越魏翁\",\"BusiType\":\"0\",\"ShBasicNum\":\"\",\"Remarks\":\"\",\"UpperName\":\"\",\"UserID\":\"12\",\"ShBankName\":\"汪宣\",\"IDCode\":\"220220197902256881\",\"ShBusiLicenseDate\":\"\",\"ShCurrencyType\":\"0\",\"IfRegisCapital\":\"1\",\"LocalCertiNum\":\"\",\"ShOrganCode\":\"05078779-6\",\"RegisCapital\":\"100000000\",\"ShDepoName\":\"霖趣输晓元运骨质瓷厂\",\"UpperIDType\":\"\",\"UserPwd\":\"3\",\"CertiDocumentType1\":\"204\",\"UpperOrganCode\":\"\",\"UpperIDCode\":\"\",\"ShAccountOpenDate\":\"20220105\",\"UpperRelatedPersonType\":\"\",\"CertiDocumentType2\":\"\",\"BusiScope\":\"汽车客运站点投资、经营管理；物业管理、自有房屋及场地租赁、自有停车场服务；客运公交；加油站管理；公共自行车服务；实业投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"AccountType\":\"0001\",\"UpperUnitName\":\"\",\"IndustryBelong\":\"G\",\"AssoEnterprise\":\"0\",\"Country\":\"111\",\"RelatedPersonType\":\"0\",\"RegisCapitalType\":\"01\",\"CertiDocumentCode2\":\"\",\"CertiDocumentCode1\":\"91270843J5WU5CC338\",\"ShCurrencySort\":\"01\",\"NoNeedCerti\":\"\",\"ShAccountNature\":\"0000\"}</ParmStrg></BODY></service>";

        //将用户输入的数据写入到服务器，socket中
        OutputStream stream=socket.getOutputStream();
        PrintWriter writer=new PrintWriter(stream);
        writer.println(input);
        writer.flush();//关闭writer写入流。

    }
}
