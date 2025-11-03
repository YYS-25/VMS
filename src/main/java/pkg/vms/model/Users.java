// java
package pkg.vms.model;

import java.io.*;
import java.util.*;

/**
 *
 */
public class Users {

    /**
     * Default constructor
     */
    public Users() {
    }

    /**
     *
     */
    public String username;

    /**
     *
     */
    public String first_name_user;

    /**
     *
     */
    public String last_name_user;

    /**
     *
     */
    public String email_user;

    /**
     *
     */
    public String role;

    /**
     *
     */
    public String password;

    /**
     *
     */
    public String ddl;

    /**
     *
     */
    public String titre;

    /**
     *
     */
    public String status;

    /**
     * Attempt login by checking provided password against stored password.
     *
     * @param password the password to check
     * @return true if credentials match, false otherwise
     */
    public boolean login(String password) {
        if (password == null || this.password == null) return false;
        return this.password.equals(password);
    }

    /**
     * Logout the user (minimal stub).
     */
    public void logout() {
        // minimal logout behaviour: mark status
        this.status = "LoggedOut";
    }

    /**
     * Initiate a request on behalf of this user.
     *
     * @param request the request to initiate
     */
    public void initiateRequest(Requests request) {
        if (request == null) return;
        // associate request with this user and set initial status
        request.ref_client = this.username != null ? this.username.hashCode() : 0;
        request.setStatus("Initiated");
        request.creation_date = request.creation_date == null ? new Date() : request.creation_date;
    }

    /**
     * Pay a request (delegates to Requests.processPayment).
     *
     * @param request the request to pay
     */
    public void payRequest(Requests request) {
        if (request == null) return;
        // minimal payment: use stored payment info if available
        String paymentMethod = request.payment != null ? request.payment : "Unknown";
        request.processPayment(paymentMethod, new Date(), request.proof_of_request);
        request.status_payment = "Paid";
    }

    /**
     * Approve a request (delegates to Requests.approveRequest).
     *
     * @param request the request to approve
     */
    public void approveRequest(Requests request) {
        if (request == null) return;
        request.approveRequest(this);
    }

    /**
     * Generate vouchers for a request (delegates to Requests.generateVouchers).
     *
     * @param request the request to generate vouchers for
     * @return list of generated vouchers or empty list
     */
    public List<Vouchers> generateVoucher(Requests request) {
        if (request == null) return Collections.emptyList();
        List<Vouchers> v = request.generateVouchers();
        return v == null ? Collections.emptyList() : v;
    }

    /**
     * Redeem a voucher at a branch (delegates to Vouchers.redeemVoucher).
     *
     * @param voucher the voucher to redeem
     * @param branch the branch where redemption happens
     */
    public void redeemVoucher(Vouchers voucher, Branch branch) {
        if (voucher == null) return;
        voucher.redeemVoucher(branch, this);
    }

}
