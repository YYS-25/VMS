// java
package pkg.vms.model;

import java.io.*;
import java.util.*;

/**
 *
 */
public class Branch {

    /**
     * Default constructor
     */
    public Branch() {
    }

    /**
     *
     */
    public int branch_id;

    /**
     *
     */
    public String location;

    /**
     * Assigned responsible user for this branch
     */
    public Users responsible;

    /**
     * Redeemed vouchers recorded by this branch
     */
    public List<Vouchers> redeemedVouchers = new ArrayList<>();

    /**
     * Assign a responsible user to this branch
     *
     * @param user the user to assign
     */
    public void assignResponsible(Users user) {
        if (user == null) return;
        this.responsible = user;
    }

    /**
     * Return redeemed vouchers for this branch (never null)
     *
     * @return unmodifiable list of redeemed vouchers
     */
    public List<Vouchers> getRedeemedVouchers() {
        return Collections.unmodifiableList(this.redeemedVouchers);
    }

    /**
     * Simple branch report summarizing key info
     *
     * @return textual report
     */
    public String getBranchReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Branch id: ").append(this.branch_id)
                .append(", location: ").append(this.location == null ? "N/A" : this.location)
                .append(", responsible: ").append(this.responsible == null ? "N/A" : (this.responsible.username == null ? "N/A" : this.responsible.username))
                .append(", redeemed vouchers: ").append(this.redeemedVouchers == null ? 0 : this.redeemedVouchers.size());
        return sb.toString();
    }

}
