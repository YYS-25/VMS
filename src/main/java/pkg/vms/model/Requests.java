package pkg.vms.model;

import java.io.*;
import java.util.*;

/**
 *
 */
public class Requests {

    /**
     * Default constructor
     */
    public Requests() {
    }

    public int ref_request;
    public Date creation_date;
    public int num_voucher;
    public int val_voucher;
    public String status;
    public int ref_client;
    public Date init_payment;
    public String payment;
    public String status_payment;
    public Date date_payment;
    public int ref_payment;
    public Date date_approval;
    public int duration_voucher;
    public Date expiry_voucher;
    public String proof_of_request;
    public String proof_file;

    /**
     * submit the request (stub)
     */
    public void submitRequest() {
        // TODO implement submit logic
    }

    /**
     * process payment (stub)
     */
    public void processPayment(String paymentMethod, Date date, String proof) {
        // TODO implement payment processing
        this.payment = paymentMethod;
        this.date_payment = date;
        this.proof_of_request = proof;
    }

    /**
     * approve request (stub)
     */
    public void approveRequest(Users approver) {
        // TODO implement approval logic
        this.status = "Approved";
        this.date_approval = new Date();
    }

    /**
     * reject request (stub)
     */
    public void rejectRequest(Users approver, String reason) {
        // TODO implement rejection logic
        this.status = "Rejected: " + reason;
        this.date_approval = new Date();
    }

    /**
     * generate vouchers (stub)
     */
    public List<Vouchers> generateVouchers() {
        // TODO implement voucher generation
        return Collections.emptyList();
    }

    /**
     * generate proof (stub)
     */
    public void generateProof() {
        // TODO implement proof generation
    }

    /**
     * get status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * set status
     */
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    /**
     * update status based on an action (stub)
     */
    public void updateStatus(String action) {
        // TODO implement status transitions
        this.status = action;
    }

    /**
     * advance to next status (stub)
     */
    public void nextStatus() {
        // TODO implement next status transition
    }
}