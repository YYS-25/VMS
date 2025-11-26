package pkg.vms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Clients {

    public int ref_client;
    public String nom_client;
    public String email_client;
    public String address_client;
    public String phone_client;

    // Requests et vouchers associated with this client
    private List<Requests> requests = new ArrayList<>();
    private List<Vouchers> vouchers = new ArrayList<>();


    // constructors

    public Clients() {}

    public Clients(int ref_client, String nom_client, String email_client, String address_client, String phone_client) {
        this.ref_client = ref_client;
        this.nom_client = nom_client;
        this.email_client = email_client;
        this.address_client = address_client;
        this.phone_client = phone_client;
    }

    // getters et setters


    public int getRef_client() { return ref_client; }
    public void setRef_client(int ref_client) { this.ref_client = ref_client; }

    public String getNom_client() { return nom_client; }
    public void setNom_client(String nom_client) { this.nom_client = nom_client; }

    public String getEmail_client() { return email_client; }
    public void setEmail_client(String email_client) { this.email_client = email_client; }

    public String getAddress_client() { return address_client; }
    public void setAddress_client(String address_client) { this.address_client = address_client; }

    public String getPhone_client() { return phone_client; }
    public void setPhone_client(String phone_client) { this.phone_client = phone_client; }

    public List<Requests> getRequests() { return Collections.unmodifiableList(requests); }
    public List<Vouchers> getVouchers() { return Collections.unmodifiableList(vouchers); }


    /**
     * Create a new request for this client.
     */
    public Requests createRequest(int voucherCount, int duration) {

        Requests req = new Requests();
        req.ref_client = this.ref_client;
        req.num_voucher = voucherCount;
        req.duration_voucher = duration;

        req.creation_date = new Date();

        // expiry = creation_date + duration days
        long expiryMillis = req.creation_date.getTime() + (duration * 24L * 60L * 60L * 1000L);
        req.expiry_voucher = new Date(expiryMillis);

        req.setStatus("Created");

        this.requests.add(req);

        // Attempt to generate vouchers (if implemented)
        try {
            List<Vouchers> generated = req.generateVouchers();
            if (generated != null && !generated.isEmpty()) {
                this.vouchers.addAll(generated);
            }
        } catch (Exception ignored) {}

        return req;
    }


    /**
     * Update client info.
     */
    public void updateClientInfo(String name, String email, String address, String phone) {
        if (name != null) this.nom_client = name;
        if (email != null) this.email_client = email;
        if (address != null) this.address_client = address;
        if (phone != null) this.phone_client = phone;
    }


    /**
     * Read-only access to requests
     */
    public List<Requests> viewRequests() {
        return Collections.unmodifiableList(this.requests);
    }

    /**
     * Read-only access to vouchers
     */
    public List<Vouchers> viewVouchers() {
        return Collections.unmodifiableList(this.vouchers);
    }

}
