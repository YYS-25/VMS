// src/main/java/pkg/vms/model/Vouchers.java
package pkg.vms.model;

import java.io.*;
import java.util.*;

/**
 *
 */
public class Vouchers {

    /**
     * Default constructor
     */
    public Vouchers() {
    }

    /**
     *
     */
    public int ref_voucher;

    /**
     *
     */
    public int val_voucher;

    /**
     *
     */
    public Date init_date;

    /**
     *
     */
    public Date expiry_date;

    /**
     *
     */
    public String status_voucher;

    /**
     *
     */
    public Date date_redeemed;

    /**
     *
     */
    public String bearer;

    /**
     * Activate the voucher (mark as active)
     */
    public void activateVoucher() {
        // minimal activation logic
        if (this.status_voucher == null || !this.status_voucher.equalsIgnoreCase("Redeemed")) {
            this.status_voucher = "Active";
            if (this.init_date == null) {
                this.init_date = new Date();
            }
        }
    }

    /**
     * Redeem the voucher
     */
    public void redeemVoucher(Branch branch, Users user) {
        // minimal redemption logic
        this.status_voucher = "Redeemed";
        this.date_redeemed = new Date();
        if (user != null) {
            // best-effort assign bearer if Users exposes a sensible toString or name field
            this.bearer = user.toString();
        }
    }

    /**
     * Check voucher validity against a provided date
     */
    public boolean checkValidity(Date currentDate) {
        if (currentDate == null) return false;
        if (this.init_date == null || this.expiry_date == null) return false;
        boolean withinRange = !currentDate.before(this.init_date) && !currentDate.after(this.expiry_date);
        boolean notRedeemed = this.status_voucher == null || !this.status_voucher.equalsIgnoreCase("Redeemed");
        return withinRange && notRedeemed;
    }

    /**
     * Return a short info string about the voucher
     */
    public String getVoucherInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Voucher ref: ").append(this.ref_voucher)
                .append(", value: ").append(this.val_voucher)
                .append(", status: ").append(this.status_voucher == null ? "N/A" : this.status_voucher)
                .append(", init: ").append(this.init_date == null ? "N/A" : this.init_date)
                .append(", expiry: ").append(this.expiry_date == null ? "N/A" : this.expiry_date);
        if (this.date_redeemed != null) {
            sb.append(", redeemed: ").append(this.date_redeemed);
        }
        if (this.bearer != null) {
            sb.append(", bearer: ").append(this.bearer);
        }
        return sb.toString();
    }

}
