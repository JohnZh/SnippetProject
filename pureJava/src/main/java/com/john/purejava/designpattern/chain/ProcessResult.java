package com.john.purejava.designpattern.chain;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class ProcessResult  {
    private boolean isOk;
    private String approver;

    public ProcessResult(boolean isOk) {
        this.isOk = isOk;
    }

    public ProcessResult(boolean isOk, String approver) {
        this.isOk = isOk;
        this.approver = approver;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
}
