// java
package pkg.vms.model;

import java.io.*;
import java.util.*;

/**
 *
 */
public class Clients {

    /**
     * Default constructor
     */
    public Clients() {
    }

    /**
     *
     */
    public int ref_client;

    /**
     *
     */
    public String nom_client;

    /**
     *
     */
    public String email_client;

    /**
     *
     */
    public String address_client;

    /**
     *
     */
    public String phone_client;

    /**
     * In-memory storage for this client's requests and vouchers (minimal stub)
     */
    public List<Requests> requests = new ArrayList<>();

    public List<Vouchers> vouchers = new ArrayList<>();

    /**
     * Create a new request for this client.
     *
     * @param voucherCount number of vouchers requested
     * @param duration     voucher validity duration in days
     * @return the created Requests instance
     */
    public Requests createRequest(int voucherCount, int duration) {
        Requests req = new Requests();
        req.ref_client = this.ref_client;
        req.num_voucher = voucherCount;
        req.duration_voucher = duration;
        req.creation_date = new Date();
        // compute expiry as creation_date + duration days (safe arithmetic)
        long millis = req.creation_date.getTime() + (duration * 24L * 60L * 60L * 1000L);
        req.expiry_voucher = new Date(millis);
        req.setStatus("Created");
        this.requests.add(req);

        // attempt to generate vouchers (Requests.generateVouchers is a stub that returns empty list in current code)
        try {
            List<Vouchers> generated = req.generateVouchers();
            if (generated != null && !generated.isEmpty()) {
                this.vouchers.addAll(generated);
            }
        } catch (Exception e) {
            // swallow exceptions to keep this method safe as a minimal stub
        }

        return req;
    }

    /**
     * Update client information (simple setter behavior).
     *
     * @param name    new name (ignored if null)
     * @param email   new email (ignored if null)
     * @param address new address (ignored if null)
     * @param phone   new phone (ignored if null)
     */
    public void updateClientInfo(String name, String email, String address, String phone) {
        if (name != null) this.nom_client = name;
        if (email != null) this.email_client = email;
        if (address != null) this.address_client = address;
        if (phone != null) this.phone_client = phone;
    }

    /**
     * Return this client's requests (never null).
     *
     * @return unmodifiable list of requests
     */
    public List<Requests> viewRequests() {
        return Collections.unmodifiableList(this.requests);
    }

    /**
     * Return this client's vouchers (never null).
     *
     * @return unmodifiable list of vouchers
     */
    public List<Vouchers> viewVouchers() {
        return Collections.unmodifiableList(this.vouchers);
    }

}
